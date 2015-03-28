package se.edstrompartners.cards.scoring;

import se.edstrompartners.cards.Card;
import se.edstrompartners.cards.Rank;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by actim_000 on 2015-03-26.
 */
public class StraightChecker implements HandChecker {
    @Override
    public Optional<List<Card>> check(List<Card> cards) {
        Map<Rank, List<Card>> map = cards.stream().collect(Collectors.groupingBy(c -> c.rank));
        List<Rank> cs = Arrays.asList(Rank.values());
        Collections.reverse(cs); // ensures higher straights are found first
        for (Rank r : cs) {
            Rank cur = r;
            List<Card> candidates = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                if (map.containsKey(cur)) {
                    candidates.add(map.get(cur).get(0));
                    cur = cur.prev(); // check downwards to find 10-A before J-2 etc
                } else {
                    break; // straight was broken, try again
                }
            }
            if (candidates.size() == 5) {
//                Collections.sort(candidates);
                return Optional.of(candidates);
            }
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
        return String.format("%s-high straight", cards.get(0).rank.verboseName());
    }
}
