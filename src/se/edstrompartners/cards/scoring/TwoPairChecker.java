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
        if(allpairs.size() == 2){
            List<Card> total = new ArrayList<>();
            allpairs.forEach(p -> total.addAll(p));
            return Optional.of(total);
        }
        return Optional.empty();
    }

    @Override
    public Comparator<List<Card>> comparator() {
        return null;
    }
}
