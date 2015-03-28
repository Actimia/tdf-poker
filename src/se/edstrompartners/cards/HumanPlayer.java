package se.edstrompartners.cards;

import se.edstrompartners.cards.scoring.ScoringHand;

import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(String name) {
        super(name);
        hidden = false;
    }

    @Override
    public int makePlay(int toCall, Round r) {
        // TODO: validation of bets.
        ScoringHand sh = ScoringHand.createBestHand(r.getHand(this));
        Scanner sc = new Scanner(System.in, "UTF-8");
        r.printCurrentState();
        System.out.println("You currently have a " + sh.verboseName());
        System.out.println("Your turn to bet! " + toCall + " to call!");
        int bet = sc.nextInt();
        return bet;
    }
}
