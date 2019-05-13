package model.tests.cardtests.propertycardtests;

import model.card.propertycard.RailroadPropertyCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RailroadPropertyCardTest{
    RailroadPropertyCard railroadPropertyCard;
    List<Integer> rentInfo = new ArrayList<>();

    @BeforeEach
    public void setup(){
        rentInfo.add(1);
        rentInfo.add(2);
        rentInfo.add(3);
        rentInfo.add(4);
        rentInfo.add(5);
        railroadPropertyCard = new RailroadPropertyCard("Railroad",1,1,1,1,1,rentInfo,1, 0);
    }

    @Test
    public void correctPaymentAmount(){
        int expected1 = 1;
        int actual1 = railroadPropertyCard.calculateRent(1,1);
        int expected2 = 3;
        int actual2 = railroadPropertyCard.calculateRent(1,3);
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
    }
}
