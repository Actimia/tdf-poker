package se.edstrompartners.cards;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = Stream.of(Suit.values())
                .flatMap(s -> Stream.of(Rank.values()).map(r -> new Card(s, r)))
                .collect(Collectors.toList());
        Collections.shuffle(cards);
    }

    public Card draw() {
        return cards.remove(cards.size() - 1);
    }

    public Card deal(List<Card> c) {
        Card card = draw();
        c.add(card);
        return card;
    }

    public Card deal(Player p) {
        Card card = draw();
        p.addCard(card);
        return card;
    }


    @Override
    public String toString() {
        return "..." + cards.subList(cards.size() - 10, cards.size()).toString();
    }
}
