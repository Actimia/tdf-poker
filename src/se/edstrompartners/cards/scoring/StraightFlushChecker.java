package se.edstrompartners.cards.scoring;

import se.edstrompartners.cards.Card;
import se.edstrompartners.cards.Suit;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by actim_000 on 2015-03-26.
 */
public class StraightFlushChecker implements HandChecker {
    @Override
    public Optional<List<Card>> check(List<Card> cards) {
        Map<Suit, List<Card>> suits = cards.stream().collect(Collectors.groupingBy(c -> c.suit));

        StraightChecker sc = new StraightChecker();
        return suits.values().stream()
                .flatMap(s -> sc.check(s)
                        .map(sf -> Stream.of(sf))
                        .orElse(Stream.empty()))
                .sorted(ScoringHand.LIST_COMPARATOR)
                .findFirst();

    }

    @Override
    public Comparator<List<Card>> comparator() {
        return null;
    }
}
