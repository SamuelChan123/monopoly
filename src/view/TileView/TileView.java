package view.TileView;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.tile.Tile;

import java.util.ArrayList;

public abstract class TileView extends VBox {

    protected static final int START_X = 0;
    protected static final int START_Y = 1;

    private double x;
    private double y;

    private VBox myDetails;

    protected Text myNameLabel;

    protected int myTextSize = 1;

    /**
     * This method updates the tile view
     */
    abstract public void updateTileView();

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setXY(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * This method sets up name label of a tileview
     * @param tile specifies the tile from model package
     */
    void setUpNameText(Tile tile){
        setOutline(Color.BLACK);
        myNameLabel = new Text();
        myNameLabel.setText(tile.getText());
        setFontStyle(myNameLabel);
        myNameLabel.wrappingWidthProperty().bind(this.widthProperty().subtract(8));
        myNameLabel.setTextAlignment(TextAlignment.CENTER);
    }

    /**
     * This method returns the name label of the tileview
     * @return the namelabel
     */
    public Text getMyNameLabel() {return myNameLabel;}

    /**
     * This method sets the font style
     * @param text specifies which needs to be styled
     */
    protected void setFontStyle(Text text){
        text.setFont(new Font("Monopoly_Regular",myTextSize));
    }

    /**
     * This method makes the background of the tileview
     * @param background determines the background shape
     * @param stackPane specifies the stackpane on top of which everything is placed
     * @param vBox specifies the VBox which contains the stackpane
     */
    protected void makeBackground(Polygon background, StackPane stackPane, VBox vBox){
        background.setFill(Color.FLORALWHITE);
        stackPane.getChildren().add(background);
        vBox.getChildren().add(myNameLabel);
    }

    /**
     * This method sets the outline of a tileview
     * @param color specifies the color of the outline
     */
    public void setOutline(Color color){
        this.setBorder(new Border(new BorderStroke(color,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }
}
