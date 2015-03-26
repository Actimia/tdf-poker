package se.edstrompartners.cards.scoring;

import java.util.*;

import se.edstrompartners.cards.Card;

public class ScoringHand{

    public enum Kind {
        HIGHCARD, PAIR, TWOPAIR, SET, STRAIGHT, FLUSH, FULLHOUSE, QUADS, STRAIGHTFLUSH, ROYALFLUSH;
    }

    private final List<Card> cards;
    private final Kind kind;

    public ScoringHand(Kind kind, List<Card> hand, List<Card> cards){
        this.kind = kind;
        ArrayList<Card> total = new ArrayList<>(hand);
        cards.stream().sorted().limit(Math.max(5 - hand.size(), 0)).forEach(total::add);
        this.cards = total;
    }

    public String toString(){
        return kind.toString();
    }

    public Kind kind(){
        return kind;
    }

    public List<Card> cards(){
        return cards;
    }

    public static final Comparator<List<Card>> LIST_COMPARATOR = (l1, l2) -> {
        List<Card> lhs = new ArrayList<>(l1);
        lhs.sort(Collections.reverseOrder(Card::compareTo));
        List<Card> rhs = new ArrayList<>(l2);
        rhs.sort(Collections.reverseOrder(Card::compareTo));

        Iterator<Card> lit = lhs.iterator();
        Iterator<Card> rit = rhs.iterator();

        while (lit.hasNext() || rit.hasNext()) {
            Card l = lit.next();
            Card r = rit.next();

            int cmp = l.compareTo(r);
            if (cmp != 0) {
                return cmp;
            }
        }

        return lit.hasNext() ? 1 : rit.hasNext() ? -1 : 0;
    };

}

interface HandChecker {
    Optional<List<Card>> check(List<Card> cards);

    ScoringHand.Kind kind();
}
