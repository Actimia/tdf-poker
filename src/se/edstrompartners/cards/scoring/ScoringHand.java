package se.edstrompartners.cards.scoring;

import se.edstrompartners.cards.Card;

import java.util.*;
import java.util.stream.Collectors;

public class ScoringHand implements Comparable<ScoringHand> {

    public enum Kind {
        HIGHCARD(new HighCardChecker()),
        PAIR(new PairChecker()),
        TWOPAIR(new TwoPairChecker()),
        SET(new SetChecker()),
        STRAIGHT(new StraightChecker()),
        FLUSH(new FlushChecker()),
        FULLHOUSE(new FullHouseChecker()),
        QUADS(new QuadChecker()),
        STRAIGHTFLUSH(new StraightFlushChecker()),
        ROYALFLUSH(new RoyalFlush());

        Kind(HandChecker hc) {
            checker = hc;
        }

        private HandChecker checker;

        public Optional<List<Card>> check(List<Card> cards) {
            return checker.check(cards);
        }

        public Comparator<List<Card>> comparator() {
            return checker.comparator();
        }
    }

    private final List<Card> cards;
    private final Kind kind;

    private ScoringHand(Kind kind, List<Card> hand, List<Card> cards) {
        this.kind = kind;
        this.cards = new ArrayList<>(hand);

        // these two are already sorted correctly and sorting
        // them again can disrupt the correct order
        // the hand 55577 would be sorted as 77555, and 432AK would become AK432
        if (!(kind == Kind.FULLHOUSE || kind == Kind.STRAIGHT)) {
            this.cards.sort(Card.SUIT_SENSITIVE);
        }

        List<Card> rest = cards.stream().sorted()
                .filter(c -> !hand.contains(c))
                .limit(Math.max(5 - hand.size(), 0))
                .collect(Collectors.toList());
        rest.sort(Card.SUIT_SENSITIVE);
        this.cards.addAll(rest);
    }

    public static ScoringHand createBestHand(List<Card> cards) {
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("List can not be empty.");
        }
        List<Kind> kinds = Arrays.asList(Kind.values());
        Collections.reverse(kinds);
        for (Kind k : kinds) {
            Optional<List<Card>> oh = k.check(cards);
            if (oh.isPresent()) {
                return new ScoringHand(k, oh.get(), cards);
            }
        }
        throw new IllegalStateException("No hand could be found.");
    }

    @Override
    public int hashCode() {
        return cards.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (!this.getClass().equals(o.getClass())) return false;
        ScoringHand sh = (ScoringHand) o;
        return this.compareTo(sh) == 0;
    }

    @Override
    public int compareTo(ScoringHand o) {
        int diff = kind.compareTo(o.kind);
        if (diff == 0) {
            return kind.comparator().compare(cards, o.cards);
        }
        return diff;
    }


    public String toString() {
        return kind.toString() + ": " + cards.toString();
    }

    public Kind kind() {
        return kind;
    }

    public List<Card> cards() {
        return cards;
    }

    public static final Comparator<List<Card>> LIST_COMPARATOR = (l1, l2) -> {
        List<Card> lhs = new ArrayList<>(l1);
        lhs.sort(Card.SUIT_INSENSITIVE);
        List<Card> rhs = new ArrayList<>(l2);
        rhs.sort(Card.SUIT_INSENSITIVE);

        Comparator<Card> cardcmp = Card.SUIT_INSENSITIVE.reversed();

        Iterator<Card> lit = lhs.iterator();
        Iterator<Card> rit = rhs.iterator();

        while (lit.hasNext() && rit.hasNext()) {
            Card l = lit.next();
            Card r = rit.next();

            int cmp = cardcmp.compare(l, r);
            if (cmp != 0) {
                return cmp;
            }
        }

        return lit.hasNext() ? 1 : rit.hasNext() ? -1 : 0;
    };
}

interface HandChecker {
    /**
     * Checks whether a collection of cards(any size but not empty) contains a certain hand.
     * Should optionally return the list of cards used for the hand (but not necessarily the rest
     * of the cards) or an empty optional.
     *
     * If multiple hands are possible of the same kind, the best one should be returned, for
     * example in the hand 456789Q, an implementation checking for straights should return 98765
     * and not 87654.
     *
     * A hand should be returned even if it will be superseded by another type of hand, ie a set
     * should be reported as a pair as well, and a full house should also be two pairs.
     *
     * For example, a pair checker should for the input AJ83A25 return AA or possibly (but not
     * required) AAJ85. The order of cards should be in highest-scoring order. For example the hand
     * K3A2Q should be ordered as 32AKQ, not AKQ32, since this straight is counted as 3-high. A
     * full house should have the set before the pair, regardless of actual card ranks.
     *
     * @param cards The list of cards to be checked.
     * @return Optional list of cards used to build the best hand possible of the kind, or empty
     * optional.
     */
    Optional<List<Card>> check(List<Card> cards);

    /**
     * Returns a comparator for comparing hands of this kind. Checking that both hands are of the
     * same type before comparing is the responsibility of the caller.
     * This is used to determine (for example) which one of two pairs are better.
     * Must also include the extra cards (if applicable), for example 88AJ9 should beat 88KDJ.
     *
     * @return A comparator for hands of the same kind.
     */
    Comparator<List<Card>> comparator();
}
