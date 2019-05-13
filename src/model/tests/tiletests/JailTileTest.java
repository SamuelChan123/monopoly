package model.tests.tiletests;

import model.card.perkcard.JailFreePerkCard;
import model.player.Player;
import model.tile.JailTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JailTileTest {
    Player player1 = new Player("BOB", 0, 1000, false);
    Player player2 = new Player("BOB's Evil Half-Brother", 0, 1000, false);
    JailTile jailTile = new JailTile(new String[]{"10","JailTile","Jail","0","0","jail.png","This is Jail"});
    List<String> attributeList = new ArrayList<>();
    JailFreePerkCard jailFreePerkCard;

    @BeforeEach
    public void setup(){
        attributeList.add("chance");
        attributeList.add("Get Out of Jail Free");
        attributeList.add("JailFreePerkCard");
        attributeList.add("0");
        attributeList.add("0");
        jailFreePerkCard = new JailFreePerkCard(attributeList);
    }

    @Test
    public void correctAmountOfPeopleInJail(){
        int expected1 = 0;
        int actual1 = jailTile.getPlayersInJail().size();
        jailTile.addPersonToJail(player1);
        jailTile.addPersonToJail(player2);
        int expected2 = 2;
        int actual2 = jailTile.getPlayersInJail().size();
        jailTile.removePersonFromJail(player1);
        int expected3 = 1;
        int actual3 = jailTile.getPlayersInJail().size();
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
        assertEquals(expected3,actual3);
    }

    @Test void doNotAddToJailIfPlayerHasJailFreeCard(){
        int expected1 = 0;
        int actual1 = jailTile.getPlayersInJail().size();
        player2.addToPerks(jailFreePerkCard);
        int expected2 = 1;
        int actual2 = player2.getPerkCards().size();
        jailTile.addPersonToJail(player2);
        int expected3 = 0;
        int actual3 = jailTile.getPlayersInJail().size();
        int expected4 = 0;
        int actual4 = player2.getPerkCards().size();
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
        assertEquals(expected3,actual3);
        assertEquals(expected4,actual4);
    }

    @Test
    public void playerGetsOutOfJailIfEnoughTurnsGoBy(){
        jailTile.addPersonToJail(player1);
        int expected1 = 3;
        int actual1 = player1.getTurnsInJail();
        int expected2 = 1;
        int actual2 = jailTile.getPlayersInJail().size();
        jailTile.jailTurn(player1,jailTile);
        int expected3 = 2;
        int actual3 = player1.getTurnsInJail();
        jailTile.jailTurn(player1,jailTile);
        jailTile.jailTurn(player1,jailTile);
        int expected4 = 0;
        int actual4 = jailTile.getPlayersInJail().size();
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
        assertEquals(expected3,actual3);
        assertEquals(expected4,actual4);
    }
}

