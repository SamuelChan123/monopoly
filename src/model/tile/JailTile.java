package model.tile;

import model.card.perkcard.PerkCard;
import model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class JailTile extends CornerTile {
    List<Player> playersInJail;
    private static final int DEFAULT_JAIL_WAIT = 3;

    public JailTile(String[] tileInfo) {
        super(tileInfo);
        playersInJail = new ArrayList<>();
    }

    public List<Player> getPlayersInJail(){
        return playersInJail;
    }

    public void addPersonToJail(Player player){
        if(! player.getPerkCards().isEmpty() && player.getIsCPU() == true){
            player.getPerkCards().remove(0);
            System.out.println(player.getName() + " used a 'Get Out of Jail Free' card to avoid going in jail!");
        }
        else{
            player.changeInJail(true);
            player.changeTurnsInJail(DEFAULT_JAIL_WAIT);
            playersInJail.add(player);
        }
    }

    public void removePersonFromJail(Player player){
        playersInJail.remove(player);
    }

    public void jailTurn(Player currentPlayer, JailTile jailTile){
        currentPlayer.changeTurnsInJail(currentPlayer.getTurnsInJail() - 1);
        System.out.println(currentPlayer.getName() + " has wait in jail for " + currentPlayer.getTurnsInJail() + " more turn(s)!");
        removePlayerFromJailIfTimesUp(currentPlayer,jailTile);
    }

    private void removePlayerFromJailIfTimesUp(Player player, JailTile tile){
        if(player.getTurnsInJail() == 0){
            tile.removePersonFromJail(player);
            player.changeInJail(false);
            System.out.println(player.getName() + " is free from jail!");
        }
    }

    @Override
    public void performAction(Player player){
    }
}