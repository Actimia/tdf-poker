package se.edstrompartners.cards;

import se.edstrompartners.cards.scoring.ScoringHand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static se.edstrompartners.cards.Util.iota;

public class Board {
    private Deck deck;
    private List<Player> players = new ArrayList<>();
    private List<Card> board = new ArrayList<>();

    /**
     * Creates a new playing round and gives each player two hold cards.
     * Supports up to 15 players.
     * @param numplayers The number of players to generate.
     */
    public Board(int numplayers) {
        if (numplayers > 15) {
            throw new IllegalArgumentException("Too many players!");
        }

        deck = new Deck();

        Player.resetNameGen();
        iota(numplayers).forEach(i -> players.add(new Player()));

        players.forEach(deck::deal);
        players.forEach(deck::deal);
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
     * Links a player with a scored hand.
     */
    static class BestHand implements Comparable<BestHand> {
        public Player p;
        public ScoringHand s;

        public BestHand(Player p, ScoringHand s) {
            this.p = p;
            this.s = s;
        }

        /**
         * Compares two hands to see which is best. Inconsistent with equals.
         */
        @Override
        public int compareTo(BestHand o) {
            return s.compareTo(o.s);
        }

        @Override
        public String toString() {
            return p.getName() + " with " + s.kind() + ": " + Util.formatCardList(s.cards());
        }
    }

    /**
     * Determines which player currently has the strongest hand
     * with all the cards currently usable for each player.
     * @return The best player and their hand.
     */
    public BestHand checkWinner() {
        List<BestHand> bestHands = new ArrayList<>();
        for (Player p : players) {
            ArrayList<Card> c = new ArrayList<>(p.getHand());
            c.addAll(board);
            bestHands.add(new BestHand(p, ScoringHand.createBestHand(c)));
        }
        Collections.sort(bestHands);
        Collections.reverse(bestHands);

        return bestHands.get(0);
    }

    /**
     * Provides a string representation of the current board state.
     * Contains information about each players hold cards, as well
     * as the public cards.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        players.forEach(p -> {
            sb.append(p);
            sb.append("\n");
        });
        sb.append("\n");
        sb.append(" ");
        sb.append(Util.formatCardList(board));
        return sb.toString();
    }
}
