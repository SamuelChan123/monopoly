package model.engine;

import model.card.perkcard.JailFreePerkCard;
import model.card.perkcard.JailPerkCard;
import model.card.perkcard.PerkCard;
import model.card.propertycard.PropertyCard;
import model.card.propertycard.RealEstatePropertyCard;
import model.player.Player;
import model.tile.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ModelActions {

    private ModelEngine modelEngine;
    private Player currentPlayer;
    private ResourceBundle languageFile;
    private ModelCheck modelCheck;

    public ModelActions(ModelEngine modelEngine) {
        this.modelEngine = modelEngine;
        this.languageFile = modelEngine.getLanguageFile();
        this.modelCheck = new ModelCheck(modelEngine);
    }

    public void rollDiceAndMovePlayer() {
        currentPlayer = modelEngine.getCurrentPlayer();
        int diceRolled = modelEngine.rollDice();
        if(currentPlayer.getInJail() && modelCheck.checkIfRolledSpecial()){
            modelEngine.updateConsoleAndEventLog(currentPlayer.getName() + languageFile.getString("rollDoublesOutOfJailText"));
            currentPlayer.changeInJail(false);
        }
        if(! currentPlayer.getInJail()){
            moveCurrentPlayer(diceRolled);
            Tile newTile = modelEngine.getCurrentPlayerTile();
            modelEngine.updateConsoleAndEventLog(currentPlayer.getName() + languageFile.getString("rolledText") + diceRolled + languageFile.getString("andMovedToTileText") + newTile.getText());
            newTile.performAction(currentPlayer);
        }
        else{
            modelEngine.jailCurrentPlayer();
        }
    }

    public void moveCurrentPlayer(int diceRolled) {
        currentPlayer = modelEngine.getCurrentPlayer();
        int boardMaxIndex = modelEngine.getBoardMaxIndex();
        if (currentPlayer.getPosition() + diceRolled > boardMaxIndex) {
            int spacesFromStart = currentPlayer.getPosition() + diceRolled - boardMaxIndex;
            currentPlayer.changePosition(spacesFromStart);
            if(spacesFromStart > boardMaxIndex){
                currentPlayer.changePosition(spacesFromStart -boardMaxIndex);
            }
            modelEngine.passGoAndCollect();
        }
        else if (currentPlayer.getPosition() + diceRolled == boardMaxIndex){
            currentPlayer.changePosition(0);
            modelEngine.passGoAndCollect();
        }
        else if (currentPlayer.getPosition() + diceRolled < 0) {
            int spacesFromStart = currentPlayer.getPosition() + diceRolled + boardMaxIndex;
            currentPlayer.changePosition(spacesFromStart);
        }
        else { currentPlayer.changePosition(currentPlayer.getPosition()+ diceRolled); }
    }

    public void buyProperty() {
        currentPlayer = modelEngine.getCurrentPlayer();
        Tile tile = modelEngine.getCurrentPlayerTile();
        if(tile instanceof PropertyTile) {
            PropertyCard property = ((PropertyTile) tile).getCard();
            if(currentPlayer.getBalance() >= property.getBuyingPrice() && !currentPlayer.containsProperty(property)) {
                currentPlayer.changeBalance(-1 * property.getBuyingPrice());
                currentPlayer.addToProperties(property);
                ((PropertyTile) tile).setCardOwned(true);
                modelEngine.updateConsoleAndEventLog(currentPlayer.getName() + languageFile.getString("hasBoughtPropertyText") + property.getTitle());
            }
        }
    }

    public void sellProperty() {
        currentPlayer = modelEngine.getCurrentPlayer();
        Tile tile = modelEngine.getCurrentPlayerTile();
        if(tile instanceof PropertyTile) {
            PropertyCard property = ((PropertyTile) tile).getCard();
            if(property instanceof RealEstatePropertyCard && ((RealEstatePropertyCard) property).getNumberOfBuildings() > 0) {
                return;
            }
            if(! currentPlayer.containsProperty(property)) {
                return;
            }
            currentPlayer.removeFromProperties(property);
            currentPlayer.changeBalance(property.getSellingPrice());
            ((PropertyTile) tile).setCardOwned(false);
            modelEngine.updateConsoleAndEventLog(currentPlayer.getName() + languageFile.getString("hasSoldPropertyText") + property.getTitle());
        }
    }

    public void buildHouse() {
        currentPlayer = modelEngine.getCurrentPlayer();
        Tile currentTile = modelEngine.getCurrentPlayerTile();
        if (currentTile instanceof RealEstateTile) {
            RealEstateTile propertyTile = (RealEstateTile) currentTile;
            RealEstatePropertyCard property = propertyTile.getCard();
            if(property.addHouse()) {
                int cost = property.getBuildingCost() * -1;
                currentPlayer.changeBalance(cost);
                modelEngine.updateConsoleAndEventLog(currentPlayer.getName() + languageFile.getString("hasBuiltHouseOnText") + property.getTitle());
            }
        }
    }

    public void sellHouse() {
        currentPlayer = modelEngine.getCurrentPlayer();
        Tile currentTile = modelEngine.getCurrentPlayerTile();
        if (currentTile instanceof RealEstateTile) {
            RealEstateTile propertyTile = (RealEstateTile) currentTile;
            RealEstatePropertyCard property = propertyTile.getCard();
            if (property.removeHouse()) {
                int income = property.getBuildingCost() / 2;
                currentPlayer.changeBalance(income);
                modelEngine.updateConsoleAndEventLog(currentPlayer.getName() + languageFile.getString("hasSoldHouseOnText") + property.getTitle());
            }
        }
    }

    public void buildHotel() {
        currentPlayer = modelEngine.getCurrentPlayer();
        Tile currentTile = modelEngine.getCurrentPlayerTile();
        if (currentTile instanceof RealEstateTile) {
            RealEstateTile propertyTile = (RealEstateTile) currentTile;
            RealEstatePropertyCard property = propertyTile.getCard();
            if(property.buildHotel()) {
                int cost = property.getBuildingCost() * -1;
                currentPlayer.changeBalance(cost);
                modelEngine.updateConsoleAndEventLog(currentPlayer.getName() + languageFile.getString("hasBuiltHotelOnText") + property.getTitle());
            }
        }
    }

    public void sellHotel() {
        currentPlayer = modelEngine.getCurrentPlayer();
        Tile currentTile = modelEngine.getCurrentPlayerTile();
        if (currentTile instanceof RealEstateTile) {
            RealEstateTile propertyTile = (RealEstateTile) currentTile;
            RealEstatePropertyCard property = propertyTile.getCard();
            if(property.sellHotel()) {
                int income = property.getBuildingCost() / 2;
                currentPlayer.changeBalance(income);
                modelEngine.updateConsoleAndEventLog(currentPlayer.getName() + languageFile.getString("hasSoldHotelOnText") + property.getTitle());
            }
        }
    }

    public void useFreeJailCard() {
        currentPlayer = modelEngine.getCurrentPlayer();
        if(modelCheck.checkIfOwnsJailPerk()) {
            currentPlayer.setTurnsInJail(0);
            currentPlayer.changeInJail(false);
            PerkCard perk = null;
            for(PerkCard pc : currentPlayer.getPerkCards()) {
                if(pc instanceof JailPerkCard) {
                    perk = pc;
                }
            }
            if(perk != null) {
                currentPlayer.getPerkCards().remove(perk);
            }
            modelEngine.updateConsoleAndEventLog(currentPlayer.getName() + languageFile.getString("useGetOutOfJailCard"));
        }
    }

    public void giveOutOfJail() {
        currentPlayer = modelEngine.getCurrentPlayer();
        JailFreePerkCard perk = new JailFreePerkCard(new ArrayList<>((Arrays.asList("JailFreePerkCard", "Get Out Of Jail Free", "JailFreePerkCard", "0", "0"))));
        currentPlayer.addToPerks(perk);
    }
}
