package model.card.perkcard;

import model.player.Player;

import java.util.List;

public class MovePerkCard extends PerkCard {
    public MovePerkCard(List<String> attributeList) {
        super(attributeList);
    }

    @Override
    public void perkCardAction(Player player){
        player.changePosition(this.getMove());
    }
}