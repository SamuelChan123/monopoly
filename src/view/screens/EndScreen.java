package view.screens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

public class EndScreen extends AbstractScreenView {

    private Pane myRoot;
    private static final String STYLESHEET = "styles.css";
    private final static String BACKGROUND_IMAGE = "background.jpg";
    private Button startOverButton;

    public EndScreen(Button startbutton, ResourceBundle resourceBundle, int sceneWidth, int sceneHeight, String playerWon) {
        super(resourceBundle);
        myRoot = new Pane();
        Scene scene = new Scene(myRoot, sceneWidth, sceneHeight);
        scene.getStylesheets().add(STYLESHEET);
        setBackground(BACKGROUND_IMAGE, myRoot);
        startOverButton = startbutton;
        startOverButton.setId("startScreenButton");
        addStartButton(sceneWidth,sceneHeight);
        addTextBox(playerWon,sceneWidth,sceneHeight);
        setScene(scene);
    }

    private void addStartButton(int sceneWidth, int sceneHeight){
        startOverButton.setLayoutX(sceneWidth*0.43);
        startOverButton.setLayoutY(sceneHeight*0.7);
        myRoot.getChildren().add(startOverButton);
    }

    private void addTextBox(String player, int sceneWidth, int sceneHeight) {
        VBox congratBox = new VBox(3);
        Label msg = new Label(getMyResources().getString("congrats"));
        msg.setId("endSceneLabel");
        Label won = new Label(String.format(getMyResources().getString("hasWonText"), player));
        won.setId("endSceneLabel");
        congratBox.getChildren().addAll(msg,won);
        congratBox.setLayoutX(sceneWidth*0.4);
        congratBox.setLayoutY(sceneHeight*0.3);
        myRoot.getChildren().addAll(congratBox);
    }

    @Override
    public void update(String text) {

    }
}
