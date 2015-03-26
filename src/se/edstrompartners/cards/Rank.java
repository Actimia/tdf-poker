package se.edstrompartners.cards;

public enum Rank {
    TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), TEN(
            "10"), JACK("J"), QUEEN("Q"), KING("K"), ACE("A");

    String symbol;

    Rank(String symbol) {
        this.symbol = symbol;
    }

    public Rank prev() {
        Rank[] vals = values();
        return vals[(ordinal() - 1 + vals.length) % vals.length];
    }

    public Rank next() {
        Rank[] vals = values();
        return vals[(ordinal() + 1) % vals.length];
    }

    @Override
    public String toString() {
        return symbol;
    }
}

