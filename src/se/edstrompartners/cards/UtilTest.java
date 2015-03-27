package se.edstrompartners.cards;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by actim_000 on 2015-03-27.
 */
public class UtilTest {
    @Test
    public void testCardGeneration(){
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.SPADES, Rank.ACE));
        cards.add(new Card(Suit.SPADES, Rank.KING));
        cards.add(new Card(Suit.SPADES, Rank.QUEEN));
        cards.add(new Card(Suit.SPADES, Rank.JACK));
        cards.add(new Card(Suit.SPADES, Rank.TEN));

        List<Card> hand = Util.quickHand("sa, sk Sq sJs10");

        assertEquals(cards, hand);
    }

}
