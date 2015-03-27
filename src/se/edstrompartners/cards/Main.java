package se.edstrompartners.cards;

import se.edstrompartners.cards.scoring.ScoringHand;

public class Main {

    public static void main(String[] args) {
        playGame();
//        testTiming();
    }

    private static void testTiming() {
        long time = System.currentTimeMillis();
        int[] wins = new int[ScoringHand.Kind.values().length];
        for (int i = 0; i < 100000; i++) {
            if (i % 1000 == 0) {
                System.out.println(i);
            }
            Board.BestHand winner = playGameQuiet();
            wins[winner.s.kind().ordinal()]++;
        }
        time = System.currentTimeMillis() - time;
        for (int j = 0; j < wins.length; j++) {
            //System.out.println(ScoringHand.Kind.values()[j] + ":\t\t\t" + wins[j]);
            System.out.printf("%-20s%d%n", ScoringHand.Kind.values()[j], wins[j]);
        }
        System.out.println("Time elapsed: " + time + " ms");
    }

    public static Board.BestHand playGameQuiet() {
        Board b = new Board(4);
        b.checkWinner();

        b.dealFlop();
        b.checkWinner();

        b.dealTurn();
        b.checkWinner();

        b.dealRiver();
        return b.checkWinner();
    }

    public static void playGame() {
        Board b = new Board(8);
        System.out.println(b);
        System.out.println(b.checkWinner());

        System.out.println();
        System.out.println();

        b.dealFlop();
        System.out.println(b);
        System.out.println(b.checkWinner());

        System.out.println();
        System.out.println();

        b.dealTurn();
        System.out.println(b);
        System.out.println(b.checkWinner());

        System.out.println();
        System.out.println();

        b.dealRiver();
        System.out.println(b);
        System.out.println(b.checkWinner());
    }
}
