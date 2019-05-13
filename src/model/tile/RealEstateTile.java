package model.tile;

import model.card.propertycard.PropertyCard;
import model.card.propertycard.RealEstatePropertyCard;
import model.player.Player;

import java.util.ArrayList;

public class RealEstateTile extends PropertyTile {

    public RealEstateTile(String[] tileInfo) {
        super(tileInfo);
        setCard(new RealEstatePropertyCard("",0,0,0,0,0,new ArrayList<>(), 0, 0));
    }

    @Override
    public RealEstatePropertyCard getCard(){
        return (RealEstatePropertyCard) card;
    }
}
