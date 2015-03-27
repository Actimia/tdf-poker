package se.edstrompartners.cards.scoring;

import org.junit.Test;
import se.edstrompartners.cards.Card;
import se.edstrompartners.cards.Rank;
import se.edstrompartners.cards.Suit;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by actim_000 on 2015-03-26.
 */
public class QuadCheckerTest {
    @Test
    public void testQuads() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.DIAMONDS, Rank.KING));
        cards.add(new Card(Suit.HEARTS, Rank.KING));
        cards.add(new Card(Suit.SPADES, Rank.KING));
        cards.add(new Card(Suit.CLUBS, Rank.KING));
        cards.add(new Card(Suit.DIAMONDS, Rank.TEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.FIVE));
        cards.add(new Card(Suit.SPADES, Rank.SEVEN));

        QuadChecker qc = new QuadChecker();
        assertTrue(qc.check(cards).isPresent());

        cards.clear();
        cards.add(new Card(Suit.DIAMONDS, Rank.KING));
        cards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        cards.add(new Card(Suit.SPADES, Rank.KING));
        cards.add(new Card(Suit.CLUBS, Rank.KING));
        cards.add(new Card(Suit.DIAMONDS, Rank.TEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.FIVE));
        cards.add(new Card(Suit.SPADES, Rank.SEVEN));

        assertFalse(qc.check(cards).isPresent());
    }

}
