package model.tests.cardtests.perkcardtests;

import model.card.perkcard.JailFreePerkCard;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class JailFreePerkCardTest {
    List<String> attributeList = new ArrayList<>();
    Player player = new Player("BOB", 0, 1000, false);
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
    public void cardAddedToPlayerPerkCardList(){
        int actual1 = player.getPerkCards().size();
        jailFreePerkCard.perkCardAction(player);
        int actual2 = player.getPerkCards().size();
        assertEquals(actual1,0);
        assertEquals(actual2,1);
    }

    @Test
    public void useCardRemovesPlayerFromJail(){
        jailFreePerkCard.getJailTile().addPersonToJail(player);
        int actual1 = jailFreePerkCard.getJailTile().getPlayersInJail().size();
        String actual2 = jailFreePerkCard.getJailTile().getPlayersInJail().get(0).getName();
        jailFreePerkCard.useCard(player);
        int actual3 = jailFreePerkCard.getJailTile().getPlayersInJail().size();
        assertEquals(actual1,1);
        assertEquals(actual2,"BOB");
        assertEquals(actual3,0);
    }
}
