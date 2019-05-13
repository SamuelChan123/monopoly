package model.card.perkcard;

import model.player.Player;
import model.tile.JailTile;

import java.util.List;

public class MoveJailPerkCard extends JailPerkCard {
    public MoveJailPerkCard(List<String> attributeList) {
        super(attributeList);
    }

    @Override
    public void perkCardAction(Player player){
        JailTile jailTile = this.getJailTile();
        jailTile.addPersonToJail(player);
        player.changePosition(jailTile.getPosition());
    }
}