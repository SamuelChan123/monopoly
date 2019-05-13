package model.card.perkcard;

import model.player.Player;

import java.util.List;

public class NumPlayersPerkCard extends PerkCard{
    public NumPlayersPerkCard(List<String> attributeList) {
        super(attributeList);
    }

    @Override
    public void perkCardAction(Player player){
        int numberOfOtherPlayers = player.getOtherPlayers().size();
        player.changeBalance(numberOfOtherPlayers*setAmount(this.getChangeFund()));
        for(int i = 0; i < numberOfOtherPlayers; i++){
            Player otherPlayer = player.getOtherPlayers().get(i);
            otherPlayer.changeBalance(-1*setAmount(this.getChangeFund()));
        }
    }

    public int setAmount(int amount){ return amount; }
}