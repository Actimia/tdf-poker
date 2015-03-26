package se.edstrompartners.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static se.edstrompartners.cards.Util.iota;
import static java.util.stream.Collectors.*;

public class Board {
    private Deck deck;

    private List<Player> players = new ArrayList<>();

    private List<Card> board = new ArrayList<>();

    public Board(int numplayers) {
        if (numplayers > 15) {
            throw new IllegalArgumentException("Too many players!");
        }

        deck = new Deck();

        iota(numplayers).forEach(i -> players.add(new Player(board)));

        players.forEach(deck::deal);
        players.forEach(deck::deal);
    }

    public void dealFlop() {
        deck.draw();
        deck.deal(board);
        deck.deal(board);
        deck.deal(board);
    }

    public void dealTurn() {
        deck.draw();
        deck.deal(board);
    }

    public void dealRiver() {
        deck.draw();
        deck.deal(board);
    }

    private static List<Card> sorted(List<Card> cs){
        ArrayList<Card> css = new ArrayList<>(cs);
        Collections.sort(css);
        return css;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        players.forEach(p -> {sb.append(p); sb.append("\n");});
        sb.append("\n");
        sb.append(board.toString());
        sb.append("\n");
        return sb.toString();
    }
}
