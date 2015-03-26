package se.edstrompartners.cards;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Fredrik on 2015-03-26.
 */
public class CardTest {


    @Test
    public void testCard(){

        Card c = new Card(Suit.HEARTS, Rank.EIGHT);
        assertTrue(c.toString().equals("8♥"));


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
        assertTrue(c.compareTo(c2) < 0);
        c2 = new Card(Suit.SPADES, Rank.SEVEN);
        assertTrue(c.compareTo(c2) > 0);


    }

}