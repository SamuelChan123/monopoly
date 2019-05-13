package model.tests.wintests;

import model.player.Player;
import model.win.WinStandard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WinStandardTest {
    WinStandard winStandard = new WinStandard();
    Player player;
    Player otherPlayer;
    List<Player> players;

    @BeforeEach
    public void setup(){
        player = new Player("JOE", 0, 1000, false);
        otherPlayer = new Player("JOE's Mortal Enemy", 0, 1000, false);
        players = new ArrayList();
        players.add(player);
        players.add(otherPlayer);
    }

    @Test
    public void isGameEndTest(){
        assertEquals(false,winStandard.isGameEnd(players));
        players.remove(otherPlayer);
        assertEquals(true,winStandard.isGameEnd(players));
    }

    @Test
    public void whoIsWinnerTest(){
        players.remove(otherPlayer);
        assertEquals(player,winStandard.whoIsWinner(players));
    }
}
