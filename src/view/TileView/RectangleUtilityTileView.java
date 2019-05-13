package view.TileView;

import javafx.scene.text.Text;
import model.tile.*;

public class RectangleUtilityTileView extends RectangleImageTileView {

    private static final int IMAGE_WIDTH = 35;
    private static final int IMAGE_HEIGHT = 45;
    private static final int ROTATION = 0;
    private Tile myTile;

    public RectangleUtilityTileView(int tileWidth, int tileHeight, Tile tile, int textSize){
        myTextSize = textSize;
        setHeight(tileHeight);
        setWidth(tileWidth);
        setUpNameText(tile);
        myTile = tile;
        makeChildren(tile,IMAGE_WIDTH,IMAGE_HEIGHT,ROTATION);
    }

    @Override
    public void updateTileView() {

    }

    @Override
    protected void makeLabel(Text bottomLabel) {
        if(myTile instanceof RailroadTile) {
            bottomLabel.setText(Integer.toString(((RailroadTile) myTile).getCard().getBuyingPrice()));
        }
        else if(myTile instanceof TaxTile) {
            bottomLabel.setText("Pay $"+Integer.toString(((TaxTile) myTile).getTaxAmount()));
        } else {
            bottomLabel.setText(Integer.toString(((UtilityTile) myTile).getCard().getBuyingPrice()));
        }
    }
}
