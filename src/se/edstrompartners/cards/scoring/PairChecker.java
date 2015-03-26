package se.edstrompartners.cards.scoring;

import se.edstrompartners.cards.Card;
import se.edstrompartners.cards.Rank;

import java.util.*;
import java.util.stream.Collectors;

public class PairChecker implements HandChecker{

    @Override
    public Optional<List<Card>> check(List<Card> cards) {
        Map<Rank, List<Card>> map = cards.stream()
                .collect(Collectors.groupingBy(c -> c.rank));
        return map.keySet().stream()
                .sorted(Collections.reverseOrder()) // find higher pair first
                .map(map::get)
                .filter(cs -> cs.size() >= 2)       // a set is technically a pair too
                .findFirst();
//                .map(hand -> new ScoringHand(ScoringHand.Kind.PAIR, hand, cards));
    }

    @Override
    public Comparator<List<Card>> comparator() {
        return null;
    }
}
