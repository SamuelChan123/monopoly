package view.screens;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SetUpScreen extends AbstractScreenView {

    private final static String BACKGROUND_IMAGE = "background2.jpg";
    private static final String STYLESHEET = "styles.css";
    private static final int MAX_NUM_PLAYERS = 9;
    private final ToggleGroup gameGroup;
    private final ToggleGroup themeGroup;
    private VBox myRoot;

    public SetUpScreen(Button nextButton, ResourceBundle resourceBundle, int sceneWidth, int sceneHeight){
        super(resourceBundle);
        gameGroup = new ToggleGroup();
        themeGroup = new ToggleGroup();
        StackPane myRootStack = new StackPane();
        myRoot = new VBox(10);
        myRoot.setPadding(new Insets(0,0,80,0));
        myRoot.setAlignment(Pos.BOTTOM_CENTER);
        Scene scene = new Scene(myRootStack, sceneWidth, sceneHeight);
        scene.getStylesheets().add(STYLESHEET);

        setBackground(BACKGROUND_IMAGE, myRootStack);
        nextButton.setId("startScreenButton");
        makeGameTypeToggleButtons();
        makeThemeToggleButtons();
        makePlayerInput();
        myRoot.getChildren().addAll(new VBox(100), nextButton);
        myRootStack.getChildren().add(myRoot);
        setScene(scene);
    }

    private void makeGameTypeToggleButtons(){
        HBox gameTypeBox = new HBox(20);
        gameTypeBox.setAlignment(Pos.CENTER);
        Label gameTypeLabel = new Label(getMyResources().getString("gameTypeLabel"));
        gameTypeLabel.setId("whiteLabel");
        ToggleButton standardTB = createToggleButton(getMyResources().getString("standardB"), gameGroup);
        standardTB.setSelected(true);
        setGameType(getMyResources().getString("standardB"));
        //standardTB.setOn
        ToggleButton millionaireB = createToggleButton(getMyResources().getString("millionaireB"), gameGroup);
        ToggleButton juniorTB = createToggleButton(getMyResources().getString("juniorB"), gameGroup);
        gameGroup.selectedToggleProperty().addListener((ov, toggle, new_toggle) -> {
            if(gameGroup.getSelectedToggle() != null) {
                setGameType((String) gameGroup.getSelectedToggle().getUserData());
                //System.out.println(getGameType());
            }
        });
        gameTypeBox.getChildren().addAll(gameTypeLabel, standardTB, juniorTB, millionaireB);
        myRoot.getChildren().add(gameTypeBox);
    }

    private void makeThemeToggleButtons(){
        HBox themeBox = new HBox(20);
        themeBox.setAlignment(Pos.CENTER);
        Label themeLabel = new Label(getMyResources().getString("themeLabel"));
        themeLabel.setId("whiteLabel");
        ToggleButton regularTB = createToggleButton(getMyResources().getString("regular"), themeGroup);
        regularTB.setSelected(true);
        setTheme(getMyResources().getString("regular"));
        ToggleButton spaceTB = createToggleButton(getMyResources().getString("space"), themeGroup);
        ToggleButton natureTB = createToggleButton(getMyResources().getString("nature"), themeGroup);
        themeGroup.selectedToggleProperty().addListener((ov, toggle, new_toggle) -> {
            if(themeGroup.getSelectedToggle() !=null){
                setTheme((String)themeGroup.getSelectedToggle().getUserData());
            }
        });
        themeBox.getChildren().addAll(themeLabel, regularTB, spaceTB, natureTB);
        myRoot.getChildren().add(themeBox);
    }

    private ToggleButton createToggleButton(String type, ToggleGroup group){
        ToggleButton tb = new ToggleButton(type);
        tb.setToggleGroup(group);
        tb.setUserData(type);
        tb.setId("gameTypeButton");
        return tb;
    }

    private void makePlayerInput(){
        HBox playerInputBox = new HBox(10);
        playerInputBox.setId("numPlayerScreen");
        playerInputBox.setAlignment(Pos.CENTER);

        Label numPlayer = new Label(getMyResources().getString("numPlayerInputLabel"));
        numPlayer.setId("whiteLabel");
        ComboBox<Integer> myComboBox = new ComboBox<>();
        List<Integer> numList = new ArrayList<>();
        for(int i=2; i<=MAX_NUM_PLAYERS; i++){
            numList.add(i);
        }
        myComboBox.getItems().addAll(numList);
        myComboBox.setValue(2);
        setNumPlayers(myComboBox.getValue());
        myComboBox.setOnAction(actionEvent -> setNumPlayers(myComboBox.getValue()));
        playerInputBox.getChildren().addAll(numPlayer, myComboBox);
        myRoot.getChildren().add(playerInputBox);
    }

    @Override
    public void update(String text) {

    }
}
