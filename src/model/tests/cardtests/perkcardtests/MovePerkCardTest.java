package model.tests.cardtests.perkcardtests;
import model.card.perkcard.MovePerkCard;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovePerkCardTest {
    List<String> attributeList = new ArrayList<>();
    Player player = new Player("BOB", 0, 1000, false);
    MovePerkCard movePerkCard;

    @BeforeEach
    public void setup(){
        attributeList.add("chance");
        attributeList.add("Advance to Illinois Ave—If you pass Go, collect $200");
        attributeList.add("MovePerkCard");
        attributeList.add("0");
        attributeList.add("24");
        movePerkCard = new MovePerkCard(attributeList);
    }

    @Test
    public void cardMovesPlayer(){
        int expected = 24;
        movePerkCard.perkCardAction(player);
        int actual = player.getPosition();
        assertEquals(expected,actual);
    }
}
