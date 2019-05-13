package model.tests.tiletests;

import model.card.perkcard.PerkCard;
import model.configreader.PerkConfig;
import model.player.Player;
import model.tile.PerkTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PerkTileTest {
    Player player = new Player("BOB", 0, 1000, false);
    PerkTile perkTile = new PerkTile(new String[]{"33","CommunityPerkTile","Community Chest","0","0","communityChest.png"});
    PerkConfig perkConfig = new PerkConfig();
    List<PerkCard> perkCardList = perkConfig.makePerkCardsFromXML(new File("data/gamefiles/standard40tile/StandardPerksInfo.xml"));

    @BeforeEach
    public void setup(){
        perkTile.setDeck(perkCardList);
    }

    @Test
    public void sortDeckWorks(){
        int expected1 = 16;
        int actual1 = perkTile.sortPerkCards("chance",perkCardList).size();
        int expected2 = 17;
        int actual2 = perkTile.sortPerkCards("community",perkCardList).size();
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
    }

    @Test
    public void drawCardGetsCard(){
        boolean cardDrawn = false;
        perkTile.drawCard(player,perkCardList);
        if(! perkTile.getDrawnCardContent().equals(null)){
            cardDrawn = true;
        }
        boolean expected = true;
        assertEquals(expected,cardDrawn);
    }
}