package se.edstrompartners.cards.scoring;

import java.util.*;

import se.edstrompartners.cards.Card;

public class ScoringHand implements Comparable<ScoringHand>{

    public enum Kind{
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

        Kind(HandChecker hc){
            checker = hc;
        }

        private HandChecker checker;

        public Optional<List<Card>> check(List<Card> cards){
            return checker.check(cards);
        }

        public Comparator<List<Card>> comparator(){
            return checker.comparator();
        }
    }

    private final List<Card> cards;
    private final Kind kind;

    private ScoringHand(Kind kind, List<Card> hand, List<Card> cards){
        this.kind = kind;
        ArrayList<Card> total = new ArrayList<>(hand);
        cards.stream().sorted().limit(Math.max(5 - hand.size(), 0)).forEach(total::add);
        this.cards = total;
    }

    static ScoringHand createBestHand(List<Card> cards){
        if(cards.isEmpty()){
            throw new IllegalArgumentException("List can not be empty.");
        }
        List<Kind> kinds = Arrays.asList(Kind.values());
        Collections.reverse(kinds);
        for(Kind k : kinds){
            Optional<List<Card>> oh = k.check(cards);
            if (oh.isPresent()){
                return new ScoringHand(k, oh.get(), cards);
            }
        }
        throw new IllegalStateException("No hand could be found.");
    }


    @Override
    public int compareTo(ScoringHand o) {
        int diff = kind.compareTo(o.kind);
        if (diff == 0) {
            return kind.comparator().compare(cards, o.cards);
        }
        return diff;
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

    Comparator<List<Card>> comparator();
}
