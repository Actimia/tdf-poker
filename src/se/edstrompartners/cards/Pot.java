package se.edstrompartners.cards;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Pot {

    private static class PotEntry {
        int bet;
        boolean folded;
        boolean allin;

        @Override
        public String toString() {
            return "" + bet;
        }
    }

    private int current;
    private Map<Player, PotEntry> bets = new HashMap<>();

    private Pot sidepot = null;

    /**
     * Attempt to bet amount.
     *
     * Returns the number of chips to be deducted from the player.
     */
    public int bet(Player p, int amount) {
        PotEntry entry = bets.computeIfAbsent(p, pl -> new PotEntry());
        int prev = entry.bet;

        // these players are always done betting
        if (entry.allin || entry.folded) return 0;

        // this is an all in
        if (amount >= p.chips) {
            amount = p.chips;
            entry.allin = true;

            current = Math.max(current, amount + prev);
            entry.bet += amount;

            if (current == entry.bet) {
                // all in call, no need to create a side pot yet
                return amount;
            } else {
                if (sidepot == null) {
                    createSidePot(current - entry.bet, p);
                } else {
                    throw new RuntimeException("All in raise");
                }
            }

            return amount;
        }


        // if a side pot exists, you can only call this pot
        if (sidepot != null) {
            int forthis = current - prev;
            int forside = amount - forthis;
            entry.bet += forthis;

            return forthis + this.sidepot.bet(p, forside);
        } else {
            // check if raise
            current = Math.max(current, amount + prev);
            entry.bet += amount;
            return amount;
        }
    }

    private void createSidePot(int amount, Player p) {
        sidepot = new Pot();
        current -= amount;

        // all the money that p has, this should be taken from every players bet to create
        // the side pot

        for (Player sp : bets.keySet()) {
            if (sp == p) {
                // player who is all in can never be a part of the side pot
                continue;
            }
            PotEntry entry = bets.get(sp);
            if (entry.bet > amount) {
                entry.bet -= amount;
                sidepot.bet(sp, amount);
            }
        }
    }

    public void resolvePot(List<BestHand> hands) {
        Optional<Player> op = hands.stream().map(h -> h.player).filter(bets::containsKey)
                .findFirst();

        if (op.isPresent()) {
            Player winner = op.get();
            int total = getTotalForThis();
            winner.chips += total;
            System.out.println(winner.name + " wins a pot of " + total);
            if (sidepot != null) {
                sidepot.resolvePot(hands);
            }
        } else {
            throw new RuntimeException("No winner found");
        }
    }

    private int getTotalForThis() {
        return bets.values().stream().mapToInt(pe -> pe.bet).sum();
    }

    public int getTotal() {
        return getTotalForThis() + (sidepot != null ? sidepot.getTotal() : 0);
    }

    public void newRound() {
//        bets.clear();
//        current = 0;
//        if (sidepot != null) {
//            sidepot.newRound();
//        }
    }

    /**
     * Every player left in the game has bet the same amount.
     */
    public boolean bettingDone() {
        return bets.isEmpty() || bets.values().stream()
                .filter(pe -> !pe.folded || !pe.allin)
                .mapToInt(pe -> pe.bet)
                .distinct()
                .count() == 1 && (sidepot == null || sidepot.bettingDone());
    }

    public void fold(Player p) {
        bets.getOrDefault(p, new PotEntry()).folded = true;
        if (sidepot != null) {
            sidepot.fold(p);
        }

//        System.out.println(p.name + " folded");
    }

    public int toCall(Player p) {
        return (current - bets.getOrDefault(p, new PotEntry()).bet) + (sidepot != null ? sidepot
                .toCall(p) : 0);
    }

    public int getCurrent() {
        return current;
    }

    @Override
    public String toString() {
        return bets.toString() + (sidepot != null ? "\n" + sidepot.toString() : "");
    }
}
