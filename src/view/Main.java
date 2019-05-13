package view;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.engine.ModelEngine;

import java.util.ResourceBundle;

public class Main extends Application {

    private Stage primaryStage;
    public static final int FRAMES_PER_SECOND = 1;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final String DEFAULT_RESOURCE_LOCATION = "data";

    private ResourceBundle myResources;
    private static final String propFile = "gamefiles/languagefiles.English";

    /**
     * Starts the game, sets the resources file, creates the scene and launches the stage
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        myResources = ResourceBundle.getBundle(propFile);
        primaryStage.setTitle(myResources.getString("TITLE"));
        var viewer = new MonopolyView(myResources, primaryStage);
        var modelEngine = new ModelEngine();
        var controller = new Controller(myResources, modelEngine, viewer);
        primaryStage.show();
    }

    /**
     * Launches the Monopoly game
     * @param args
     */
    public static void main (String[] args) {
        launch(args);
    }
}