package se.edstrompartners.cards.scoring;

import se.edstrompartners.cards.Card;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Created by actim_000 on 2015-03-26.
 */
public class RoyalFlush implements HandChecker {

    @Override
    public Optional<List<Card>> check(List<Card> cards) {
        StraightFlushChecker sfc = new StraightFlushChecker();
        return sfc.check(cards)
                .filter(sf -> sf.stream()
                        .mapToInt(c -> c.rank.ordinal())
                        .sum() == 50);
    }

    @Override
    public Comparator<List<Card>> comparator() {
        return (o1, o2) -> 0;
    }

    @Override
    public String verboseName(List<Card> cards) {
        return "royal straight flush";
    }
}
