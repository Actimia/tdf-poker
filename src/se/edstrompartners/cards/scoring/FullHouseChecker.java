package se.edstrompartners.cards.scoring;

import se.edstrompartners.cards.Card;
import se.edstrompartners.cards.Rank;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by actim_000 on 2015-03-26.
 */
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
                        p.addAll(set);
                        return p;
                    });
//                    .map(p -> new ScoringHand(ScoringHand.Kind.FULLHOUSE, p, cards));
        }
        return Optional.empty();
    }
}
