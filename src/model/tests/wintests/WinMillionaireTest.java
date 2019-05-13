package model.tests.wintests;

import model.player.Player;
import model.win.WinMillionaire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WinMillionaireTest {
    WinMillionaire winMillionaire = new WinMillionaire();
    Player player;
    Player otherPlayer;
    List<Player> players;

    @BeforeEach
    public void setup(){
        player = new Player("BOB", 0, 1000, false);
        otherPlayer = new Player("Bob's Mortal Enemy", 0, 1000, false);
        players = new ArrayList();
        players.add(player);
        players.add(otherPlayer);
    }

    @Test
    public void isGameEndTest(){
        assertEquals(false,winMillionaire.isGameEnd(players));
        player.changeBalance(1000000);
        assertEquals(true,winMillionaire.isGameEnd(players));
        player.changeBalance(-1000000);
        players.remove(otherPlayer);
        assertEquals(true,winMillionaire.isGameEnd(players));
    }

    @Test
    public void whoIsWinnerTest(){
        player.changeBalance(1000000);
        assertEquals(true,winMillionaire.isGameEnd(players));
        assertEquals(player,winMillionaire.whoIsWinner(players));
    }
}
