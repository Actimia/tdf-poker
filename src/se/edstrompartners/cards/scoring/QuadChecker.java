package se.edstrompartners.cards.scoring;

import se.edstrompartners.cards.Card;
import se.edstrompartners.cards.Rank;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by actim_000 on 2015-03-26.
 */
public class QuadChecker implements HandChecker {
    @Override
    public Optional<List<Card>> check(List<Card> cards) {
        Map<Rank, List<Card>> map = cards.stream().collect(Collectors.groupingBy(c -> c.rank));
        return map.keySet().stream()
                .sorted(Collections.reverseOrder())
                .map(map::get)
                .filter(l -> l.size() >= 4)
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
