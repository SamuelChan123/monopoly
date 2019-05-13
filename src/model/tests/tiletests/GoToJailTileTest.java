package model.tests.tiletests;

import model.player.Player;
import model.tile.GoToJailTile;
import model.tile.JailTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoToJailTileTest {
    Player player = new Player("BOB", 0, 1000, false);
    GoToJailTile goToJailTile = new GoToJailTile(new String[]{"30","GoToJailTile","Go to Jail","0","0","handcuff.png","Go To Jail"});
    JailTile jailTile = new JailTile(new String[]{"10","JailTile","Jail","0","0","jail.png","This is Jail"});

    @BeforeEach
    public void setup(){
        goToJailTile.setJailTile(jailTile);
    }

    @Test
    public void tileMovesPlayerToCorrectPositionAndPutsThemInJail(){
        int expected1 = 0;
        int actual1 = jailTile.getPlayersInJail().size();
        goToJailTile.performAction(player);
        int expected2 = 10;
        int actual2 = player.getPosition();
        int expected3 = 1;
        int actual3 = jailTile.getPlayersInJail().size();
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
        assertEquals(expected3,actual3);
    }
}
