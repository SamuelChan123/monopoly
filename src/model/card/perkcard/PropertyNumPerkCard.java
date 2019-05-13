package model.card.perkcard;

import model.card.propertycard.PropertyCard;
import model.card.propertycard.RealEstatePropertyCard;
import model.player.Player;

import java.util.List;
import java.util.Map;

public class PropertyNumPerkCard extends PerkCard{
    int costEachHouse;
    int costEachHotel;
    int totalHouse;
    int totalHotel;

    public PropertyNumPerkCard(List<String> attributeList) {
        super(attributeList);
        costEachHouse = Integer.parseInt(attributeList.get(attributeList.size() - 2));
        costEachHotel = Integer.parseInt(attributeList.get(attributeList.size() - 1));
        totalHouse = 0;
        totalHotel = 0;
    }

    @Override
    public void perkCardAction(Player player) {
        calculateHotelsAndHouses(player);
        int totalPayment = (costEachHouse * totalHouse) + (costEachHotel * totalHotel);
        player.changeBalance((-1*totalPayment));
    }

    public void calculateHotelsAndHouses(Player player){
        Map<Integer,List<PropertyCard>> propertyMap = player.getPropertiesMap();
        for(List<PropertyCard> propertyCardList : propertyMap.values()){
            for(PropertyCard propertyCard : propertyCardList){
                if(propertyCard instanceof RealEstatePropertyCard){
                    totalHouse += ((RealEstatePropertyCard) propertyCard).getNumHouses();
                    totalHotel += ((RealEstatePropertyCard) propertyCard).getNumHotels();
                }
            }
        }
    }
}