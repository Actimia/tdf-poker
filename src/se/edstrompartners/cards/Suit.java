package se.edstrompartners.cards;

/**
 * Created by actim_000 on 2015-03-26.
 */
public enum Suit {
    SPADES("\u001B[30;47m\u2660"), HEARTS("\u001b[31;47m\u2665"), DIAMONDS
            ("\u001B[31;47m\u2666"), CLUBS("\u001B[30;47m\u2663");

    String symbol;

    Suit(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
