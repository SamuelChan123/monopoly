package model;

import model.card.propertycard.PropertyCard;
import model.player.Player;

public class PropertyExchange {

    public PropertyExchange(){
    }

    public void giveProperty(Player player, Player otherPlayer, PropertyCard propertyCard, int auctionValue){
        player.removeFromProperties(propertyCard);
        otherPlayer.addToProperties(propertyCard);
        player.changeBalance(auctionValue);
        otherPlayer.changeBalance(-1*auctionValue);
        System.out.println(player.getName() + " auctioned " + propertyCard.getTitle() + " to " + otherPlayer.getName() + " for " + auctionValue);
    }

    public void tradeProperty(Player player, Player otherPlayer, PropertyCard propertyCard, PropertyCard otherPropertyCard){
        player.removeFromProperties(propertyCard);
        otherPlayer.addToProperties(propertyCard);
        otherPlayer.removeFromProperties(otherPropertyCard);
        player.addToProperties(otherPropertyCard);
        System.out.println(player.getName() + " traded " + propertyCard.getTitle() + " with " + otherPlayer.getName() + " for " + otherPropertyCard.getTitle());
    }

}

