package model.card.perkcard;

import model.player.Player;
import model.tile.FreeParkTile;

import java.util.List;

public class ChangeFundsPerkCard extends PerkCard {
    FreeParkTile freeParkTile;
    public ChangeFundsPerkCard(List<String> attributeList) {
        super(attributeList);
    }

    @Override
    public void perkCardAction(Player player){
        player.changeBalance(this.getChangeFund());
        if(this.getChangeFund() < 0){
            this.getFreeParkTile().changeParkingMoney(-1*this.getChangeFund());
        }
    }

    public void setFreeParkTile(FreeParkTile tile){
        freeParkTile = tile;
    }

    public FreeParkTile getFreeParkTile(){
        return freeParkTile;
    }
}