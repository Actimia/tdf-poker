package se.edstrompartners.cards;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class PotTest {

    private static class TestPlayer extends Player {
        public TestPlayer(String s) {
            super(s);
        }

        public TestPlayer(String s, int i) {
            super(s);
            chips = i;
        }

        @Override
        public String toString() {
            return getName() + "(" + chips + ")";
        }
    }

    @Test
    public void testPot() {
        Pot pot = new Pot();
        List<Player> players = new ArrayList<>();
        Player p1 = new TestPlayer("1");
        players.add(p1);
        Player p2 = new TestPlayer("2");
        players.add(p2);
        Player p3 = new TestPlayer("3");
        players.add(p3);
        Player p4 = new TestPlayer("4");
        players.add(p4);

        p3.chips -= pot.bet(p3, 50);
        p4.chips -= pot.bet(p4, 100);

        p1.chips -= pot.bet(p1, 150);
        p2.chips -= pot.bet(p2, pot.toCall(p2));
        p3.chips -= pot.bet(p3, pot.toCall(p3));
        p4.chips -= pot.bet(p4, pot.toCall(p4));

        System.out.println(pot);
        assertTrue(pot.bettingDone());
        assertTrue(pot.getCurrent() == 150);

        p1.chips -= pot.bet(p1, 100);
        p2.chips -= pot.bet(p2, pot.toCall(p2) + 200);
        p3.chips -= pot.bet(p3, pot.toCall(p3));
        p4.chips -= pot.bet(p4, pot.toCall(p4));

        assertTrue(!pot.bettingDone());

        p1.chips -= pot.bet(p1, pot.toCall(p1));
        System.out.println(pot);
        assertTrue(pot.bettingDone());
    }

    @Test
    public void testPotAllin() {
        Pot pot = new Pot();
        List<Player> players = new ArrayList<>();
        Player p1 = new TestPlayer("1", 500);
        players.add(p1);
        Player p2 = new TestPlayer("2", 250);
        players.add(p2);
        Player p3 = new TestPlayer("3", 400);
        players.add(p3);
        Player p4 = new TestPlayer("4", 600);
        players.add(p4);

        p3.chips -= pot.bet(p3, 50);
        p4.chips -= pot.bet(p4, 100);

        p1.chips -= pot.bet(p1, 300);
//        printPot(pot);

        assertTrue(pot.toCall(p2) == 300);
        p2.chips -= pot.bet(p2, pot.toCall(p2));
//        printPot(pot);
        assertTrue(p2.chips == 0);

        assertTrue(pot.toCall(p3) == 250);

        p3.chips -= pot.bet(p3, pot.toCall(p3));
        p4.chips -= pot.bet(p4, pot.toCall(p4));
        System.out.println(pot);
        assertTrue(pot.bettingDone());
    }

    @Test
    public void testPotAllin2() {
        Pot pot = new Pot();
        List<Player> players = new ArrayList<>();
        Player p1 = new TestPlayer("1", 500);
        players.add(p1);
        Player p2 = new TestPlayer("2", 550);
        players.add(p2);
        Player p3 = new TestPlayer("3", 550);
        players.add(p3);
        Player p4 = new TestPlayer("4", 550);
        players.add(p4);

        p3.chips -= pot.bet(p3, 50);
        p4.chips -= pot.bet(p4, 100);

        p1.chips -= pot.bet(p1, 500);
//        printPot(pot);

        assertTrue(pot.toCall(p2) == 500);
        p2.chips -= pot.bet(p2, pot.toCall(p2));
//        printPot(pot);
//        assertTrue(p2.chips == 0);

        assertTrue(pot.toCall(p3) == 450);

        p3.chips -= pot.bet(p3, pot.toCall(p3));
        p4.chips -= pot.bet(p4, pot.toCall(p4));
//        printPot(pot);
        assertTrue(pot.bettingDone());
    }
}
