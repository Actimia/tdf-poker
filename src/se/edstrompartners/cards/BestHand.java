package se.edstrompartners.cards;

import se.edstrompartners.cards.scoring.ScoringHand;

public class BestHand implements Comparable<BestHand> {
    public Player player;
    public ScoringHand hand;

    public BestHand(Player p, ScoringHand s) {
        this.player = p;
        this.hand = s;
    }

    /**
     * Compares two hands to see which is best. Inconsistent with equals.
     */
    @Override
    public int compareTo(BestHand o) {
        return hand.compareTo(o.hand);
    }

    @Override
    public String toString() {
        return player.getName() + " with " + hand.verboseName() + ": \n\t" + Util.formatCardList(hand.cards
                ());
    }
}
