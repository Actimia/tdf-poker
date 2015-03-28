package se.edstrompartners.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
        int hands = 0;
        while (players.size() > 1) {
            Round round = new Round(this);
            round.play();
            hands++;
            Iterator<Player> pit = players.iterator();
            while (pit.hasNext()) {
                Player p = pit.next();
                p.getHand().clear();
                if (p.chips <= 0) {
                    System.out.println(p.name + " has been eliminated!");
                    pit.remove();
                }
            }
            Collections.rotate(players, 1);
        }
        Player winner = players.get(0);
        System.out.println(winner.name + " wins after " + hands + " hands! Congratulations!");
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
