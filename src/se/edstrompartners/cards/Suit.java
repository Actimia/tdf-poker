package se.edstrompartners.cards;

public enum Suit {
    SPADES("\u001B[30;47m\u2660\u001B[0m"), HEARTS("\u001b[31;47m\u2665\u001B[0m"), DIAMONDS
            ("\u001B[36;47m\u2666\u001B[0m"), CLUBS("\u001B[32;47m\u2663\u001B[0m");

    String symbol;

    Suit(String symbol) {
        this.symbol = symbol;
    }

    public String verboseName() {
        return name().toLowerCase();
    }

    @Override
    public String toString() {
        return symbol;
    }
}
