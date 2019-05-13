package model.engine;

import model.card.perkcard.JailPerkCard;
import model.card.perkcard.PerkCard;
import model.card.propertycard.PropertyCard;
import model.card.propertycard.RealEstatePropertyCard;
import model.player.Player;
import model.tile.PropertyTile;
import model.tile.RealEstateTile;
import model.tile.Tile;
import java.util.List;
import java.util.Map;

public class ModelCheck {

    private ModelEngine modelEngine;

    public ModelCheck(ModelEngine modelEngine) {
        this.modelEngine = modelEngine;
    }

    public boolean checkIfOwnsJailPerk() {
        for(PerkCard perk : modelEngine.getCurrentPlayer().getPerkCards()) {
            if(perk instanceof JailPerkCard) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfJailed() { return modelEngine.getCurrentPlayer().getInJail(); }

    public boolean checkIfIsProperty() {
        Tile currentTile = modelEngine.getBoard().getTile(modelEngine.getCurrentPlayer().getPosition());
        return (currentTile instanceof PropertyTile);
    }

    public boolean checkIfPropertyTileOwnedBySelf() {
        Tile currentTile = modelEngine.getBoard().getTile(modelEngine.getCurrentPlayer().getPosition());
        if (currentTile instanceof PropertyTile) {
            PropertyCard property = ((PropertyTile) currentTile).getCard();
            return modelEngine.getCurrentPlayer().haveProperty(property);
        }
        return false;
    }

    public boolean checkIfCanBuildHouses() {
        Tile currentTile = modelEngine.getBoard().getTile(modelEngine.getCurrentPlayer().getPosition());
        if (checkIfPropertyTileOwnedBySelf() && currentTile instanceof RealEstateTile) {
            RealEstatePropertyCard property = ((RealEstateTile) currentTile).getCard();
            return property.getNumHouses() < modelEngine.getHouseToHotelThreshold() && property.getNumHotels() == 0;
        }
        return false;
    }

    public boolean checkIfPropertyHasHouses() {
        Tile currentTile = modelEngine.getBoard().getTile(modelEngine.getCurrentPlayer().getPosition());
        if (checkIfPropertyTileOwnedBySelf() && currentTile instanceof RealEstateTile) {
            RealEstatePropertyCard property = ((RealEstateTile) currentTile).getCard();
            return property.getNumHouses() > 0;
        }
        return false;
    }

    public boolean checkIfCanBuildHotel() {
        Tile currentTile = modelEngine.getBoard().getTile(modelEngine.getCurrentPlayer().getPosition());
        if (checkIfPropertyTileOwnedBySelf() && currentTile instanceof RealEstateTile) {
            RealEstatePropertyCard property = ((RealEstateTile) currentTile).getCard();
            if(property.getNumHotels() == 0) {
                return property.getNumHouses() == modelEngine.getHouseToHotelThreshold();
            }
        }
        return false;
    }

    public boolean checkIfCanSellHotel() {
        Tile currentTile = modelEngine.getBoard().getTile(modelEngine.getCurrentPlayer().getPosition());
        if (checkIfPropertyTileOwnedBySelf() && currentTile instanceof RealEstateTile) {
            RealEstatePropertyCard property = ((RealEstateTile) currentTile).getCard();
            return property.getNumHotels() > 0;

        }
        return false;
    }

    public boolean checkIfMonopoly(){
        if(checkIfPropertyTileOwnedBySelf() && !modelEngine.getGameType().equalsIgnoreCase("Junior")) {
            Tile currentTile = modelEngine.getBoard().getTile(modelEngine.getCurrentPlayer().getPosition());
            Map<Integer, List<PropertyCard>> map = modelEngine.getCurrentPlayer().getPropertiesMap();
            if(currentTile instanceof PropertyTile) {
                PropertyCard card = (PropertyCard) ((PropertyTile) currentTile).getCard();
                if(card.getPropertyGroup() == 1 || card.getPropertyGroup() == 8) {
                    return map.get(card.getPropertyGroup()).size() == 2;
                }
                else {
                    return map.get(card.getPropertyGroup()).size() == 3;
                }
            }

        }
        return false;
    }

    public boolean checkIfPropertyTileOwnedByOther() {
        Tile currentTile = modelEngine.getBoard().getTile(modelEngine.getCurrentPlayer().getPosition());
        if (currentTile instanceof PropertyTile) {
            PropertyCard property = ((PropertyTile) currentTile).getCard();
            for (Player player : modelEngine.getCurrentPlayer().getOtherPlayers()) {
                if (player.haveProperty(property)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfRolledSpecial() {
        for (Integer integer : modelEngine.getDiceRolledList()) {
            if (modelEngine.getDiceRolledList().get(0) != integer) {
                return false;
            }
        }
        return true;
    }

    public boolean checkIfOwnsProperties() { return ! modelEngine.getCurrentPlayer().getPropertiesMap().isEmpty(); }

    public boolean checkIfOthersOwnProps() {
        for(Player p : modelEngine.getPlayers()) {
            if(p != modelEngine.getCurrentPlayer()) {
                if(p.getPropertiesMap().size() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCurrentPlayerCPU() { return modelEngine.getCurrentPlayer().getIsCPU(); }

    public boolean isAnyPlayerCPU() {
        for (Player player : modelEngine.getPlayers()) {
            if (player.getIsCPU()) {
                return true;
            }
        }
        return false;
    }
}
