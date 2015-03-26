package se.edstrompartners.cards.scoring;

import se.edstrompartners.cards.Card;
import se.edstrompartners.cards.Rank;

import java.util.*;
import java.util.stream.Collectors;

public class SetChecker implements HandChecker{

    @Override
    public Optional<List<Card>> check(List<Card> cards) {
        Map<Rank, List<Card>> map = cards.stream()
                .collect(Collectors.groupingBy(c -> c.rank));
        // almost the same code as for Pair, check that code for comments.
        return map.keySet().stream()
                .sorted(Collections.reverseOrder())
                .map(map::get)
                .filter(cs -> cs.size() >= 3)
                .findFirst();
//                .map(hand -> new ScoringHand(ScoringHand.Kind.SET, hand, cards));
    }

    @Override
    public Comparator<List<Card>> comparator() {
        return null;
    }
}
