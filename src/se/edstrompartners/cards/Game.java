package se.edstrompartners.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static se.edstrompartners.cards.Util.iota;

public class Game {
    private List<Player> players = new ArrayList<>();

    /**
     * Creates a new game with new players.
     * Supports up to 15 players.
     *
     * @param numplayers The number of players to generate.
     */
    public Game(int numplayers, String playername) {
        if (numplayers > 15) {
            throw new IllegalArgumentException("Too many players!");
        }
        Player.resetNameGen();
        iota(numplayers).forEach(i -> players.add(new Player()));
        Player player = new HumanPlayer(playername);
        players.add(player);
        Collections.shuffle(players);
    }

    public void play() {
        Round round = new Round(this);
        round.play();
    }

    public void playNaive() {
        Round round = new Round(this);
        System.out.println(round);
        System.out.println(round.checkWinner());

        System.out.println();
        System.out.println();

        round.dealFlop();
        System.out.println(round);
        System.out.println(round.checkWinner());

        System.out.println();
        System.out.println();

        round.dealTurn();
        System.out.println(round);
        System.out.println(round.checkWinner());

        System.out.println();
        System.out.println();

        round.dealRiver();
        System.out.println(round);
        System.out.println(round.checkWinner());
    }

    public List<Player> getPlayers() {
        return players;
    }
}
