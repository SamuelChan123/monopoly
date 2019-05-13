package model.tests.cardtests.perkcardtests;

import model.card.perkcard.NearRailTilesPerkCard;
import model.card.propertycard.PropertyCard;
import model.configreader.BoardConfig;
import model.configreader.PropertyConfig;
import model.player.Player;
import model.tile.RailroadTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NearRailTilesPerkCardTest {
    List<String> attributeList = new ArrayList<>();
    List<PropertyCard> propertyCardList = new ArrayList<>();
    BoardConfig boardConfig = new BoardConfig();
    PropertyConfig propertyConfig = new PropertyConfig();
    Player player = new Player("BOB", 0, 1000, false);
    Player otherPlayer = new Player("Bob's Mortal Enemy", 0, 1000, false);
    List<Player> otherPlayers = new ArrayList<>();
    NearRailTilesPerkCard nearRailTilesPerkCard;
    RailroadTile railroadTile1;
    RailroadTile railroadTile2;
    PropertyCard railroadPropertyCard1;
    PropertyCard railroadPropertyCard2;

    @BeforeEach
    public void setup(){
        otherPlayers.add(otherPlayer);
        player.setOtherPlayers(otherPlayers);
        attributeList.add("chance");
        attributeList.add("Advance token to the nearest Railroad and pay owner twice the rental to which he/she {he} is otherwise entitled. If Railroad is unowned, you may buy it from the Bank.");
        attributeList.add("NearRailTilesPerkCard");
        attributeList.add("2");
        attributeList.add("0");
        nearRailTilesPerkCard = new NearRailTilesPerkCard(attributeList);
        nearRailTilesPerkCard.setBoard(boardConfig.makeBoardFromXML(new File("data/gamefiles/standard40tile/StandardTilesLayout.xml")));
        propertyCardList = propertyConfig.makePropertiesInfoFromXML(new File("data/gamefiles/standard40tile/StandardPropertiesInfo.xml"));
        railroadTile1 = ((RailroadTile) nearRailTilesPerkCard.getBoard().getBoardMap().get(25));
        railroadTile2 = ((RailroadTile) nearRailTilesPerkCard.getBoard().getBoardMap().get(35));
        railroadPropertyCard1 = propertyCardList.get(24);
        railroadPropertyCard2 = propertyCardList.get(25);
        railroadTile1.setCard(railroadPropertyCard1);
        railroadTile2.setCard(railroadPropertyCard2);
        railroadTile1.setCardOwned(true);
        railroadTile2.setCardOwned(true);
        otherPlayer.addToProperties(railroadPropertyCard1);
        otherPlayer.addToProperties(railroadPropertyCard2);
    }

    @Test
    public void findNearestTilePositionIsCorrect(){
        player.changePosition(20);
        int expected = 25;
        int actual = nearRailTilesPerkCard.findNearestTilePosition("RailroadTile",player,nearRailTilesPerkCard.getBoard());
        assertEquals(expected,actual);
    }

    @Test
    public void findNearestTilePositionIsCorrectEdgeCaseLoopAround(){
        player.changePosition(37);
        int expected = 5;
        int actual = nearRailTilesPerkCard.findNearestTilePosition("RailroadTile",player,nearRailTilesPerkCard.getBoard());
        assertEquals(expected,actual);
    }

    @Test
    public void playerMovesCorrectPositionEdgeCase(){
        player.changePosition(37);
        nearRailTilesPerkCard.perkCardAction(player);
        int expected = 5;
        int actual = player.getPosition();
        assertEquals(expected,actual);
    }

    @Test
    public void playerPaysCorrectAmount(){
        player.changePosition(20);
        int expected1 = 1000;
        int actual1 = player.getBalance();
        nearRailTilesPerkCard.perkCardAction(player);
        //System.out.println(player.getPosition());
        int expected2 = 900;
        int actual2 = player.getBalance();
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
    }
}
