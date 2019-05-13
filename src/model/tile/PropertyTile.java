package model.tile;

import model.card.propertycard.PropertyCard;
import model.player.Player;

import java.util.List;

public class PropertyTile extends Tile {

    private boolean cardOwned;
    protected PropertyCard card;

    public PropertyTile(String[] tileInfo) {
        super(tileInfo);
        this.cardOwned = false;
    }

    public void setCardOwned(boolean cardOwned) {
        this.cardOwned = cardOwned;
    }

    public boolean getCardOwned(){ return cardOwned; }

    @Override
    public void performAction(Player player){
        if(cardOwned){
            Player playerToPay = this.findPlayerToPayRent(player);
            if(!(playerToPay == player)) {
                player.payOtherPlayer(this.getCard(), playerToPay);
            }
            else {
                System.out.println("Player landed on its own property.");
            }
        }
    }

    private Player findPlayerToPayRent(Player player){
        Player playerToPay = null;
        List<Player> otherPlayers = player.getOtherPlayers();

        for (Player otherPlayer : otherPlayers) {
            if (otherPlayer.containsProperty(card)) {
                playerToPay = otherPlayer;
                break;
            }
        }

        if(player.containsProperty(card)) {
            playerToPay = player;
        }

        if(playerToPay == null) {
            throw new IllegalArgumentException("No player found to pay rent to!");
        }
        return playerToPay;
    }

    public void setCard(PropertyCard propertyCard){
        this.card = propertyCard;
    }

    public PropertyCard getCard(){
        return card;
    }
}