package model.tests.tiletests;

import model.player.Player;
import model.tile.FreeParkTile;
import model.tile.TaxTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TaxTileTest {
    TaxTile taxTile = new TaxTile(new String[]{"4","TaxTile","Income Tax","11","400","money.png"});
    Player player = new Player("BOB", 0, 1000, false);
    FreeParkTile freeParkTile = new FreeParkTile(new String[]{"20","FreeParkTile","Free Parking","0","0","freeparking.png","Free Parking"});

    @BeforeEach
    public void setup(){
        taxTile.setFreeParkTile(freeParkTile);
    }

    @Test
    public void taxTileGivesCorrectAmountBasedOnTaxAmount(){
        int expected = 600;
        taxTile.performAction(player);
        int actual = player.getBalance();
        assertEquals(expected,actual);
    }
}
