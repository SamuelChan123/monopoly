package model.tests.tiletests;

import model.card.propertycard.RailroadPropertyCard;
import model.player.Player;
import model.tile.PropertyTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertyTileTest {
    Player player1 = new Player("BOB", 0, 1000, false);
    Player player2 = new Player("BOB's Evil Half-Brother", 0, 1000, false);
    PropertyTile propertyTile = new PropertyTile(new String[]{"35","RailroadTile","Short Line","9","4","railroad.png"});
    RailroadPropertyCard railroadPropertyCard;
    List<Integer> rentInfo = new ArrayList<>();
    List<Player> otherPlayers = new ArrayList<>();

    @BeforeEach
    public void setup(){
        rentInfo.add(1);
        rentInfo.add(2);
        rentInfo.add(3);
        rentInfo.add(4);
        rentInfo.add(5);
        railroadPropertyCard = new RailroadPropertyCard("Railroad",1,1,1,1,1,rentInfo,1, 0);
        propertyTile.setCard(railroadPropertyCard);
        otherPlayers.add(player2);
        player1.setOtherPlayers(otherPlayers);
    }

    @Test
    public void nothingHappensWhenCardNotOwned(){
        int expected1 = 1000;
        int actual1 = player1.getBalance();
        propertyTile.performAction(player1);
        int expected2 = 1000;
        int actual2 = player1.getBalance();
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
    }

    @Test
    public void payOwnerCorrectAmount(){
        int expected1 = 1000;
        int actual1 = player1.getBalance();
        player2.addToProperties(railroadPropertyCard);
        propertyTile.setCardOwned(true);
        propertyTile.performAction(player1);
        int expected2 = 999;
        int actual2 = player1.getBalance();
        int expected3 = 1001;
        int actual3 = player2.getBalance();
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
        assertEquals(expected3,actual3);
    }
}
