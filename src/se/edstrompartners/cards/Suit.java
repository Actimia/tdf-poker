package se.edstrompartners.cards;

public enum Suit {
    SPADES("\u001B[30;47m\u2660"), HEARTS("\u001b[31;47m\u2665"), DIAMONDS
            ("\u001B[31;47m\u2666"), CLUBS("\u001B[30;47m\u2663");

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
