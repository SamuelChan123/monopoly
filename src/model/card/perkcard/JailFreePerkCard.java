package model.card.perkcard;

import model.player.Player;
import model.tile.JailTile;

import java.util.List;

public class JailFreePerkCard extends JailPerkCard {
    public JailFreePerkCard(List<String> attributeList) { super(attributeList); }

    @Override
    public void perkCardAction(Player player){
        player.addToPerks(this);
    }

    public void useCard(Player player){
        JailTile jailTile = this.getJailTile();
        if(jailTile.getPlayersInJail().contains(player)){
            jailTile.removePersonFromJail(player);
        }
    }
}