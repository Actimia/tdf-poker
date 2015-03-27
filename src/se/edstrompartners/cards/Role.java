package se.edstrompartners.cards;

/**
 * Created by actim_000 on 2015-03-27.
 */
public enum Role {
    BIGBLIND("BB"), SMALLBLIND("SB"), DEALER("D"), NORMAL("");

    private String symbol;

    Role(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
