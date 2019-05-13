package model.tile;

import model.player.Player;

public class GoTile extends CornerTile {
    private int amountBonus;

    public GoTile(String[] tileInfo) {
        super(tileInfo);
        this.amountBonus = Integer.parseInt(tileInfo[4]);
    }

    @Override
    public void performAction(Player player) {
        player.changeBalance(amountBonus);
    }

    public int getAmountBonus() {
        return amountBonus;
    }
}