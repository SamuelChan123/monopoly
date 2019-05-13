package view.PopUp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.tile.Tile;
import view.TileView.TileView;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

abstract public class PopUp extends VBox {

    private static final int POP_UP_WIDTH = 200;
    private static final int POP_UP_HEIGHT = 210;
    private static final int BORDER = 5;

    private String myTitle;
    //private Label myLabel;
    private HBox buttonBox;
    private VBox myBox;
    private Tile myTile;
    private Color myColor;
    private int myWidth;
    private int myHeight;
    private ResourceBundle myResources;

    protected List<Button> myButtons;

    public PopUp(ResourceBundle resourceBundle){
        myResources = resourceBundle;
        this.setMaxWidth(POP_UP_WIDTH);
        this.setMaxHeight(POP_UP_HEIGHT);
        this.setSpacing(2);
        /*myWidth = width;
        myHeight = height;*/
        /*this.setLayoutX(myWidth/2);
        this.setLayoutY(myHeight/2);*/
        //this.setColor(groupColor);
        //myColor = tile.getMyGroupColor();
    }

    /*public void setLabel(String text){
        myLabel.setText(text);
    }*/

    protected void setUpPopUp(){

        /*Rectangle rectangle = new Rectangle(POP_UP_WIDTH,POP_UP_HEIGHT);
        this.setAlignment(Pos.CENTER);
        rectangle.setFill(Color.HONEYDEW);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(BORDER);
//        this.getChildren().add(rectangle);
        this.getChildren().add(rectangle);*/
        //myLabel = new Label("hello");
//        myBox.getChildren().add(myLabel);
//        myBox.setStyle("-fx-background-color: red");
        //this.getChildren().add(myBox);
//        vBox.getChildren().add(myLabel);
        /*buttonBox = new HBox();
        buttonBox.setMaxWidth(POP_UP_WIDTH);
        for(Button button: myButtons){
            buttonBox.getChildren().add(button);
        }
        //buttonBox.getChildren().add(buttonBox);
        this.getChildren().add(buttonBox);*/
    }

    protected int getPopUpWidth() {return POP_UP_WIDTH;}

    protected int getPopUpHeight() {return POP_UP_HEIGHT;}

//    protected StackPane getMyStack() {return myStack;}

    public HBox getMyBox() {return buttonBox;}

    public String getMyTitle() {return myTitle;}

    public Color getMyColor() {return myColor;}

    public int getMyWidth() {return myWidth;}

    public int getMyHeight() {return myHeight;}

    protected void setButtonStyle(Button button, String text){
        button.setText(text);
    }

    protected ResourceBundle getMyResources() {
        return myResources;
    }
 }
