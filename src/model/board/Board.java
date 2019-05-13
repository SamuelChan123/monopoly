package model.board;

import model.tile.Tile;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private Map<Integer,Tile> boardMap;

    public Board(){
        boardMap = new HashMap<Integer, Tile>();
    }

    public void addTile(Tile tile){
        boardMap.put(tile.getPosition(),tile);
    }

    public Map<Integer,Tile> getBoardMap(){
        return boardMap;
    }

    public Tile getTile(int index) { return boardMap.get(index); }

    public int getMaxIndex() { return boardMap.keySet().size();}
}
