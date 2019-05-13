package controller;

import model.engine.ModelCheck;
import model.engine.ModelEngine;
import view.MonopolyView;

public class StatusController {

    private MonopolyView viewer;
    private ModelCheck modelCheck;

    public StatusController(ModelEngine modelEngine, MonopolyView viewer) {
        this.viewer = viewer;
        this.modelCheck = new ModelCheck(modelEngine);
    }

    //================================================================================
    // Setup Buttons
    //================================================================================

    public void newTurnButtons() {
        viewer.getBuildHousesButton().setDisable(true);
        viewer.getSellHousesButton().setDisable(true);
        viewer.getBuyPropertyButton().setDisable(true);
        viewer.getSellPropertyButton().setDisable(true);
        viewer.getBuildHotelButton().setDisable(true);
        viewer.getSellHotelButton().setDisable(true);
        viewer.getRollDiceButton().setDisable(false);
        viewer.getUseJailFreePerkButton().setDisable(true);
        viewer.getAuctionTradeButton().setDisable(true);
    }

    public void checkIfCPU() {
        if(modelCheck.isCurrentPlayerCPU()) {
            setAllVisible(false);
        }
        else{
            setAllVisible(true);
        }
    }

    private void setAllVisible(boolean b) {
        viewer.getBuildHousesButton().setVisible(b);
        viewer.getBuyPropertyButton().setVisible(b);
        viewer.getSellPropertyButton().setVisible(b);
        viewer.getRollDiceButton().setVisible(b);
        viewer.getUseJailFreePerkButton().setVisible(b);
        viewer.getEndTurnButton().setVisible(b);
        viewer.getForfeitButton().setVisible(b);
        viewer.getSellHousesButton().setVisible(b);
        viewer.getSellHotelButton().setVisible(b);
        viewer.getBuildHotelButton().setVisible(b);
        viewer.getAuctionTradeButton().setVisible(b);
        viewer.getChangeRuleButton().setVisible(b);
    }

    //================================================================================
    // Button Handling for Buying/Selling (Property, Houses, Hotel)
    //================================================================================


    public void checkAboutTile() {
        checkIfIsProperty();
        checkIfPropertyOwnedBySelf();
        checkIfPropertyOwnedByOther();
        checkIfPropertyIsUnowned();
        checkIfOwnsJailPerk();
        checkIfPlayerOwnsProperties();
    }


    private void checkIfIsProperty() {
        if(!modelCheck.checkIfIsProperty()) {
            viewer.getBuyPropertyButton().setDisable(true);
            viewer.getSellPropertyButton().setDisable(true);
        }
    }

    private void checkIfPropertyOwnedBySelf() {
        if (modelCheck.checkIfPropertyTileOwnedBySelf()) {
            viewer.getBuyPropertyButton().setDisable(true);
            viewer.getSellPropertyButton().setDisable(false);
            checkIfPropertyCanBuild();
            checkIfPropertyHasHouses();
            checkIfCanBuildHotel();
            checkIfCanSellHotel();
        }
    }

    private void checkIfPropertyOwnedByOther() {
        if (modelCheck.checkIfPropertyTileOwnedByOther()) {
            viewer.getBuyPropertyButton().setDisable(true);
            viewer.getSellPropertyButton().setDisable(true);
            viewer.getBuildHousesButton().setDisable(true);
            viewer.getSellHousesButton().setDisable(true);
            viewer.getSellHotelButton().setDisable(true);
            viewer.getBuildHotelButton().setDisable(true);
        }
    }

    private void checkIfPropertyIsUnowned() {
        if(!modelCheck.checkIfPropertyTileOwnedBySelf() && !modelCheck.checkIfPropertyTileOwnedByOther() && modelCheck.checkIfIsProperty()) {
        viewer.getBuyPropertyButton().setDisable(false);
        viewer.getSellPropertyButton().setDisable(true);
        viewer.getBuildHousesButton().setDisable(true);
        viewer.getSellHousesButton().setDisable(true);
        viewer.getSellHotelButton().setDisable(true);
        viewer.getBuildHotelButton().setDisable(true);
        }
    }

    private void checkIfPropertyCanBuild() {
        if (modelCheck.checkIfCanBuildHouses() && modelCheck.checkIfMonopoly()) {
            viewer.getBuildHousesButton().setDisable(false);
        }
        else {
            viewer.getBuildHousesButton().setDisable(true);
        }
    }

    private void checkIfPropertyHasHouses() {
        if (modelCheck.checkIfPropertyHasHouses()) {
            viewer.getSellHousesButton().setDisable(false);
        }
        else {
            viewer.getSellHousesButton().setDisable(true);
        }
    }

    private void checkIfCanBuildHotel() {
        if(modelCheck.checkIfCanBuildHotel() && modelCheck.checkIfMonopoly()) {
            viewer.getBuildHotelButton().setDisable(false);
        }
        else {
            viewer.getBuildHotelButton().setDisable(true);
        }
    }

    private void checkIfCanSellHotel() {
        if (modelCheck.checkIfCanSellHotel()) {
            viewer.getSellHotelButton().setDisable(false);
        }
        else {
            viewer.getSellHotelButton().setDisable(true);
        }
    }

    private void checkIfPlayerOwnsProperties() {
        if (modelCheck.checkIfOwnsProperties() && modelCheck.checkIfOthersOwnProps()) {
            viewer.getAuctionTradeButton().setDisable(false);
        }
        else {
            viewer.getAuctionTradeButton().setDisable(true);
        }
    }

    //================================================================================
    // All Other Button Handling (Jail, Roll Again, Cheats)
    //================================================================================

    private void checkIfOwnsJailPerk() {
        if(modelCheck.checkIfOwnsJailPerk() && modelCheck.checkIfJailed()) {
            viewer.getUseJailFreePerkButton().setDisable(false);
        }
    }

    public void checkIfRolledSpecial() {
        if (modelCheck.checkIfRolledSpecial()) {
            viewer.getRollDiceButton().setDisable(false);
        }
        else {
            viewer.getRollDiceButton().setDisable(true);
        }
    }

    public void cheatsStatus(Boolean b) {
        viewer.getCheatMoveButton().setVisible(b);
        viewer.getCheatMoneyButton().setVisible(b);

    }

    public void CPUSpeedStatus(Boolean b) {
        viewer.getCPUSpeedButton().setVisible(b);
    }
}