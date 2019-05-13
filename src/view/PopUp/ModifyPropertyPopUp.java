package view.PopUp;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.card.propertycard.PropertyCard;
import model.tile.RealEstateTile;
import model.tile.Tile;
import java.util.List;
import java.util.ResourceBundle;

public class ModifyPropertyPopUp extends PopUp{

    private Tile myTile;

    public ModifyPropertyPopUp(RealEstateTile tile, ResourceBundle resourceBundle) {
        super(resourceBundle);
        setUpPopUp();
        this.setId("propertyCardPopUp");
        myTile = tile;
        PropertyCard myCard = tile.getCard();
        StackPane topPane = new StackPane();
        topPane.setMaxWidth(getPopUpWidth());
        Rectangle myColorBox = createColorBox();
        topPane.getChildren().add(myColorBox);
        topPane.getChildren().add(createLabel());
        this.getChildren().add(topPane);
        createRentLabel((myCard.getRentInfo()));
        this.setAlignment(Pos.CENTER);

    }

    private VBox createLabel() {
        VBox titleBox = new VBox();
        titleBox.setAlignment(Pos.CENTER);
        //super.setLabel(text);
        Label title = new Label(getMyResources().getString("titleDeed"));
        title.setId("popUpTitle");
        titleBox.getChildren().add(title);
        Label name = new Label(myTile.getText());
        name.setId("popUpName");
        titleBox.getChildren().add(name);
        return titleBox;
    }

    private Rectangle createColorBox() {
        Rectangle titleBox = new Rectangle(getPopUpWidth(),getPopUpHeight()/7);
        titleBox.setId("propertyCardRect");
        titleBox.setFill(Color.valueOf(myTile.getColor().toUpperCase()));
        this.getChildren().add(titleBox);
        return titleBox;
    }

    private void createRentLabel(List<Integer> rentlist) {
        Label rent = new Label(String.format(getMyResources().getString("rentLabel"),rentlist.get(0)));
        this.getChildren().add(rent);
        for (int i=1; i<rentlist.size()-1; i++) {
            String str = String.format(getMyResources().getString("withHouse"), i,rentlist.get(i));
            Label label = new Label(str);
            //Label label = new Label("With "+Integer.toString(i)+" House(s):       " + Integer.toString(rentlist.get(i)));
            this.getChildren().add(label);
        }
        Label hotel = new Label(String.format(getMyResources().getString("withHotel"), rentlist.get(rentlist.size()-1)));
//        Label hotel = new Label("With HOTEL $"+ rentlist.get(rentlist.size()-1));
        this.getChildren().add(hotel);
    }
}
