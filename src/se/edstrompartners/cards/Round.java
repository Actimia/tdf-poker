package se.edstrompartners.cards;

import se.edstrompartners.cards.scoring.ScoringHand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Round {

    private Deck deck = new Deck();
    private List<Card> board = new ArrayList<>();
    private List<Player> players;
    private Pot pot = new Pot();
    private Game game;

    public Round(Game game) {
        this.game = game;
        players = new ArrayList<>(game.getPlayers());

        Player small = players.get(players.size() - 2);
        Player big = players.get(players.size() - 1);

        small.chips -= pot.bet(small, 50);
        big.chips -= pot.bet(big, 100);

        players.forEach(deck::deal);
        players.forEach(deck::deal);
    }

    public void play() {
        bettingRound();

        if (players.size() > 1) {
            dealFlop();
            pot.newRound();
            bettingRound();
        }

        if (players.size() > 1) {
            dealTurn();
            pot.newRound();
            bettingRound();
        }

        if (players.size() > 1) {
            dealRiver();
            pot.newRound();
            bettingRound();
        }
//        BestHand winner = checkWinner();
//        winner.player.addChips(pot.getTotal());
        List<BestHand> hands = checkWinner();
        pot.resolvePot(hands);
        BestHand winner = hands.get(0);
        System.out.println(this);
        System.out.println(winner);
        System.out.println();
    }

    private void bettingRound() {
        // First, we make sure everyone gets to bet
        Iterator<Player> pit = players.iterator();
        while (pit.hasNext() && playersLeft() > 1) {
            Player p = pit.next();
            if (!individualBet(p)) {
                pit.remove();
            }
        }

        // then we make keep betting until its done
        while (!pot.bettingDone() && playersLeft() > 1) {
            pit = players.iterator();
            while (pit.hasNext() && !pot.bettingDone() && playersLeft() > 1) {
                Player p = pit.next();
                if (!individualBet(p)) {
                    pit.remove();
                }
            }
        }
    }

    private boolean individualBet(Player p) {
        int toCall = pot.toCall(p);
        int bet = p.makePlay(toCall, this);
        if (bet < 0) {
            pot.fold(p);
            return false;
        } else {
            p.chips -= pot.bet(p, bet);
            return true;
        }
    }

    public List<Card> getHand(Player p) {
        List<Card> res = new ArrayList<>(p.getHand());
        res.addAll(board);
        return res;
    }

    /**
     * Determines which player currently has the strongest hand
     * with all the cards currently usable for each player.
     *
     * #TODO: split pots
     *
     * @return The best player and their hand.
     */
    public List<BestHand> checkWinner() {
        List<BestHand> bestHands = players.stream()
                .map(p -> new BestHand(p, ScoringHand.createBestHand(getHand(p))))
                .collect(Collectors.toList());
        Collections.sort(bestHands);
        Collections.reverse(bestHands);

        return bestHands;
    }

    /**
     * Discards a card and draws three cards.
     */
    public void dealFlop() {
        deck.draw();
        deck.deal(board);
        deck.deal(board);
        deck.deal(board);
    }

    /**
     * Discards a card and draws a fourth card.
     */
    public void dealTurn() {
        deck.draw();
        deck.deal(board);
    }

    /**
     * Discards a card and draws a fifth and final card.
     */
    public void dealRiver() {
        deck.draw();
        deck.deal(board);
    }

    /**
     * Provides a string representation of the current board state.
     * Contains information about each players hold cards, as well
     * as the public cards.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        game.getPlayers().forEach(p -> {
            if (!players.contains(p)) {
                sb.append(p.getRoundState());
            } else {
                sb.append(p);
            }

            sb.append("\n");
        });
        sb.append("\n\t");
        sb.append(Util.formatCardList(board));
        return sb.toString();
    }

    public void printCurrentState() {
        System.out.println();
        System.out.println("Pot: " + pot.getTotal() + ", current bet: " + pot.getCurrent());
        players.stream()
                .map(Player::getRoundState)
                .forEach(System.out::println);

        if (!board.isEmpty()) {
            System.out.println("\t" + Util.formatCardList(board));
        }
    }

    public int playersLeft() {
        return players.size();
    }
}
