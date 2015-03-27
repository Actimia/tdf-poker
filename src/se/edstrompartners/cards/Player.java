package se.edstrompartners.cards;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by actim_000 on 2015-03-26.
 */
public class Player {

    private List<Card> hand;

    private List<Card> board;

    public Player(List<Card> board) {
        this.board = board;
        hand = new ArrayList<>();
    }

    public void addCard(Card c) {
        hand.add(c);
    }
    public List<Card> getHand(){ return hand; }
    @Override
    public String toString() {
        return hand.toString();
    }
}
