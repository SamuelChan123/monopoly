package model.tile;

import model.card.perkcard.PerkCard;
import model.player.Player;
import java.util.List;

public class CommunityPerkTile extends PerkTile {
    public CommunityPerkTile(String[] tileInfo) {
        super(tileInfo);
    }

    @Override
    public void performAction(Player player){
        List<PerkCard> communityCardList = sortPerkCards("community", this.getDeck());
        drawCard(player,communityCardList);
    }
}