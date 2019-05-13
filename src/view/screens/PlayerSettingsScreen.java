package view.screens;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class PlayerSettingsScreen extends AbstractScreenView {

    private final static String BACKGROUND_IMAGE = "background.jpg";
    private static final String STYLESHEET = "styles.css";
    private Pane myRoot;
    private Scene scene;
    private Map<Integer, List<String>> playerMap;

    public PlayerSettingsScreen(int numPlayers, Map<Integer, List<String>> playerMap, Button startButton, ResourceBundle resourceBundle, int sceneWidth, int sceneHeight) {
        super(resourceBundle);
        myRoot = new Pane();
        scene = new Scene(myRoot, sceneWidth, sceneHeight);
        this.playerMap = playerMap;
        scene.getStylesheets().add(STYLESHEET);
        setBackground(BACKGROUND_IMAGE, myRoot);
        startButton.setId("startScreenButton");
        startButton.setLayoutX(sceneWidth / 2 - 50);
        startButton.setLayoutY(sceneHeight - 100);
        myRoot.getChildren().add(startButton);
        createPlayerInputBoxes(numPlayers, sceneWidth);
        setScene(scene);
    }

    @Override
    public void update(String text) {
        //do nothing
    }

    private void createPlayerInputBoxes(int numPlayers, int sceneWidth){
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefHeight(250);
        HBox hBox = new HBox();
        hBox.setId("monopolyGreen");
        for(int i=1; i<=numPlayers; i++){
            hBox.getChildren().add(makePlayerConfigBox(i));
        }
        scrollPane.setContent(hBox);
        scrollPane.setPrefWidth(825);
        scrollPane.setLayoutY(200);
        scrollPane.setLayoutX((sceneWidth / 2) - (820 / 2));
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        myRoot.getChildren().add(scrollPane);
    }

    private GridPane makePlayerConfigBox(int num){
        GridPane playerBox = new GridPane();
        playerBox.setId("scrollPane");
        playerMap.put(num, new ArrayList<>(List.of("", "", "")));
        playerBox.setId("playerConfigBox");
        Label playerNumLabel = new Label(String.format(getMyResources().getString("playerNum"), num));
        playerBox.add(playerNumLabel, 0,0);
        playerBox.add(new Label(getMyResources().getString("playerName")), 0,1);
        playerBox.add(new Label(getMyResources().getString("playerColor")), 0,2);
        playerBox.add(new Label(getMyResources().getString("playerType")), 0,3);

        TextField nameTextField = new TextField();
        nameTextField.textProperty().addListener(e -> playerMap.get(num).set(0, nameTextField.getText()));
        playerBox.add(nameTextField, 1,1);

        final ColorPicker colorPicker1 = new ColorPicker(Color.GREEN);
        playerMap.get(num).set(1, String.valueOf(colorPicker1.getValue()));
        colorPicker1.setOnAction(e -> playerMap.get(num).set(1, String.valueOf(colorPicker1.getValue())));
        playerBox.add(colorPicker1, 1,2);

        ComboBox<String> myComboBox= new ComboBox<>();
        List<String> typeList = new ArrayList<>(List.of(getMyResources().getString("human"), getMyResources().getString("cpu")));
        myComboBox.getItems().addAll(typeList);
        myComboBox.setValue(getMyResources().getString("human"));
        myComboBox.setOnAction(e -> playerMap.get(num).set(2, myComboBox.getValue()));
        playerBox.add(myComboBox, 1, 3);
        return playerBox;
    }
}
