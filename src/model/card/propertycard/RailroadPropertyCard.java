package model.card.propertycard;

import java.util.List;

public class RailroadPropertyCard extends PropertyCard {

    public RailroadPropertyCard(String title, int propertyGroup, int groupNum, int buyingPrice, int sellingPrice, int totalGroups, List<Integer> rentInfo, int mortgage, int buildingCost) {
        super(title, propertyGroup, groupNum, buyingPrice, sellingPrice, totalGroups, rentInfo, mortgage, buildingCost);
    }

    @Override
    public int calculateRent(int diceRolled, int numberOwned) {
        if(numberOwned == 0) {
            return 0;
        }
        return getRentInfo().get(numberOwned - 1);
    }
}

