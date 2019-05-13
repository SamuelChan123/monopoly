package model.card.propertycard;

import model.engine.ModelEngine;

import java.util.List;
import java.util.ResourceBundle;

public class RealEstatePropertyCard extends PropertyCard {

    private ResourceBundle setupResources = ResourceBundle.getBundle(String.format("%1$s.%2$s%3$s", "gamefiles/setupfiles", "Standard", "Setup"));
    private int buildingCost;
    private int numberOfBuildings;
    private int numHouses;
    private int numHotels;
    private int maxProp;
    private int THRESHOLD = Integer.parseInt(setupResources.getString("HouseToHotelThreshold"));

    public RealEstatePropertyCard(String title, int propertyGroup, int groupNum, int buyingPrice, int sellingPrice, int totalGroups, List<Integer> rentInfo, int mortgage, int buildingCost) {
        super(title, propertyGroup, groupNum, buyingPrice, sellingPrice, totalGroups, rentInfo, mortgage, buildingCost);
        this.numberOfBuildings = 0;
        this.numHouses = 0;
        this.numHotels = 0;
        this.maxProp = rentInfo.size();
    }

    @Override
    public int calculateRent(int diceRolled, int numberOwned) {
        int rent = this.getRentInfo().get(0);
        if(numberOfBuildings > 0){
            if (numberOwned == this.getTotalGroups()) {
                rent = this.getRentInfo().get(numberOfBuildings - 1) * 2;
            }
            else {
                rent = this.getRentInfo().get(numberOfBuildings - 1);
            }
        }
        return rent;
    }

    public boolean addHouse() {
        if(numHouses < THRESHOLD && numHotels == 0){
            numberOfBuildings ++;
            numHouses ++;
            return true;
        }
        return false;
    }

    public boolean removeHouse() {
        if(numHouses > 0 && numHotels == 0){
            numberOfBuildings --;
            numHouses--;
            return true;
        }
        return false;
    }

    public boolean buildHotel() {
        if(numberOfBuildings <= (maxProp - 1) && numHotels == 0){
            numberOfBuildings ++;
            numHotels++;
            numHouses = 0;
            return true;
        }
        return false;
    }

    public boolean sellHotel() {
        if (numHotels > 0 && numHouses == 0) {
            numberOfBuildings--;
            numHotels--;
            numHouses = THRESHOLD;
            return true;
        }
        return false;
    }

    public int getNumberOfBuildings(){
        return numberOfBuildings;
    }

    public int getNumHouses() { return numHouses; }

    public int getNumHotels() { return numHotels; }

    public void setThreshold(int thresh) {
        THRESHOLD = thresh;
    }

    public int getThreshold() {
        return THRESHOLD;
    }

}
