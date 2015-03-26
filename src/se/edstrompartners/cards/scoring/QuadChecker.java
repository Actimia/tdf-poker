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
        return null;
    }
}
