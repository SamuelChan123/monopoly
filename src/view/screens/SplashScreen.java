package view.screens;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import java.util.ResourceBundle;

public class SplashScreen extends AbstractScreenView {

    private final static String BACKGROUND_IMAGE = "background1.jpg";
    private static final String STYLESHEET = "styles.css";


    public SplashScreen(ChoiceBox<String> langChoice, Button playButton, ResourceBundle resourceBundle, int sceneWidth, int sceneHeight){
        super(resourceBundle);
        Pane myRoot = new Pane();
        Scene scene = new Scene(myRoot, sceneWidth, sceneHeight);
        scene.getStylesheets().add(STYLESHEET);
        setBackground(BACKGROUND_IMAGE, myRoot);

        playButton.setId("startScreenButton");
        playButton.setLayoutX((sceneWidth / 2) - 50);
        playButton.setLayoutY(sceneHeight - 150);
        langChoice.setValue(getMyResources().getString("english"));
        langChoice.setId("langBox");
        langChoice.setLayoutX((sceneWidth / 2) - 90);
        langChoice.setLayoutY(sceneHeight - 250);
        myRoot.getChildren().addAll(langChoice, playButton);
        setScene(scene);
    }

    @Override
    public void update(String text) {

    }
}
