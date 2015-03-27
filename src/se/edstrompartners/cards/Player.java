package se.edstrompartners.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by actim_000 on 2015-03-26.
 */
public class Player {

    private static List<String> names = generateNames();

    private static List<String> generateNames() {
        List<String> names = new ArrayList<>();
        names.add("Hercules");
        names.add("Apollo");
        names.add("Afrodite");
        names.add("Ares");
        names.add("Artemis");
        names.add("Athena");
        names.add("Demeter");
        names.add("Dionysos");
        names.add("Hephaestos");
        names.add("Hera");
        names.add("Hermes");
        names.add("Hestia");
        names.add("Poseidon");
        names.add("Zeus");

        names.add("Odin");
        names.add("Thor");
        names.add("Loki");
        names.add("Freya");
        names.add("Balder");
        names.add("Hodir");
        names.add("Idun");
        names.add("Heimdall");
        names.add("Sigyn");
        names.add("Skadi");

        Collections.shuffle(names);
        return names;
    }

    private List<Card> hand;

    private String name;

    public Player() {
        hand = new ArrayList<>();
        name = names.remove(0);
    }

    public void addCard(Card c) {
        hand.add(c);
    }

    public List<Card> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        ArrayList<Card> sorted = new ArrayList<>(hand);
        sorted.sort(Card.SUIT_SENSITIVE);
        return name + ": " + sorted.toString();
    }

    public static void resetNameGen() {
        names = generateNames();
    }
}
