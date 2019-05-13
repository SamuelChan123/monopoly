package model.tests.configreadertests;

import model.configreader.PropertyConfig;

import model.card.propertycard.PropertyCard;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertyConfigTest {
    private PropertyConfig testSpace = new PropertyConfig();
    private File file = new File("data/GameTestFiles/PropertiesInfoTest.xml");
    List<PropertyCard> propertyCardList = testSpace.makePropertiesInfoFromXML(file);

    @Test
    public void makeListWithCorrectPropertyCards(){
        String actual = "";
        String expected = "Mediterranean Avenue60Baltic Avenue60Oriental Avenue100Vermont Avenue100Connecticut Avenue120Reading Railroad200Pennsylvania Railroad200Electric Company150Water Works150";
        for(int i = 0; i < propertyCardList.size(); i++){
            PropertyCard propertyCard = propertyCardList.get(i);
            actual += propertyCard.getTitle() + propertyCard.getBuyingPrice();
        }
    }
}
