package se.edstrompartners.cards.scoring;

import se.edstrompartners.cards.Card;
import se.edstrompartners.cards.Rank;

import java.util.*;
import java.util.stream.Collectors;

public class FullHouseChecker implements HandChecker {

    @Override
    public Optional<List<Card>> check(List<Card> cards) {
        Map<Rank, List<Card>> map = cards.stream().collect(Collectors.groupingBy(c -> c.rank));

        Optional<List<Card>> oset = map.keySet().stream()
                .sorted(Collections.reverseOrder())
                .map(map::get)
                .filter(cs -> cs.size() >= 3)
                .findFirst();

        if (oset.isPresent()) {
            List<Card> set = oset.get();
            Map<Rank, List<Card>> map2 = cards.stream()
                    .filter(c -> !set.contains(c))  // remove the cards already found
                    .collect(Collectors.groupingBy(c -> c.rank));

            Optional<List<Card>> pair = map2.keySet().stream()
                    .sorted(Collections.reverseOrder())
                    .map(map::get)
                    .filter(cs -> cs.size() >= 2)
                    .findFirst();

            return pair
                    .map(p -> { // add the set back
                        set.addAll(p);
                        return set;
                    });
        }
        return Optional.empty();
    }

    @Override
    public Comparator<List<Card>> comparator() {
        return (o1, o2) -> {
            SetChecker sc = new SetChecker();

            List<Card> q1 = sc.check(o1).get();
            List<Card> q2 = sc.check(o2).get();
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
        return String.format("full house, %ss full of %ss", cards.get(0).rank.verboseName(),
                cards.get(3).rank.verboseName());
    }
}
