package se.edstrompartners.cards;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Deck {
    List<Card> cards;

    public Deck() {
        cards = Stream.of(Suit.values())
                .flatMap(s -> Stream.of(Rank.values()).map(r -> new Card(s, r)))
                .collect(Collectors.toList());
        Collections.shuffle(cards);
    }

    public Card draw() {
        return cards.remove(cards.size() - 1);
    }

    public Deck deal(List<Card> c) {
        c.add(draw());
        return this;
    }

    public Deck deal(Player p) {
        p.addCard(draw());
        return this;
    }


    @Override
    public String toString() {
        return "..." + cards.subList(cards.size() - 10, cards.size()).toString();
    }
}