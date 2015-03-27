package se.edstrompartners.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by actim_000 on 2015-03-26.
 */
public class Player {

    private static final String HIDDEN_CARDS = "\u001B[30;47m??\u001B[0m  \u001B[30;" +
            "47m??\u001B[0m ";

    protected List<Card> hand;
    protected String name;
    protected int chips = 1000;
    protected boolean hidden = true;
//    protected Role role = Role.NORMAL;

    public Player() {
        this(names.remove(0));
    }

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
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

    public int makePlay(int toCall, Round r) {
        return toCall;
    }

    @Override
    public String toString() {
        ArrayList<Card> sorted = new ArrayList<>(hand);
        sorted.sort(Card.SUIT_SENSITIVE);
        String showcards = sorted.stream().map(Card::toString).collect(Collectors.joining(" "));
        return String.format("%-12s%s%8d",
                name, showcards, chips);
    }


    public String getRoundState() {
        ArrayList<Card> sorted = new ArrayList<>(hand);
        sorted.sort(Card.SUIT_SENSITIVE);
        String showcards = sorted.stream().map(Card::toString).collect(Collectors.joining(" "));
        return String.format("%-12s%s%8d",
                name, hidden ? HIDDEN_CARDS : showcards, chips);
    }

    public int addChips(int amount) {
        chips += amount;
        return chips;
    }

    public int removeChips(int amount) {
        chips -= amount;
        return chips;
    }

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

    public static void resetNameGen() {
        names = generateNames();
    }
}
