package model.tests.cardtests.perkcardtests;
import model.card.perkcard.PayToPlayersPerkCard;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PayToPlayersPerkCardTest extends NumPlayersPerkCardTest{
    List<String> attributeList = new ArrayList<>();
    List<Player> playerList = new ArrayList<>();
    Player player1 = new Player("BOB", 0, 1000, false);
    Player player2 = new Player("JOE", 0, 1000, false);
    Player player3 = new Player("SAM", 0, 1000, false);
    PayToPlayersPerkCard payToPlayersPerkCard;

    @BeforeEach
    public void setup(){
        attributeList.add("chance");
        attributeList.add("You have been elected Chairman of the Board–Pay each player $50");
        attributeList.add("PayToPlayersPerkCard");
        attributeList.add("50");
        attributeList.add("0");
        payToPlayersPerkCard = new PayToPlayersPerkCard(attributeList);
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        setOtherPlayersFromList(playerList);
        payToPlayersPerkCard.perkCardAction(player1);
    }

    @Test
    public void correctMoneyToPlayers(){
        int expected1 = 1050;
        int actual1 = player2.getBalance();
        int expected2 = 1050;
        int actual2 = player3.getBalance();
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
    }

    @Test
    public void correctMoneyFromPlayer(){
        int expected = 900;
        int actual = player1.getBalance();
        assertEquals(expected,actual);
    }
}