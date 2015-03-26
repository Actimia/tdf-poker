package se.edstrompartners.cards.scoring;

import junit.framework.TestCase;
import se.edstrompartners.cards.Card;
import se.edstrompartners.cards.Rank;
import se.edstrompartners.cards.Suit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fredrik on 2015-03-26.
 */
public class PairCheckerTest extends TestCase {

    public void testPair(){
        PairChecker p = new PairChecker();

        // Check simple pair
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEARTS, Rank.TWO));
        cards.add(new Card(Suit.HEARTS, Rank.FIVE));
        cards.add(new Card(Suit.HEARTS, Rank.SIX));
        cards.add(new Card(Suit.HEARTS, Rank.SEVEN));
        cards.add(new Card(Suit.HEARTS, Rank.EIGHT));
        cards.add(new Card(Suit.HEARTS, Rank.NINE));
        cards.add(new Card(Suit.SPADES, Rank.NINE));
        assertTrue(p.check(cards).isPresent());

        // Check that a pair is found in a set.
        cards = new ArrayList<>();
        cards.add(new Card(Suit.HEARTS, Rank.TWO));
        cards.add(new Card(Suit.HEARTS, Rank.FIVE));
        cards.add(new Card(Suit.DIAMONDS, Rank.NINE));
        cards.add(new Card(Suit.HEARTS, Rank.SEVEN));
        cards.add(new Card(Suit.HEARTS, Rank.EIGHT));
        cards.add(new Card(Suit.HEARTS, Rank.NINE));
        cards.add(new Card(Suit.SPADES, Rank.NINE));
        assertTrue(p.check(cards).isPresent());

        cards = new ArrayList<>();
        cards.add(new Card(Suit.HEARTS, Rank.TWO));
        cards.add(new Card(Suit.HEARTS, Rank.FIVE));
        cards.add(new Card(Suit.DIAMONDS, Rank.KING));
        cards.add(new Card(Suit.HEARTS, Rank.SEVEN));
        cards.add(new Card(Suit.HEARTS, Rank.KING));
        cards.add(new Card(Suit.HEARTS, Rank.NINE));
        cards.add(new Card(Suit.SPADES, Rank.NINE));
        assertTrue(p.check(cards).isPresent());

        List<Card> b = new ArrayList<>();

        b.add(new Card(Suit.DIAMONDS, Rank.KING));
        b.add(new Card(Suit.HEARTS, Rank.KING));

        List<Card> fp = p.check(cards).get();
        assertTrue(fp.equals(b));

    }

}