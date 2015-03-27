package se.edstrompartners.cards;

import java.util.Comparator;

public class Card implements Comparable<Card> {
    public final Suit suit;
    public final Rank rank;

    public Card(Suit s, Rank r) {
        suit = s;
        rank = r;
    }

    public String verboseName() {
        return String.format("%s of %s", rank.verboseName(), suit.verboseName());
    }

    @Override
    public String toString() {
        return "\u001b[;30;47m" + rank.toString() + suit.toString() + "\u001b[0m";
    }

    // compiler cannot determine types if this is a one liner
    private static final Comparator<Card> fuckingcompiler = Comparator.comparing(c -> c.rank);
    public static final Comparator<Card> SUIT_INSENSITIVE = fuckingcompiler.reversed();

    public static final Comparator<Card> SUIT_SENSITIVE = SUIT_INSENSITIVE.thenComparing(c -> c.suit);

    @Override
    public int compareTo(Card o) {
        return SUIT_SENSITIVE.compare(this, o);
    }


    @Override
    public int hashCode() {
        return 20 * suit.ordinal() + rank.ordinal();
    }

    @Override
    public boolean equals(Object c) {
        return c != null && c.getClass() == this.getClass() && ((Card) c).suit == suit && ((Card) c).rank == rank;
    }
}

