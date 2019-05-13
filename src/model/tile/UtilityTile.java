package model.tile;

import model.card.propertycard.PropertyCard;
import model.card.propertycard.RailroadPropertyCard;
import model.card.propertycard.UtilitiesPropertyCard;

import java.util.ArrayList;

public class UtilityTile extends PropertyTile {

    public UtilityTile(String[] tileInfo) {
        super(tileInfo);
        setCard(new UtilitiesPropertyCard("",0,0,0,0,0,new ArrayList<>(),0, 0));
    }
}