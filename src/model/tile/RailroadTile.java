package model.tile;

import model.card.propertycard.PropertyCard;
import model.card.propertycard.RailroadPropertyCard;

import java.util.ArrayList;

public class RailroadTile extends PropertyTile {

    public RailroadTile(String[] tileInfo) {
        super(tileInfo);
        setCard(new RailroadPropertyCard("",0,0,0,0,0,new ArrayList<>(), 0, 0));
    }
}
