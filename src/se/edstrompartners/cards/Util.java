package se.edstrompartners.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Util {

    /**
     * Creates a stream of ints ranging from 0 up i (exclusive).
     */
    public static IntStream iota(int i) {
        return IntStream.range(0, i);
    }

    /**
     * Simple DSL for quickly creating lists of cards (mainly for unit testing).
     *
     * Syntax is [SUIT][RANK] as a single letter (ex DQ for queen of diamonds). spaces, commas
     * and 0 is ignored (so 10 is lexed as 1). Some examples of valid sequences are:
     *
     * "DQ HQ C4"
     * "CA,DA,HA,S4,S3"
     * "C10D10HAS4"
     *
     * Returns the cards in the same order as the input.
     */
    public static List<Card> quickHand(String in) {
        in = in.replaceAll("[ ,0]", "");
        in = in.toUpperCase();
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < in.length(); i += 2) {
            char rank = in.charAt(i + 1);
            char suit = in.charAt(i);
            Suit s;
            switch (suit) {
                case 'S':
                    s = Suit.SPADES;
                    break;
                case 'H':
                    s = Suit.HEARTS;
                    break;
                case 'D':
                    s = Suit.DIAMONDS;
                    break;
                case 'C':
                    s = Suit.CLUBS;
                    break;
                default:
                    throw new IllegalArgumentException("Malformed input string");
            }

            Rank r;
            switch (rank) {
                case '2':
                    r = Rank.TWO;
                    break;
                case '3':
                    r = Rank.THREE;
                    break;
                case '4':
                    r = Rank.FOUR;
                    break;
                case '5':
                    r = Rank.FIVE;
                    break;
                case '6':
                    r = Rank.SIX;
                    break;
                case '7':
                    r = Rank.SEVEN;
                    break;
                case '8':
                    r = Rank.EIGHT;
                    break;
                case '9':
                    r = Rank.NINE;
                    break;
                case '1':
                    r = Rank.TEN;
                    break;
                case 'J':
                    r = Rank.JACK;
                    break;
                case 'Q':
                    r = Rank.QUEEN;
                    break;
                case 'K':
                    r = Rank.KING;
                    break;
                case 'A':
                    r = Rank.ACE;
                    break;
                default:
                    throw new IllegalArgumentException("Malformed input string");
            }
            cards.add(new Card(s, r));
        }

        return cards;
    }

    public static String formatCardList(List<Card> cards) {
        return cards.stream()
                .map(Card::toString)
                .collect(Collectors.joining(" "));
    }
}
