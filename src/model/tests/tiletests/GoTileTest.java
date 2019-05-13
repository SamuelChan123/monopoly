package model.tests.tiletests;

import model.player.Player;
import model.tile.GoTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoTileTest {
    Player player = new Player("BOB", 0, 1000, false);
    GoTile goTile = new GoTile(new String[]{"0","GoTile","Go","0","400","go.png","Go Ahead"});

    @Test
    public void goTileGivesCorrectAmountBasedOnBonusValue(){
        int expected = 1400;
        goTile.performAction(player);
        int actual = player.getBalance();
        assertEquals(expected,actual);
    }
}
