package se.edstrompartners.cards;

import se.edstrompartners.cards.scoring.ScoringHand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Round {

    private Deck deck = new Deck();
    private List<Card> board = new ArrayList<>();
    private List<Player> players;
    private Pot pot = new Pot();
    private Game game;

    public Round(Game game) {
        this.game = game;
        players = new ArrayList<>(game.getPlayers());

        Player big = players.get(players.size() - 1);
        Player small = players.get(players.size() - 2);

//        players.forEach(p -> p.role = Role.NORMAL);
        pot.bet(big, 100);
//        big.role = Role.BIGBLIND;
        pot.bet(small, 50);
//        small.role = Role.SMALLBLIND;

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
        BestHand winner = checkWinner();
        winner.p.addChips(pot.getTotal());
        System.out.println(this);
        System.out.println(winner);
    }

    private void bettingRound() {

        // first an initial round of betting
        Iterator<Player> pit = players.iterator();
        while (pit.hasNext()) {
            Player p = pit.next();
            int toCall = pot.toCall(p);
            int bet = p.makePlay(toCall, this);
            if (bet == -1) {
                pot.fold(p);
                pit.remove();
            } else {
                pot.bet(p, bet);
            }
        }
        // then we bet until everyone is happy
        pit = players.iterator();
        while (!pot.bettingDone() && pit.hasNext()) {
            Player p = pit.next();
            int toCall = pot.toCall(p);
            int bet = p.makePlay(toCall, this);
            if (bet == -1) {
                pot.fold(p);
            } else {
                pot.bet(p, bet);
            }
        }
    }

    /**
     * Determines which player currently has the strongest hand
     * with all the cards currently usable for each player.
     *
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
        sb.append("\n");
        sb.append(" ");
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
            System.out.println(board);
        }
    }
}
