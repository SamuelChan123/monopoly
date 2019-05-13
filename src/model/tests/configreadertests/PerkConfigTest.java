package model.tests.configreadertests;

import model.configreader.PerkConfig;
import model.card.perkcard.JailFreePerkCard;
import model.card.perkcard.PerkCard;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PerkConfigTest {
    PerkConfig testSpace = new PerkConfig();
    File file = new File("data/GameTestFiles/PerksInfoTest.xml");

    @Test
    void createCardMakesRightCardType(){
        List<String> attributeList = new ArrayList<>();
        attributeList.add("chance");
        attributeList.add("WOOHOO YOU GET OUT OF JAIL");
        attributeList.add("JailFreePerkCard");
        attributeList.add("0");
        attributeList.add("0");
        String expected = new JailFreePerkCard(attributeList).getAction();
        String actual = testSpace.createCard(attributeList).getAction();
        assertEquals(expected,actual);
    }

    @Test
    void makeListFromCorrectPerkCards(){
        String expected = "Advance to Go (Collect $200)MovePerkCard,Advance to Illinois Ave—If you pass Go, collect $200MovePerkCard,Advance to St. Charles Place – If you pass Go, collect $200MovePerkCard,Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times the amount thrown.NearUtilityTilesPerkCard,Advance token to the nearest Railroad and pay owner twice the rental to which he/she {he} is otherwise entitled. If Railroad is unowned, you may buy it from the Bank.NearRailTilesPerkCard,";
        String actual = "";
        List<PerkCard> testList = testSpace.makePerkCardsFromXML(file);
        for (int i = 0; i < testList.size(); i++){
            actual += testList.get(i).getText() + testList.get(i).getAction() + ",";
        }
        assertEquals(expected,actual);
    }
}

