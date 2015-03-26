package se.edstrompartners.cards;

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

    @Override
    public int compareTo(Card o) {
        return rank.ordinal() - o.rank.ordinal() == 0 ? suit.ordinal() - o.suit.ordinal() : rank
                .ordinal() - o.rank.ordinal();
    }

    @Override
    public boolean equals(Object c) {
        return c.getClass() == this.getClass() && ((Card) c).suit == suit && ((Card) c).rank == rank;
    }
}

