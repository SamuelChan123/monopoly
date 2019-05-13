package view.TileView;

import javafx.beans.property.*;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.engine.ModelEngine;
import model.tile.RealEstateTile;
import model.tile.Tile;
import view.PopUp.ModifyPropertyPopUp;
import view.PopUp.PopUp;

import java.util.ResourceBundle;

public class RectangleRealEstateTileView extends TileView {

    private static final int TEXT_SIZE = 10;
    private static final int BLANK_SPACE = 13;
  //  private ImageView houseImage = new ImageView(new Image("house.png"));
    private Image houseImage = new Image("housetest.gif");
    private Image hotelImage = new Image("hoteltest.gif");
    private Text myPriceLabel;
    private Color myGroupColor;
    private Color myOwnerColor;
    private PopUp myPopUp;
    private RealEstateTile tile;
    private StackPane propColorStackPane;
    private ResourceBundle myResources;

    public RectangleRealEstateTileView(int tileWidth, int tileHeight, int groupColor, RealEstateTile tile, int textSize, ResourceBundle resourceBundle){
        myResources = resourceBundle;
        myTextSize = textSize;
        this.tile = tile;
        setHeight(tileHeight);
        setWidth(tileWidth);
        myGroupColor = Color.valueOf(tile.getColor());
        myPriceLabel = new Text(Integer.toString(tile.getCard().getBuyingPrice()));
        setFontStyle(myPriceLabel);
        setUpNameText(tile);
        makeChildren();
        myPopUp = new ModifyPropertyPopUp(tile,myResources);
    }

    protected void makeChildren() {
        Polygon propertyGroup = new Polygon(
                                            START_X,START_Y,
                                            START_X+getWidth(),START_Y,
                                            START_X+getWidth(),START_Y+getHeight()/6,
                                            START_X,START_X+START_Y+getHeight()/6
        );
        propertyGroup.setFill(myGroupColor);
        Polygon tileDetails = new Polygon(
                                            START_X,START_Y+getHeight()/6,
                                            START_X+getWidth(),START_Y+getHeight()/6,
                                            START_X+getWidth(),START_Y+5*getHeight()/6,
                                            START_X, START_X+START_Y+5*getHeight()/6
        );
        tileDetails.setFill(Color.FLORALWHITE);
        Polygon ownerGroup = new Polygon(
                                            START_X,START_Y+5*getHeight()/6,
                                            START_X+getWidth(),START_Y+5*getHeight()/6,
                                            START_X+getWidth(),START_Y+getHeight(),
                                            START_X, START_X+START_Y+getHeight()
        );
        ownerGroup.setFill(Color.FLORALWHITE);
        StackPane stackPane = new StackPane();
        VBox vBox = new VBox();
        makeBackground(tileDetails,stackPane,vBox);
        Text blankSpace = new Text(" ");
        blankSpace.setFont(new Font("Verdana", BLANK_SPACE));
        vBox.getChildren().add(blankSpace);
        vBox.getChildren().add(myPriceLabel);
        vBox.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(vBox);
        propColorStackPane = new StackPane();
        propColorStackPane.getChildren().add(propertyGroup);
        this.getChildren().addAll(propColorStackPane, stackPane, ownerGroup);
    }

    @Override
    public void updateTileView() {
        if(propColorStackPane.getChildren().size() > 1){
            propColorStackPane.getChildren().remove(1);
        }
        HBox housesBox = new HBox(2);

        int houses =  tile.getCard().getNumHouses();
        int hotels = tile.getCard().getNumHotels();
        int threshold = tile.getCard().getThreshold();

        if(hotels > 0) {
            housesBox.getChildren().clear();
            for(int i = 0; i < hotels; i++) {
                housesBox.getChildren().add(new ImageView(hotelImage));
            }
        } else {
            for(int i =0; i < houses; i++) {
                if (i < threshold) {
                    housesBox.getChildren().add(new ImageView(houseImage));
                }
            }
        }

        propColorStackPane.getChildren().add(housesBox);
    }

    public Label addTitle(Tile tile) {
        Label name = new Label(tile.getType());
        name.setFont(new Font("Arial", TEXT_SIZE));
        name.setAlignment(Pos.CENTER);
        //this.getChildren().add(name);
        return name;
    }

    public PopUp getMyPopUp() {
        return myPopUp;
    }
}
