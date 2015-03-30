package se.edstrompartners.cards;

public class Main {

    public static void main(String[] args) {
//        playAIGame();
        playGame();
//        testTiming();
    }

//    private static void testTiming() {
//        long time = System.currentTimeMillis();
//        int[] wins = new int[ScoringHand.Kind.values().length];
//        for (int i = 0; i < 100000; i++) {
//            if (i % 1000 == 0) {
//                System.out.println(i);
//            }
//            Game.BestHand winner = playGameQuiet();
//            wins[winner.hand.kind().ordinal()]++;
//        }
//        time = System.currentTimeMillis() - time;
//        for (int j = 0; j < wins.length; j++) {
//            //System.out.println(ScoringHand.Kind.values()[j] + ":\t\t\t" + wins[j]);
//            System.out.printf("%-20s%d%n", ScoringHand.Kind.values()[j], wins[j]);
//        }
//        System.out.println("Time elapsed: " + time + " ms");
//    }

//    public static Game.BestHand playGameQuiet() {
//        Game b = new Game(4);
//        b.checkWinner();
//
//        b.dealFlop();
//        b.checkWinner();
//
//        b.dealTurn();
//        b.checkWinner();
//
//        b.dealRiver();
//        return b.checkWinner();
//    }

    public static void playGame() {
        Game game = new Game(4, "Actimia");
        game.play();
    }

    public static void playAIGame() {
        Game game = new Game(5);
        game.play();
    }
}
