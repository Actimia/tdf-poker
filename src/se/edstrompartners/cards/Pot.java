package se.edstrompartners.cards;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by actim_000 on 2015-03-27.
 */
public class Pot {
    private int total;
    private int current;
    private Map<Player, Integer> bets = new HashMap<>();

    public void bet(Player p, int amount) {
        if (bets.containsKey(p)) {
            int cur = bets.get(p);
            int ptotal = cur + amount;
            total += amount;
            current = Math.max(current, ptotal);
            p.removeChips(amount);
            bets.put(p, ptotal);
        } else {
            total += amount;
            current = Math.max(current, amount);
            p.removeChips(amount);
            bets.put(p, amount);
        }
    }

    public int getTotal() {
        return total;
    }

    public void newRound() {
        bets.clear();
        current = 0;
    }

    /**
     * Every player left in the game has bet the same amount.
     */
    public boolean bettingDone() {
        return total > 0 && bets.values().stream()
                .distinct()
                .count() == 1;
    }

    public void fold(Player p) {
        bets.remove(p);
    }

    public int toCall(Player p) {
        Integer pcur = bets.get(p);
        if (pcur == null) {
            return current;
        } else {
            return current - pcur;
        }
    }
}
