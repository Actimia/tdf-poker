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
                Collections.sort(candidates);
                return Optional.of(candidates);
            }
        }
        return Optional.empty();
    }

    @Override
    public ScoringHand.Kind kind() {
        return ScoringHand.Kind.STRAIGHT;
    }
}
