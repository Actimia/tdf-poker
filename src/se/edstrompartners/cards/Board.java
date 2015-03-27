package se.edstrompartners.cards;

import se.edstrompartners.cards.scoring.ScoringHand;

import java.util.*;

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


    public void checkWinner(){

        class BestHand implements  Comparable<BestHand>  {
            public Player p;
            public ScoringHand s;

            public BestHand(Player p, ScoringHand s){
                this.p = p;
                this.s = s;
            }
            @Override
            public int compareTo(BestHand o) {
                return s.compareTo(o.s);
            }
        }
        
        List<BestHand> bestHands = new ArrayList<>();
        for(Player p: players){
            ArrayList<Card> c = new ArrayList(p.getHand());
            c.addAll(board);
            bestHands.add(new BestHand(p, ScoringHand.createBestHand(c)));
        }
        Collections.sort(bestHands);
        Collections.reverse(bestHands);


        System.out.println(bestHands.get(0).s);

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
