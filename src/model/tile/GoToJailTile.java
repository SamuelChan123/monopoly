package model.tile;

import model.board.Board;
import model.player.Player;

public class GoToJailTile extends CornerTile {
    JailTile jailTile;

    public GoToJailTile(String[] tileInfo) {
        super(tileInfo);
    }

    @Override
    public void performAction(Player player){
        jailTile.addPersonToJail(player);
        player.changePosition(jailTile.getPosition());
    }

    public void setJailTile(JailTile tile){
        jailTile = tile;
    }
}