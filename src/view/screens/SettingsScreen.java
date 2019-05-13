package view.screens;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.util.ResourceBundle;

public class SettingsScreen extends AbstractScreenView {

    private final static String BACKGROUND_IMAGE = "background_settings.jpg";
    private static final String STYLESHEET = "styles.css";
    private String theme;

    public SettingsScreen(Slider mVolumeBox, Slider fxVolumeBox, String theme, Button applyButton, ResourceBundle resources, int sceneWidth, int sceneHeight) {
        super(resources);
        this.theme = theme;
        StackPane myRoot = new StackPane();
        VBox myRootVBox = new VBox(10);
        myRootVBox.setPadding(new Insets(0,0,100,0));
        Scene scene = new Scene(myRoot, sceneWidth, sceneHeight);
        scene.getStylesheets().add(STYLESHEET);
        setBackground(BACKGROUND_IMAGE, myRoot);
        applyButton.setId("startScreenButton");

        myRootVBox.getChildren().addAll(makeThemeChanger(), makeSlider("musicVolumeLabel",mVolumeBox)
                ,makeSlider("fxVolumeLabel",fxVolumeBox), applyButton);
        myRootVBox.setAlignment(Pos.BOTTOM_CENTER);
        myRoot.getChildren().addAll(myRootVBox);
        setScene(scene);
    }

    private HBox makeSlider(String label, Slider slider){
        HBox volumeBox = new HBox(10);
        volumeBox.setAlignment(Pos.CENTER);
        Label volumeLabel = new Label(getMyResources().getString(label));
        volumeLabel.setId("whiteLabel");
        slider.setShowTickMarks(true);
        volumeBox.getChildren().addAll(volumeLabel, slider);
        return volumeBox;
    }

    private HBox makeThemeChanger(){
        HBox themeBox = new HBox(10);
        Label themeLabel = new Label(getMyResources().getString("themeLabel"));
        themeLabel.setId("whiteLabel");
        ChoiceBox<String> themeChoiceBox = new ChoiceBox(FXCollections.observableArrayList(getMyResources().getString("regular"),
                getMyResources().getString("space"), getMyResources().getString("nature")));
        themeChoiceBox.setValue(theme);
        themeChoiceBox.setId("themeButtonSetting");
        themeChoiceBox.setOnAction(e -> setTheme(themeChoiceBox.getValue()));
        themeBox.getChildren().addAll(themeLabel, themeChoiceBox);
        themeBox.setAlignment(Pos.CENTER);
        return themeBox;
    }

    @Override
    public void update(String text) {

    }
}
