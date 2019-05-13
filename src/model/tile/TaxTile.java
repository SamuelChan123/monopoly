package model.tile;

import model.player.Player;

public class TaxTile extends Tile {

    private int amountTax;
    FreeParkTile freeParkTile;

    public TaxTile(String[] tileInfo) {
        super(tileInfo);
        this.amountTax = Integer.parseInt(tileInfo[4]);
    }

    @Override
    public void performAction(Player player) {
        player.changeBalance(-1 * amountTax);
        this.freeParkTile.changeParkingMoney(amountTax);
    }

    public int getTaxAmount(){
        return amountTax;
    }

    public void setFreeParkTile(FreeParkTile tile){
        freeParkTile = tile;
    }

}
