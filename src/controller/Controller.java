package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import model.engine.ModelEngine;
import view.MonopolyView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller {

    private String DEFAULT_RESOURCE_LOCATION = "data/gamefiles";
    private String SETUP_RESOURCE_LOCATION = "gamefiles/setupfiles";
    private String LANGUAGE_RESOURCE_LOCATION = "gamefiles/languagefiles";
    private MonopolyView viewer;
    private ModelEngine modelEngine;
    private ResourceBundle setupResources;
    private String gameType;
    private ButtonController buttonController;
    private File propertiesInfoFile;
    private File perksInfoFile;
    private File tilesLayoutFile;
    private ResourceBundle propertyColorFile;
    private ResourceBundle languageFile;

    public Controller(ResourceBundle languageFile, ModelEngine modelEngine, MonopolyView viewer) {
        this.languageFile = languageFile;
        this.modelEngine = modelEngine;
        this.viewer = viewer;
        buttonController = new ButtonController(modelEngine, viewer);
        setSetupButtonActions();
    }

    private void setKeyPress() {
        var myScene = viewer.getMyScene();
        myScene.setOnKeyPressed(e -> buttonController.handleKeyPress(e.getCode()));
    }

    private void setupFiles() {
        gameType = viewer.getGameType();
        setupResources = ResourceBundle.getBundle(String.format("%1$s.%2$s%3$s", SETUP_RESOURCE_LOCATION, gameType, "Setup"));
        String resourcesPath = String.format("%1$s/%2$s", DEFAULT_RESOURCE_LOCATION, setupResources.getString("PackageName"));
        propertiesInfoFile = new File(String.format("%1$s/%2$s", resourcesPath, setupResources.getString("PropertyInfoFile")));
        perksInfoFile = new File(String.format("%1$s/%2$s", resourcesPath, setupResources.getString("PerkInfoFile")));
        tilesLayoutFile = new File(String.format("%1$s/%2$s", resourcesPath, setupResources.getString("TileLayoutFile")));
        propertyColorFile = ResourceBundle.getBundle(String.format("%1$s.%2$s.%3$s", "gamefiles", setupResources.getString("PackageName"), setupResources.getString("PropertyColorFile")));
        modelEngine.setHouseToHotelThreshold(Integer.parseInt(setupResources.getString("HouseToHotelThreshold")));
        modelEngine.setup(propertiesInfoFile, perksInfoFile, tilesLayoutFile, languageFile);
        modelEngine.setTileColors(propertyColorFile);
        modelEngine.createDice(Integer.parseInt(setupResources.getString("DiceNumberOfDie")), Integer.parseInt(setupResources.getString("DiceNumberOfFaces")));
    }

    private void setUpPlayers() {
        List<String> playerNames = new ArrayList<>();
        List<Boolean> playerCPUs = new ArrayList<>();
        for(int i=0; i<viewer.getNumPlayers(); i++){
            playerNames.add(viewer.getPlayerName(i));
            playerCPUs.add(viewer.isPlayerCPU(i));
        }
        int playerInitialBalance = Integer.parseInt(setupResources.getString("PlayerInitialBalance"));
        modelEngine.setUpPlayers(playerNames, playerCPUs, playerInitialBalance);
        modelEngine.setUpOtherPlayers(modelEngine.getPlayers());
    }

    //================================================================================
    // GameScene Setup Buttons (needed before files are read in)
    //================================================================================

    private void setSetupButtonActions() {
        viewer.getStartGameButton().setOnAction(this::startGameButton);
        viewer.getScreenPlayButton().setOnAction(this::screenPlayButton);
        viewer.getScreenNextButton().setOnAction(this::screenNextButton);
        viewer.getLanguageChoiceBox().setOnAction(this::changeLanguage);
    }

    private void screenPlayButton(ActionEvent e) {
        viewer.initSetUpScene();
        viewer.goToNextScene();
    }

    private void screenNextButton(ActionEvent e) {
        viewer.setupPlayerConfig();
        viewer.goToNextScene();
        setupFiles();
    }

    private void startGameButton(ActionEvent e) {
        try {
            setUpPlayers();
            buttonController.startGame(gameType);
            setKeyPress();
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
    }

    private void changeLanguage(Event e){
        viewer.setMyResources(ResourceBundle.getBundle(String.format("%1$s.%2$s", LANGUAGE_RESOURCE_LOCATION, viewer.getLanguageChoiceBox().getValue())));
        viewer.updateButtonLanguage();
        languageFile = ResourceBundle.getBundle(String.format("%1$s.%2$s", LANGUAGE_RESOURCE_LOCATION, viewer.getLanguageChoiceBox().getValue()));
    }
}