/**
 * Constraints: assuming only certain number of dice face possibilities, since we are using imageviews
 */

package view.infopanel;
import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import javafx.util.Duration;
import model.dice.Dice;


import java.util.ArrayList;
import java.util.List;

public class DiceView extends HBox {
    private List<Shape> myDiceList; // holds polygons
    private Dice dice;
    private int amountDice;

    public DiceView(Dice dice, int x, int y) {
        super(10);

        myDiceList = new ArrayList<>();
        this.dice = dice;
        amountDice = dice.getNumberOfDie();

        createDefault(amountDice);
        this.setLayoutX(x);
        this.setLayoutY(y);
        initialDisplay();
    }

    private void createDefault(int dieNum) {
        for (int i = 0; i < dieNum; i++) {
            Shape s = new Rectangle(50,50); // each dice is a polygon
            s.setFill(Color.BLACK);
            myDiceList.add(s); // adding empty polygons to the list
            getChildren().add(s);
        }
    }

    public void updateAllDice() { //updates all of the face of the die depending on an arraylist of ints from model
        List<Integer> rolledValues = dice.getRolledDice();
        for (int i = 0; i < rolledValues.size(); i++) {
            int displaynum = rolledValues.get(i);
            String imgstr = "dice-"+Integer.toString(displaynum)+"-md.png";
            Image img = new Image(this.getClass().getClassLoader().getResourceAsStream(imgstr));
            ImagePattern imgp = new ImagePattern(img);
            myDiceList.get(i).setFill(imgp);
        }
    }

    public void initialDisplay() { //updates all of the face of the die depending on an arraylist of ints from model
        for (int i = 0; i < amountDice; i++) {
            String imgstr = "dice-1-md.png";
            Image img = new Image(this.getClass().getClassLoader().getResourceAsStream(imgstr));
            ImagePattern imgp = new ImagePattern(img);
            myDiceList.get(i).setFill(imgp);
        }
    }

    public void rollAnimation() {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), this);
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
        rotateTransition.play();
    }
}

