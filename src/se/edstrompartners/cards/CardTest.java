package se.edstrompartners.cards;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Fredrik on 2015-03-26.
 */
public class CardTest {

    @Test
    public void testCard() {
        Card c = new Card(Suit.HEARTS, Rank.EIGHT);

        Card c2 = new Card(Suit.HEARTS, Rank.EIGHT);
        assertTrue(c.equals(c2));
        c2 = new Card(Suit.SPADES, Rank.EIGHT);
        assertFalse(c.equals(c2));
        c2 = new Card(Suit.SPADES, Rank.NINE);
        assertFalse(c.equals(c2));
        c2 = new Card(Suit.HEARTS, Rank.NINE);
        assertFalse(c.equals(c2));


        c2 = new Card(Suit.HEARTS, Rank.EIGHT);
        assertTrue(c.compareTo(c2) == 0);
        c2 = new Card(Suit.SPADES, Rank.NINE);
        assertTrue(c.compareTo(c2) > 0);
        c2 = new Card(Suit.SPADES, Rank.SEVEN);
        assertTrue(c.compareTo(c2) < 0);
    }

    @Test
    public void testSorting() {
        {
            List<Card> h1 = Util.quickHand("h4, hq, hk, h5, ha");
            h1.sort(Card.SUIT_SENSITIVE);

            List<Card> res = Util.quickHand("ha hk hq h5 h4");
            assertEquals(h1, res);
        }

        {
            List<Card> h1 = Util.quickHand("h3 d3 s3 c3 ha");
            h1.sort(Card.SUIT_INSENSITIVE);

            List<Card> res = Util.quickHand("ha h3 d3 s3 c3");
            assertEquals(h1, res);
        }

        {
            List<Card> h1 = Util.quickHand("h3 d3 s3 c3 ha");
            h1.sort(Card.SUIT_SENSITIVE);

            List<Card> res = Util.quickHand("ha s3 h3 d3 c3");
            assertEquals(h1, res);
        }
    }
}
