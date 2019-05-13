package model.tests.cardtests.perkcardtests;

import model.card.perkcard.GetFromPlayersPerkCard;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetFromPlayersPerkCardTest extends NumPlayersPerkCardTest{
    List<String> attributeList = new ArrayList<>();
    List<Player> playerList = new ArrayList<>();
    Player player1 = new Player("BOB", 0, 1000, false);
    Player player2 = new Player("JOE", 0, 1000, false);
    Player player3 = new Player("SAM", 0, 1000, false);
    GetFromPlayersPerkCard getFromPlayersPerkCard;

    @BeforeEach
    public void setup(){
        attributeList.add("community");
        attributeList.add("Grand Opera Night—Collect $50 from every player for opening night seats");
        attributeList.add("GetFromPlayersPerkCard");
        attributeList.add("50");
        attributeList.add("0");
        getFromPlayersPerkCard = new GetFromPlayersPerkCard(attributeList);
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        setOtherPlayersFromList(playerList);
        getFromPlayersPerkCard.perkCardAction(player1);

    }

    @Test
    public void correctMoneyFromOtherPlayers(){
        int expected1 = 950;
        int actual1 = player2.getBalance();
        int expected2 = 950;
        int actual2 = player3.getBalance();
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
    }

    @Test
    public void correctMoneyToPlayer(){
        int expected = 1100;
        int actual = player1.getBalance();
        assertEquals(expected,actual);
    }
}
