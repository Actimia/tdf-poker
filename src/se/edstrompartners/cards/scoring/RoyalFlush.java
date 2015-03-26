package se.edstrompartners.cards.scoring;

import se.edstrompartners.cards.Card;

import java.util.List;
import java.util.Optional;

/**
 * Created by actim_000 on 2015-03-26.
 */
public class RoyalFlush implements HandChecker{

    @Override
    public Optional<List<Card>> check(List<Card> cards) {
        StraightFlushChecker sfc = new StraightFlushChecker();
        return sfc.check(cards).map(sf -> null);
    }

    @Override
    public ScoringHand.Kind kind() {
        return ScoringHand.Kind.ROYALFLUSH;
    }
}
