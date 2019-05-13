package model.tests.cardtests.perkcardtests;

import model.card.perkcard.MoveJailPerkCard;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveJailPerkCardTest {
    List<String> attributeList = new ArrayList<>();
    Player player = new Player("BOB", 0, 1000, false);
    MoveJailPerkCard moveJailPerkCard;

    @BeforeEach
    public void setup(){
        attributeList.add("chance");
        attributeList.add("Go to Jail–Go directly to Jail–Do not pass Go, do not collect $200");
        attributeList.add("MoveJailPerkCard");
        attributeList.add("0");
        attributeList.add("0");
        moveJailPerkCard = new MoveJailPerkCard(attributeList);
    }

    @Test
    public void cardMovesPosition(){
        int expected1 = 0;
        int actual1 = player.getPosition();
        moveJailPerkCard.perkCardAction(player);
        int expected2 = moveJailPerkCard.getJailTile().getPosition();
        int actual2 = player.getPosition();
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
    }

    @Test
    public void cardAddsPlayerToJail(){
        int expected1 = 0;
        int actual1 = moveJailPerkCard.getJailTile().getPlayersInJail().size();
        moveJailPerkCard.perkCardAction(player);
        int expected2 = 1;
        int actual2 = moveJailPerkCard.getJailTile().getPlayersInJail().size();
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
    }
}
