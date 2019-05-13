package view.TileView;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import model.tile.CornerTile;
import model.tile.Tile;

abstract public class RectangleImageTileView extends TileView {

    /**
     * This method makes the other children to be put in the VBox of the tileview
     * @param tile specifies the corresponding tile
     * @param imageWidth specifies the image width of the image
     * @param imageHeight specifies the image height of the image
     * @param angle specifies the angle of rotation of the image
     */
    protected void makeChildren(Tile tile, int imageWidth, int imageHeight, int angle) {
        Polygon background = new Polygon(
                START_X, START_Y,
                START_X + getWidth(), START_Y,
                START_X + getWidth(), START_Y + getHeight(),
                START_X, START_Y + getHeight()
        );
        StackPane stackPane = new StackPane();
        VBox vBox = new VBox();
        makeBackground(background,stackPane,vBox);
        Text bottomLabel = new Text();
        ImageView image;
        if(!tile.getImage().equals("")){
            image = new ImageView(tile.getImage());
        } else {
            image = new ImageView("jail.png");
        }

        makeLabel(bottomLabel);
        image.setFitWidth(imageWidth);
        image.setFitHeight(imageHeight);
        setFontStyle(bottomLabel);
        vBox.getChildren().add(image);
        vBox.getChildren().add(bottomLabel);
        vBox.setRotate(angle);
        vBox.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(vBox);
        this.getChildren().add(stackPane);
    }

    /**
     * This method makes the bottom label of the tileview
     * @param bottomLabel specifies the text to be put in the bottom label
     */
    protected abstract void makeLabel(Text bottomLabel);
}
