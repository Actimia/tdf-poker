package se.edstrompartners.cards;

import se.edstrompartners.cards.scoring.ScoringHand;

public class BestHand implements Comparable<BestHand> {
    public Player p;
    public ScoringHand s;

    public BestHand(Player p, ScoringHand s) {
        this.p = p;
        this.s = s;
    }

    /**
     * Compares two hands to see which is best. Inconsistent with equals.
     */
    @Override
    public int compareTo(BestHand o) {
        return s.compareTo(o.s);
    }

    @Override
    public String toString() {
        return p.getName() + " with " + s.kind() + ": \n\t" + Util.formatCardList(s.cards());
    }
}
