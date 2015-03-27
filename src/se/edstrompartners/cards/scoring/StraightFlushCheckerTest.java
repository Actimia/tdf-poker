package se.edstrompartners.cards.scoring;

import org.junit.Test;
import se.edstrompartners.cards.Card;
import se.edstrompartners.cards.Rank;
import se.edstrompartners.cards.Suit;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by actim_000 on 2015-03-26.
 */
public class StraightFlushCheckerTest {
    @Test
    public void testStraightFlush() {
        StraightFlushChecker sf = new StraightFlushChecker();
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEARTS, Rank.TWO));
        cards.add(new Card(Suit.HEARTS, Rank.FIVE));
        cards.add(new Card(Suit.HEARTS, Rank.SIX));
        cards.add(new Card(Suit.HEARTS, Rank.SEVEN));
        cards.add(new Card(Suit.HEARTS, Rank.EIGHT));
        cards.add(new Card(Suit.HEARTS, Rank.NINE));
        cards.add(new Card(Suit.SPADES, Rank.QUEEN));

        assertTrue(sf.check(cards).isPresent());
    }
}
