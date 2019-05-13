package view.TileView;

import javafx.scene.text.Text;
import model.tile.*;

public class RectanglePerkTileView extends RectangleImageTileView {

    private Tile myTile;

    public RectanglePerkTileView(int tileWidth, int tileHeight, PerkTile tile, int textSize){
        myTextSize = textSize;
        setHeight(tileHeight);
        setWidth(tileWidth);
        myTile = tile;
        setUpNameText(tile);
        makeChildren(myTile,35,50,0);
        //setStyle("-fx-border-color: black");
    }

    @Override
    public void updateTileView() {

    }

    @Override
    protected void makeLabel(Text bottomLabel) {
        bottomLabel.setText("");
    }

}
