package model.win;

import model.player.Player;
import java.util.List;

public interface WinCondition {

    boolean isGameEnd(List<Player> players);

    Player whoIsWinner(List<Player> players);
}
