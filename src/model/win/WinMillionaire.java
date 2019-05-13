package model.win;

import model.player.Player;
import java.util.List;

public class WinMillionaire implements WinCondition {

    Player winner;

    public boolean isGameEnd(List<Player> players) {
        if(players.size() == 1) {
            winner = players.get(0);
            return true;
        }
        for(Player p : players) {
            if(p.getTotalWealth() >= 1000000) {
                winner = p;
                return true;
            }
        }
        return false;
    }

    public Player whoIsWinner(List<Player> players) {
        if(isGameEnd(players)) {
            return winner;
        }
        throw new IllegalArgumentException("There is no winner!");
    }
}
