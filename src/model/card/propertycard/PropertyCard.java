package model.card.propertycard;

import java.util.List;

public abstract class PropertyCard {

    private String title;
    private int propertyGroup;
    private int groupNum;
    private int sellingPrice;
    private int buyingPrice;
    private int totalGroups;
    private List<Integer> rentInfo;
    private int mortgage;
    private int buildingCost;

    public PropertyCard(String title, int propertyGroup, int groupNum, int buyingPrice, int sellingPrice, int totalGroups, List<Integer> rentInfo, int mortgage, int buildingCost) {
        this.title = title;
        this.propertyGroup = propertyGroup;
        this.groupNum = groupNum;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.totalGroups = totalGroups;
        this.rentInfo = rentInfo;
        this.mortgage = mortgage;
        this.buildingCost = buildingCost;
    }

    public abstract int calculateRent(int diceRolled, int numberOwned);

    public String getTitle(){
        return title;
    }

    public int getBuyingPrice() {
        return buyingPrice;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public int getPropertyGroup() {
        return propertyGroup;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public int getTotalGroups() {
        return totalGroups;
    }

    public int getRent() { return rentInfo.get(0); }

    public List<Integer> getRentInfo() {
        return rentInfo;
    }

    public int getMortgageValue() {
        return mortgage;
    }

    public int getMortgage(){
        return mortgage;
    }

    public int getBuildingCost(){
        return buildingCost;
    }

    @Override
    public String toString() {
        return title;
    }
}
