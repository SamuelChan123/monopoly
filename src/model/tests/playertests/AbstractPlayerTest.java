package model.tests.playertests;

import model.card.perkcard.ChangeFundsPerkCard;
import model.card.perkcard.PerkCard;
import model.card.propertycard.RealEstatePropertyCard;
import model.dice.Dice;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.tests.cardtests.perkcardtests.NumPlayersPerkCardTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbstractPlayerTest extends NumPlayersPerkCardTest {
    Player testPlayer = new Player("Bob",1,1000, false);
    Player otherPlayer1 = new Player("Enemy",1,1000, false);
    Player otherPlayer2 = new Player("Of My Enemy",1,1000, false);
    RealEstatePropertyCard exampleProperty;
    RealEstatePropertyCard examplePropertyCopy;
    RealEstatePropertyCard exampleProperty2;
    PerkCard examplePerk;
    PerkCard examplePerk2;
    List rentInfo;
    List attributeList;
    Dice dice;
    List<Player> playerList = new ArrayList<>();

    @BeforeEach
    public void setup(){
        dice = new Dice(2,6);
        rentInfo = new ArrayList();
        rentInfo.add(20);
        rentInfo.add(40);
        rentInfo.add(60);
        rentInfo.add(80);
        rentInfo.add(100);
        exampleProperty = new RealEstatePropertyCard("Your Mom's House",1,1,100,50,3,rentInfo,50,100);
        examplePropertyCopy = exampleProperty;
        exampleProperty2 = new RealEstatePropertyCard("Your Mom's Other House",1,2,200,50,3,rentInfo,100,100);
        attributeList = new ArrayList();
        attributeList = new ArrayList();
        attributeList.add("chance");
        attributeList.add("GET OUT OF JAIL FREE WOOHOO KEEP THIS CARD");
        attributeList.add("JailFreePerkCard");
        attributeList.add("0");
        attributeList.add("0");
        examplePerk = new ChangeFundsPerkCard(attributeList);
        examplePerk2 = new ChangeFundsPerkCard(attributeList);
        playerList.add(testPlayer);
        playerList.add(otherPlayer1);
        playerList.add(otherPlayer2);
        setOtherPlayersFromList(playerList);
    }

    @Test
    public void playerConstructorWorks(){
        String expected1 = "Bob";
        int expected2 = 1;
        int expected3 = 1000;
        assertEquals(expected1,testPlayer.getName());
        assertEquals(expected2,testPlayer.getPosition());
        assertEquals(expected3,testPlayer.getBalance());
    }

    @Test
    public void changePositionAndChanceBalanceWork(){
        int expected1 = 3;
        int expected2 = 700;
        testPlayer.changePosition(3);
        testPlayer.changeBalance(-300);
        assertEquals(expected1,testPlayer.getPosition());
        assertEquals(expected2,testPlayer.getBalance());
    }

    @Test
    public void addRemovePropertiesWorks(){
        int expected1 = 0;
        int actual1 = testPlayer.getPropertiesMap().size();
        testPlayer.addToProperties(exampleProperty);
        testPlayer.addToProperties(exampleProperty2);
        int expected2 = 2;
        int actual2 = testPlayer.getPropertiesMap().get(1).size();
        testPlayer.removeFromProperties(exampleProperty2);
        int expected3 = 1;
        int actual3 = testPlayer.getPropertiesMap().size();
        String expected4 = "Your Mom's House";
        String actual4 = testPlayer.getPropertiesMap().get(1).get(0).getTitle();
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
        assertEquals(expected3,actual3);
        assertEquals(expected4,actual4);
    }

    @Test
    public void cannotAddDuplicateProperty(){
        testPlayer.addToProperties(exampleProperty2);
        testPlayer.addToProperties(examplePropertyCopy);
        int expected = 1;
        int actual = testPlayer.getPropertiesMap().size();
        assertEquals(expected,actual);
    }

    @Test
    public void addRemovePerksWorks(){
        int expected1 = 0;
        int actual1 = testPlayer.getPerkCards().size();
        testPlayer.addToPerks(examplePerk);
        testPlayer.addToPerks(examplePerk2);
        int expected2 = 2;
        int actual2 = testPlayer.getPerkCards().size();
        testPlayer.removeFromPerks(examplePerk);
        int expected3 = 1;
        int actual3 = testPlayer.getPerkCards().size();
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
        assertEquals(expected3,actual3);
    }

    @Test
    public void payOtherPlayerWorks(){
        otherPlayer1.addToProperties(exampleProperty);
        testPlayer.payOtherPlayer(exampleProperty,otherPlayer1);
        int expected1 = 980;
        int actual1 = testPlayer.getBalance();
        int expected2 = 1020;
        int actual2 = otherPlayer1.getBalance();
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
    }

    @Test
    public void getOtherPlayersWorks(){
        String expected = "Enemy Of My Enemy";
        String actual = "";
        for(Player otherPlayer : testPlayer.getOtherPlayers()){
            actual += otherPlayer.getName() + " ";
        }
        actual = actual.trim();
        assertEquals(expected,actual);
    }

    @Test
    public void getMortgageWorthWorks(){
        testPlayer.addToProperties(exampleProperty);
        testPlayer.addToProperties(exampleProperty2);
        int expected = 150;
        assertEquals(expected,testPlayer.getMortageWorth());
    }

    @Test
    public void getTotalWealthWorks(){
        testPlayer.addToProperties(exampleProperty);
        testPlayer.addToProperties(exampleProperty2);
        int expected = 1150;
        assertEquals(expected,testPlayer.getTotalWealth());
    }
}
