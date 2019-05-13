package model.card.perkcard;

import model.board.Board;
import model.player.Player;
import model.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class NearTilesPerkCard extends PerkCard {
    String type = "";
    Board board = new Board();

    public NearTilesPerkCard(List<String> attributeList) {
        super(attributeList);
    }

    @Override
    public void perkCardAction(Player player){
        int tilePosition = this.findNearestTilePosition(type,player,board);
        player.changePosition(tilePosition);
        for(int i = 0; i < this.getChangeFund(); i++){
            this.getBoard().getTile(player.getPosition()).performAction(player);
        }
    }

    public int findNearestTilePosition(String type, Player player, Board board){
        int nearLocation = -1;
        for(int i = player.getPosition(); i < board.getBoardMap().size(); i++){
            Tile tile = board.getBoardMap().get(i);
            if (tile.getType().equals(type)){
                nearLocation = i;
                return nearLocation;
            }
        }

        if(nearLocation == -1){
            for(int i = 0; i < player.getPosition(); i++){
                Tile tile = board.getBoardMap().get(i);
                if (tile.getType().equals(type)){
                    nearLocation = i;
                    return nearLocation;
                }
            }
        }
        return nearLocation;
    }

    public void setBoard(Board distanceboard){
        board = distanceboard;
    }

    public Board getBoard(){
        return board;
    }
}