package se.edstrompartners.cards.scoring;

import se.edstrompartners.cards.Card;
import se.edstrompartners.cards.Rank;

import java.util.*;
import java.util.stream.Collectors;

public class TwoPairChecker implements HandChecker {
    @Override
    public Optional<List<Card>> check(List<Card> cards) {
        Map<Rank, List<Card>> map = cards.stream()
                .collect(Collectors.groupingBy(c -> c.rank));
        List<List<Card>> allpairs = map.keySet().stream()
                .sorted(Collections.reverseOrder()) // ensure highest are found first
                .map(map::get)
                .filter(cs -> cs.size() == 2) // extract  pairs
                .limit(2)                     // ensure correct number
                .collect(Collectors.toList());
        if (allpairs.size() == 2) {
            List<Card> total = new ArrayList<>();
            allpairs.forEach(p -> total.addAll(p));
            return Optional.of(total);
        }
        return Optional.empty();
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

    @Override
    public String verboseName(List<Card> cards) {
        return String.format("two pairs, %ss and %ss", cards.get(0).rank.verboseName(), cards.get
                (2).rank.verboseName());
    }
}
