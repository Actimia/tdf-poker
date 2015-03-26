package se.edstrompartners.cards;

import java.util.Comparator;

public class Card implements Comparable<Card> {
    public final Suit suit;
    public final Rank rank;

    public Card(Suit s, Rank r) {
        suit = s;
        rank = r;
    }

    @Override
    public String toString() {
        return rank.toString() + suit.toString();
    }


    public static final Comparator<Card> SUIT_INSENSITIVE = (o1, o2) ->
            o1.rank.ordinal() - o2.rank.ordinal();

    public static final Comparator<Card> SUIT_SENSITIVE = (o1, o2) ->
            o1.rank.ordinal() - o2.rank.ordinal() == 0 ? o1.suit.ordinal() - o2.suit.ordinal() : 0;

    @Override
    public int compareTo(Card o) {
        return SUIT_SENSITIVE.compare(this, o);
    }

    @Override
    public boolean equals(Object c) {
        return c.getClass() == this.getClass() && ((Card) c).suit == suit && ((Card) c).rank == rank;
    }
}

