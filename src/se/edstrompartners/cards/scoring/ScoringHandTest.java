package se.edstrompartners.cards.scoring;

import org.junit.Test;
import se.edstrompartners.cards.Card;
import se.edstrompartners.cards.Util;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by actim_000 on 2015-03-27.
 */
public class ScoringHandTest {

    @Test
    public void testScoring() {
        {
            List<Card> h = Util.quickHand("SA SK SQ SJ S10 D7, D2");
            ScoringHand sh = ScoringHand.createBestHand(h);
            assertTrue(sh.kind() == ScoringHand.Kind.ROYALFLUSH);
        }

        {
            List<Card> h = Util.quickHand("SA, H10, CJ, S5, S3, DA");
            ScoringHand sh = ScoringHand.createBestHand(h);
            assertTrue(sh.kind() == ScoringHand.Kind.PAIR);
        }

        {
            List<Card> h = Util.quickHand("SA, H10, D10, DA");
            ScoringHand sh = ScoringHand.createBestHand(h);
            assertTrue(sh.kind() == ScoringHand.Kind.TWOPAIR);
        }

        {
            List<Card> h = Util.quickHand("SA, S2, S3, S4, SK, SQ, DQ");
            ScoringHand sh = ScoringHand.createBestHand(h);
            assertTrue(sh.kind() == ScoringHand.Kind.TWOPAIR);
        }

        {
            List<Card> h = Util.quickHand("SA, H10");
            ScoringHand sh = ScoringHand.createBestHand(h);
            assertTrue(sh.kind() == ScoringHand.Kind.HIGHCARD);
        }

        {
            List<Card> h = Util.quickHand("SA, S7, S9, S4, DQ, CA, S2");
            ScoringHand sh = ScoringHand.createBestHand(h);
            assertTrue(sh.kind() == ScoringHand.Kind.FLUSH);
        }

        {
            List<Card> h = Util.quickHand("S4 S5 S6 S7 S8 SQ D10");
            ScoringHand sh = ScoringHand.createBestHand(h);
            assertTrue(sh.kind() == ScoringHand.Kind.STRAIGHTFLUSH);
        }
    }

    @Test
    public void TestComparison(){
        {
            List<Card> h1 = Util.quickHand("SA SK SQ SJ S10 D7, D2");
            ScoringHand sh1 = ScoringHand.createBestHand(h1);

            List<Card> h2 = Util.quickHand("SA SK S10 D7, D2 SQ SJ ");
            ScoringHand sh2 = ScoringHand.createBestHand(h2);
            assertTrue(sh1.compareTo(sh2) == 0);
        }

        {
            List<Card> h1 = Util.quickHand("SA, H10, D10, DA S5 D7, D2");
            ScoringHand sh1 = ScoringHand.createBestHand(h1);

            List<Card> h2 = Util.quickHand("SA, HJ, DJ, DA S5 D7, D2");
            ScoringHand sh2 = ScoringHand.createBestHand(h2);
            assertTrue(sh1.compareTo(sh2) <= 0);
        }
    }

}
