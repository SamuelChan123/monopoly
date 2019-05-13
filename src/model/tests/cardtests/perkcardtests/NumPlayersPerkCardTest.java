package model.tests.cardtests.perkcardtests;

import model.player.Player;

import java.util.List;

public class NumPlayersPerkCardTest {
    public void setOtherPlayersFromList(List<Player> list){
        for(Player player : list){
            player.setOtherPlayers(list);
        }
    }
}
