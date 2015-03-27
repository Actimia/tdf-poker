package se.edstrompartners.cards.scoring;

import org.junit.Test;
import se.edstrompartners.cards.Card;
import se.edstrompartners.cards.Rank;
import se.edstrompartners.cards.Suit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by actim_000 on 2015-03-27.
 */
public class FullHouseCheckerTest {

    @Test
    public void testFullHouse() {
        FullHouseChecker fh = new FullHouseChecker();

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUBS, Rank.EIGHT));
        cards.add(new Card(Suit.HEARTS, Rank.EIGHT));
        cards.add(new Card(Suit.DIAMONDS, Rank.EIGHT));
        cards.add(new Card(Suit.CLUBS, Rank.JACK));
        cards.add(new Card(Suit.SPADES, Rank.JACK));
        cards.add(new Card(Suit.SPADES, Rank.FIVE));
        cards.add(new Card(Suit.HEARTS, Rank.NINE));

        assertTrue(fh.check(cards).isPresent());

        cards.clear();
        cards.add(new Card(Suit.CLUBS, Rank.EIGHT));
        cards.add(new Card(Suit.HEARTS, Rank.TWO));
        cards.add(new Card(Suit.DIAMONDS, Rank.EIGHT));
        cards.add(new Card(Suit.CLUBS, Rank.JACK));
        cards.add(new Card(Suit.SPADES, Rank.ACE));
        cards.add(new Card(Suit.SPADES, Rank.FIVE));
        cards.add(new Card(Suit.HEARTS, Rank.NINE));

        assertTrue(!fh.check(cards).isPresent());
    }

    @Test
    public void testFullHouseComparator() {
        FullHouseChecker fh = new FullHouseChecker();
        Comparator<List<Card>> fhc = fh.comparator();

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUBS, Rank.EIGHT));
        cards.add(new Card(Suit.HEARTS, Rank.EIGHT));
        cards.add(new Card(Suit.DIAMONDS, Rank.EIGHT));
        cards.add(new Card(Suit.CLUBS, Rank.JACK));
        cards.add(new Card(Suit.SPADES, Rank.JACK));

        List<Card> cards2 = new ArrayList<>();
        cards2.add(new Card(Suit.CLUBS, Rank.EIGHT));
        cards2.add(new Card(Suit.HEARTS, Rank.EIGHT));
        cards2.add(new Card(Suit.DIAMONDS, Rank.JACK));
        cards2.add(new Card(Suit.CLUBS, Rank.JACK));
        cards2.add(new Card(Suit.SPADES, Rank.JACK));

        int cmp = fhc.compare(cards, cards2);
        assertTrue(cmp < 0);

        cards2.clear();
        cards2.add(new Card(Suit.CLUBS, Rank.KING));
        cards2.add(new Card(Suit.HEARTS, Rank.KING));
        cards2.add(new Card(Suit.DIAMONDS, Rank.JACK));
        cards2.add(new Card(Suit.CLUBS, Rank.JACK));
        cards2.add(new Card(Suit.SPADES, Rank.JACK));

        cmp = fhc.compare(cards, cards2);
        assertTrue(cmp < 0);

        cards2.clear();
        cards2.add(new Card(Suit.CLUBS, Rank.KING));
        cards2.add(new Card(Suit.HEARTS, Rank.KING));
        cards2.add(new Card(Suit.DIAMONDS, Rank.KING));
        cards2.add(new Card(Suit.CLUBS, Rank.JACK));
        cards2.add(new Card(Suit.SPADES, Rank.JACK));

        cmp = fhc.compare(cards, cards2);
        assertTrue(cmp < 0);
    }
}
