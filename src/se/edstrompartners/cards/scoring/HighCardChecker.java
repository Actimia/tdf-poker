package se.edstrompartners.cards.scoring;

import se.edstrompartners.cards.Card;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by actim_000 on 2015-03-26.
 */
public class HighCardChecker implements HandChecker {
    @Override
    public Optional<List<Card>> check(List<Card> cards) {
        List<Card> cs = cards.stream().sorted().limit(5).collect(Collectors.toList());
        return Optional.of(cs);
    }

    public Comparator<List<Card>> comparator(){
        return ScoringHand.LIST_COMPARATOR;
    }
}
