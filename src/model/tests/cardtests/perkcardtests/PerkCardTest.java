package model.tests.cardtests.perkcardtests;

import model.card.perkcard.ChangeFundsPerkCard;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PerkCardTest {
    List<String> attributeList = new ArrayList<>();
    Player player = new Player("BOB", 0, 1000, false);
    ChangeFundsPerkCard changeFundsPerkCard;

    @BeforeEach
    public void setup(){
        attributeList.add("chance");
        attributeList.add("You lose $1000");
        attributeList.add("ChangeFundsPerkCard");
        attributeList.add("-1000");
        attributeList.add("0");
        changeFundsPerkCard = new ChangeFundsPerkCard(attributeList);
    }

    @Test
    public void cardsFormCorrect(){
        String expected = "chanceYou lose $1000ChangeFundsPerkCard-10000";
        String actual = "";
        actual += changeFundsPerkCard.getType();
        actual += changeFundsPerkCard.getText();
        actual += changeFundsPerkCard.getAction();
        actual += changeFundsPerkCard.getChangeFund();
        actual += changeFundsPerkCard.getMove();
        assertEquals(expected,actual);
    }
}
