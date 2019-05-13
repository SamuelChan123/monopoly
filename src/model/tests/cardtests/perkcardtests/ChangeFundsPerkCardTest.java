package model.tests.cardtests.perkcardtests;
import model.card.perkcard.ChangeFundsPerkCard;
import model.player.Player;
import model.tile.FreeParkTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeFundsPerkCardTest {
    List<String> attributeList = new ArrayList<>();
    List<String> attributeList2 = new ArrayList<>();
    Player player = new Player("BOB", 0, 1000, false);
    ChangeFundsPerkCard changeFundsPerkCard;
    ChangeFundsPerkCard changeFundsPerkCard2;

    @BeforeEach
    public void setup(){
        attributeList.add("chance");
        attributeList.add("Bank pays you dividend of $50");
        attributeList.add("ChangeFundsPerkCard");
        attributeList.add("50");
        attributeList.add("0");
        attributeList2.add("chance");
        attributeList2.add("Bank takes away balance of $50");
        attributeList2.add("ChangeFundsPerkCard");
        attributeList2.add("-50");
        attributeList2.add("0");
        changeFundsPerkCard = new ChangeFundsPerkCard(attributeList);
        changeFundsPerkCard2 = new ChangeFundsPerkCard(attributeList2);
        changeFundsPerkCard2.setFreeParkTile(new FreeParkTile(new String[]{"20","FreeParkTile","Free Parking","0","0","freeparking.png","Free Parking"}));
    }

    @Test
    public void cardAddsMoney(){
        int expected = 1050;
        changeFundsPerkCard.perkCardAction(player);
        int actual = player.getBalance();
        assertEquals(expected,actual);
    }

    @Test
    public void cardsSubtractsMoney(){
        int expected = 950;
        changeFundsPerkCard2.perkCardAction(player);
        int actual = player.getBalance();
        assertEquals(expected,actual);
    }
}
