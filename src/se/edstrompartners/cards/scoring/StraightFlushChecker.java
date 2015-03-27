package se.edstrompartners.cards.scoring;

import se.edstrompartners.cards.Card;
import se.edstrompartners.cards.Suit;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by actim_000 on 2015-03-26.
 */
public class StraightFlushChecker implements HandChecker {
    @Override
    public Optional<List<Card>> check(List<Card> cards) {
        Map<Suit, List<Card>> suits = cards.stream().collect(Collectors.groupingBy(c -> c.suit));

        StraightChecker sc = new StraightChecker();
        return suits.values().stream()
                .flatMap(s -> sc.check(s)
                        .map(sf -> Stream.of(sf))
                        .orElse(Stream.empty()))
                .sorted(ScoringHand.LIST_COMPARATOR)
                .findFirst();

    }

    @Override
    public Comparator<List<Card>> comparator() {
        return (o1, o2) -> {
            if (o1.equals(o2)) {
                return 0;
            }

            List<Card> q1 = check(o1).get();
            List<Card> q2 = check(o2).get();

            int cmp = ScoringHand.LIST_COMPARATOR.compare(q1, q2);
            if (cmp != 0) {
                return cmp;
            }

            List<Card> o1s = new ArrayList<>(o1);
            List<Card> o2s = new ArrayList<>(o2);
            o1s.removeAll(q1);
            o2s.removeAll(q2);

            return ScoringHand.LIST_COMPARATOR.compare(o1s, o2s);
        };
    }
}
