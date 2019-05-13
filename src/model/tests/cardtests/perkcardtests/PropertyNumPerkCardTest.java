package model.tests.cardtests.perkcardtests;

import model.card.perkcard.PropertyNumPerkCard;
import model.card.propertycard.RealEstatePropertyCard;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertyNumPerkCardTest {
    List<String> attributeList = new ArrayList<>();
    List<Integer> rentInfo = new ArrayList<>();
    RealEstatePropertyCard realEstatePropertyCard1;
    RealEstatePropertyCard realEstatePropertyCard2;
    RealEstatePropertyCard realEstatePropertyCard3;
    Player player = new Player("BOB", 0, 1000, false);
    PropertyNumPerkCard propertyNumPerkCard;

    @BeforeEach
    public void setup(){
        attributeList.add("chance");
        attributeList.add("Make general repairs on all your property–For each house pay $25–For each hotel $100");
        attributeList.add("PropertyNumPerkCard");
        attributeList.add("25");
        attributeList.add("100");
        rentInfo.add(50);
        rentInfo.add(100);
        rentInfo.add(150);
        rentInfo.add(200);
        rentInfo.add(250);
        realEstatePropertyCard1 = new RealEstatePropertyCard("A",1,1,1,1,1,rentInfo,1,1);
        realEstatePropertyCard2 = new RealEstatePropertyCard("B",2,2,2,2,2,rentInfo,2,2);
        realEstatePropertyCard3 = new RealEstatePropertyCard("C",3,3,3,3,3,rentInfo,3,3);
        propertyNumPerkCard = new PropertyNumPerkCard(attributeList);
        realEstatePropertyCard1.addHouse();
        realEstatePropertyCard1.addHouse();
        realEstatePropertyCard1.addHouse();
        realEstatePropertyCard1.addHouse();
        realEstatePropertyCard1.addHouse();
        realEstatePropertyCard2.addHouse();
        realEstatePropertyCard2.addHouse();
        realEstatePropertyCard3.addHouse();
        player.addToProperties(realEstatePropertyCard1);
        player.addToProperties(realEstatePropertyCard2);
        player.addToProperties(realEstatePropertyCard3);
    }

    @Test
    public void correctNumberOfPropertiesInMap(){
        int expected = 3;
        int actual = player.getPropertiesMap().size();
        assertEquals(expected,actual);
    }

    @Test
    public void correctPaymentMade(){
        int expected1 = 1000;
        int actual1 = player.getBalance();
        propertyNumPerkCard.perkCardAction(player);
        int expected2 = 825;
        int actual2 = player.getBalance();
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
    }
}
