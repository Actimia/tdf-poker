package se.edstrompartners.cards;

public class Main {

    public static void main(String[] args) {
        Board b = new Board(4);
        System.out.println(b);

        b.dealFlop();
        System.out.println(b);

        b.dealTurn();
        System.out.println(b);

        b.dealRiver();
        System.out.println(b);
    }
}
