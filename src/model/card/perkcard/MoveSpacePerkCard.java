package model.card.perkcard;

import model.player.Player;

import java.util.List;

public class MoveSpacePerkCard extends PerkCard {
    public MoveSpacePerkCard(List<String> attributeList) { super(attributeList); }

    @Override
    public void perkCardAction(Player player){
        int newPosition = player.getPosition() + this.getMove();
        player.changePosition(newPosition);
    }
}