package model.tests.cardtests.propertycardtests;

import model.card.propertycard.UtilitiesPropertyCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilitiesPropertyCardTest {
    UtilitiesPropertyCard utilitiesPropertyCard;
    List<Integer> rentInfo = new ArrayList<>();

    @BeforeEach
    public void setup(){
        rentInfo.add(10);
        rentInfo.add(50);
        utilitiesPropertyCard = new UtilitiesPropertyCard("Utilities",1,1,1,1,4,rentInfo,1, 0);
    }

    @Test
    public void correctPaymentSomeOwned(){
        int expect = 20;
        int actual = utilitiesPropertyCard.calculateRent(2,2);
        assertEquals(expect,actual);
    }

    @Test
    public void correctPaymentAllOwned(){
        int expect = 100;
        int actual = utilitiesPropertyCard.calculateRent(2,4);
        assertEquals(expect,actual);
    }
}
