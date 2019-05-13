package model.tests.tiletests;

import model.card.perkcard.ChangeFundsPerkCard;
import model.player.Player;
import model.tile.FreeParkTile;
import model.tile.TaxTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FreeParkTileTest {
    Player player1 = new Player("BOB", 0, 1000, false);
    Player player2 = new Player("BOB's Ultimate Nemesis", 0, 1000, false);
    FreeParkTile freeParkTile = new FreeParkTile(new String[]{"20","FreeParkTile","Free Parking","0","0","freeparking.png","Free Parking"});
    TaxTile taxTile = new TaxTile(new String[]{"4","TaxTile","Income Tax","11","400","money.png"});
    ChangeFundsPerkCard changeFundsPerkCard;
    List<String> attributeList = new ArrayList<>();

    @BeforeEach
    public void setup(){
        attributeList.add("chance");
        attributeList.add("You lose $100 for whatever reason");
        attributeList.add("ChangeFundsPerkCard");
        attributeList.add("-100");
        attributeList.add("0");
        changeFundsPerkCard = new ChangeFundsPerkCard(attributeList);
        taxTile.setFreeParkTile(freeParkTile);
        changeFundsPerkCard.setFreeParkTile(freeParkTile);
        taxTile.performAction(player1);
        changeFundsPerkCard.perkCardAction(player1);
    }

    @Test
    public void correctAmountAddedToFreeParking(){
        int expected = 500;
        int actual = freeParkTile.getParkingMoney();
        assertEquals(expected,actual);
    }

    @Test
    public void freeParkingPaysPlayerUponLanding(){
        int expected = 1500;
        freeParkTile.performAction(player2);
        int actual = player2.getBalance();
        assertEquals(expected,actual);
    }
}
