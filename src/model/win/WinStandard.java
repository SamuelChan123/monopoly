package model.win;

import model.player.Player;
import java.util.List;

public class WinStandard implements WinCondition {

    public boolean isGameEnd(List<Player> players) {
        return players.size() == 1;
    }

    public Player whoIsWinner(List<Player> players) {
        if(isGameEnd(players)) {
            return players.get(0);
        }
        throw new IllegalArgumentException("More than 1 player still playing!");
    }
}
