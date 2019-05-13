package model.tests.cardtests.perkcardtests;

import model.card.perkcard.NearUtilityTilesPerkCard;
import model.card.propertycard.PropertyCard;
import model.configreader.BoardConfig;
import model.configreader.PropertyConfig;
import model.player.Player;
import model.tile.UtilityTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NearUtilityTilesPerkCardTest {
    List<String> attributeList = new ArrayList<>();
    BoardConfig boardConfig = new BoardConfig();
    PropertyConfig propertyConfig = new PropertyConfig();
    Player player = new Player("BOB", 0, 1000, false);
    Player otherPlayer = new Player("Bob's Mortal Enemy", 0, 1000, false);
    List<Player> otherPlayers = new ArrayList<>();
    NearUtilityTilesPerkCard nearUtilityTilesPerkCard;
    List<PropertyCard> propertyCardList;
    UtilityTile utilityTile;
    PropertyCard utilitiesPropertyCard;

    @BeforeEach
    public void setup(){
        otherPlayers.add(otherPlayer);
        player.setOtherPlayers(otherPlayers);
        attributeList.add("chance");
        attributeList.add("Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times the amount thrown.");
        attributeList.add("NearUtilityTilesPerkCard");
        attributeList.add("10");
        attributeList.add("0");
        nearUtilityTilesPerkCard = new NearUtilityTilesPerkCard(attributeList);
        nearUtilityTilesPerkCard.setBoard(boardConfig.makeBoardFromXML(new File("data/gamefiles/standard40tile/StandardTilesLayout.xml")));
        propertyCardList = propertyConfig.makePropertiesInfoFromXML(new File("data/gamefiles/standard40tile/StandardPropertiesInfo.xml"));
        utilityTile = ((UtilityTile) nearUtilityTilesPerkCard.getBoard().getBoardMap().get(12));
        utilitiesPropertyCard = propertyCardList.get(26);
        utilityTile.setCard(utilitiesPropertyCard);
        utilityTile.setCardOwned(true);
        otherPlayer.addToProperties(utilitiesPropertyCard);
    }

    @Test
    public void findNearestTilePositionIsCorrect(){
        player.changePosition(20);
        int expected = 28;
        int actual = nearUtilityTilesPerkCard.findNearestTilePosition("UtilityTile",player,nearUtilityTilesPerkCard.getBoard());
        assertEquals(expected,actual);
    }

    @Test
    public void findNearestTilePositionIsCorrectEdgeCaseLoopAround(){
        player.changePosition(37);
        int expected = 12;
        int actual = nearUtilityTilesPerkCard.findNearestTilePosition("UtilityTile",player,nearUtilityTilesPerkCard.getBoard());
        assertEquals(expected,actual);
    }

    @Test
    public void playerMovesCorrectPositionEdgeCase(){
        player.changePosition(37);
        nearUtilityTilesPerkCard.perkCardAction(player);
        int expected = 12;
        int actual = player.getPosition();
        assertEquals(expected,actual);
    }

    @Test
    public void playerPaysCorrectAmount(){
        player.changePosition(2);
        int expected1 = 1000;
        int actual1 = player.getBalance();
        for(int i = 0; i < nearUtilityTilesPerkCard.getChangeFund(); i++){
            int rent = utilitiesPropertyCard.calculateRent(4,otherPlayer.getNumOwned(utilitiesPropertyCard));
            player.changeBalance(-1*rent);
            otherPlayer.changeBalance(rent);
        }
        int expected2 = 840;
        int actual2 = player.getBalance();
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
    }
}
