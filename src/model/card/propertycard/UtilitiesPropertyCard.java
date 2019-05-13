package model.card.propertycard;

import java.util.List;

public class UtilitiesPropertyCard extends PropertyCard {

    public UtilitiesPropertyCard(String title, int propertyGroup, int groupNum, int buyingPrice, int sellingPrice, int totalGroups, List<Integer> rentInfo, int mortgage, int buildingCost) {
        super(title, propertyGroup, groupNum, buyingPrice, sellingPrice, totalGroups, rentInfo, mortgage, buildingCost);
    }

    @Override
    public int calculateRent(int diceRolled, int numberOwned) {
        int rent;
        if (numberOwned == this.getTotalGroups()) {
            rent = diceRolled * getRentInfo().get(1);
        }
        else {
            rent = diceRolled * getRentInfo().get(0);
        }
        return rent;
    }
}
