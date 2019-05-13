package model.tile;

import model.card.perkcard.PerkCard;
import model.player.Player;
import java.util.List;

public class ChancePerkTile extends PerkTile {
    public ChancePerkTile(String[] tileInfo) {
        super(tileInfo);
    }

    @Override
    public void performAction(Player player){
        List<PerkCard> chanceCardList = sortPerkCards("chance",this.getDeck());
        drawCard(player,chanceCardList);
    }
}