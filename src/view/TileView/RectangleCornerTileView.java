package view.TileView;

import javafx.scene.text.Text;
import model.tile.*;

public class RectangleCornerTileView extends RectangleImageTileView{

    private static final int IMAGE_WIDTH = 35;
    private static final int IMAGE_HEIGHT = 35;
    private Tile myTile;
    private int myRotate;

    public RectangleCornerTileView(int tileWidth, int tileHeight, Tile tile, int angle, int textSize){
        myTextSize = textSize;
        setHeight(tileHeight);
        setWidth(tileWidth);
        setUpNameText(tile);
        myRotate = angle;
        myTile = tile;
        makeChildren(myTile,IMAGE_WIDTH,IMAGE_HEIGHT,angle);
    }

    @Override
    public void updateTileView() {

    }

    @Override
    protected void makeLabel(Text bottomLabel) {
        bottomLabel.setText(((CornerTile) myTile).getLabel());
    }
}
