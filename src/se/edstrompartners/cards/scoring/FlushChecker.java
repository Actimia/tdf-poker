package se.edstrompartners.cards.scoring;

import se.edstrompartners.cards.Card;
import se.edstrompartners.cards.Suit;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FlushChecker implements HandChecker {
    @Override
    public Optional<List<Card>> check(List<Card> cards) {
        Map<Suit, List<Card>> map = cards.stream().collect(Collectors.groupingBy(c -> c.suit));
        return map.values().stream()
                .filter(cs -> cs.size() >= 5)
                .sorted(ScoringHand.LIST_COMPARATOR)
                .findFirst();
    }

    @Override
    public Comparator<List<Card>> comparator() {
        return ScoringHand.LIST_COMPARATOR;
    }
}
