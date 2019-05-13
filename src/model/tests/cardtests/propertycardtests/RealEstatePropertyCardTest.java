package model.tests.cardtests.propertycardtests;

import model.card.propertycard.RealEstatePropertyCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RealEstatePropertyCardTest {
    RealEstatePropertyCard realEstatePropertyCard;
    List<Integer> rentInfo = new ArrayList<>();

    @BeforeEach
    public void setup(){
        rentInfo.add(10);
        rentInfo.add(20);
        rentInfo.add(30);
        rentInfo.add(40);
        rentInfo.add(50);
        realEstatePropertyCard = new RealEstatePropertyCard("Real Estate",1,1,1,1,1,rentInfo,1,1);
    }

    @Test
    public void correctRentReturned(){
        realEstatePropertyCard.addHouse();
        System.out.println(realEstatePropertyCard.getNumHouses());
        realEstatePropertyCard.addHouse();
        realEstatePropertyCard.addHouse();
        int expected = 30;
        int actual = realEstatePropertyCard.calculateRent(2,3);
        assertEquals(expected,actual);
    }

    @Test
    public void correctNumberOfHouses(){
        int expected1 = 0;
        int actual1 = realEstatePropertyCard.getNumHouses();
        realEstatePropertyCard.addHouse();
        realEstatePropertyCard.addHouse();
        int expected2 = 2;
        int actual2 = realEstatePropertyCard.getNumHouses();
        realEstatePropertyCard.removeHouse();
        int expected3 = 1;
        int actual3 = realEstatePropertyCard.getNumHouses();
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
        assertEquals(expected3,actual3);
    }

    @Test
    public void correctNumberOfHotels(){
        realEstatePropertyCard.addHouse();
        realEstatePropertyCard.addHouse();
        realEstatePropertyCard.addHouse();
        realEstatePropertyCard.addHouse();
        realEstatePropertyCard.buildHotel();
        int expected = 1;
        int actual = realEstatePropertyCard.getNumHotels();
        assertEquals(expected,actual);
        realEstatePropertyCard.sellHotel();
        assertEquals(0,realEstatePropertyCard.getNumHotels());
        assertEquals(4,realEstatePropertyCard.getNumberOfBuildings());
    }

    @Test
    public void gettersWork(){
        assertEquals(1,realEstatePropertyCard.getBuyingPrice());
        assertEquals(1,realEstatePropertyCard.getSellingPrice());
        assertEquals(1,realEstatePropertyCard.getGroupNum());
        assertEquals(10,realEstatePropertyCard.getRent());
        assertEquals(1,realEstatePropertyCard.getMortgage());
        assertEquals(1,realEstatePropertyCard.getBuildingCost());
        assertEquals("Real Estate",realEstatePropertyCard.toString());
    }

    @Test void getSetThresholdWorks(){
        assertEquals(4,realEstatePropertyCard.getThreshold());
        realEstatePropertyCard.setThreshold(2);
        assertEquals(2,realEstatePropertyCard.getThreshold());
    }
}
