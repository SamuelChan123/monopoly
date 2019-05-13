package model.tests.cardtests.perkcardtests;

import model.card.perkcard.MoveSpacePerkCard;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveSpacePerkCardTest {
    List<String> attributeList = new ArrayList<>();
    Player player = new Player("BOB", 10, 1000, false);
    MoveSpacePerkCard moveSpacePerkCard;

    @BeforeEach
    public void setup(){
        attributeList.add("chance");
        attributeList.add("Go Back 3 Spaces");
        attributeList.add("MovePerkCard");
        attributeList.add("0");
        attributeList.add("-3");
        moveSpacePerkCard = new MoveSpacePerkCard(attributeList);
    }

    @Test
    public void cardMovesPlayer(){
        int expected = 7;
        moveSpacePerkCard.perkCardAction(player);
        int actual = player.getPosition();
        assertEquals(expected,actual);
    }
}
