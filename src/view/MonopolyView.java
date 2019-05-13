package view;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.dice.Dice;
import model.player.Player;
import model.tile.Tile;
import view.infopanel.DiceView;
import view.screens.*;
import java.util.ResourceBundle;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class MonopolyView {

    private final static int SCREEN_WIDTH = 1250;
    private final static int SCREEN_HEIGHT = 650;
    private final static int TOTAL_NUM_SCENES = 6;
    private int currentSceneIndex = 0;
    private int nextSceneIndex = 0;
    private Stage myStage;
    private Stage addStage;
    private ResourceBundle myResources;
    private AbstractScreenView[] starterScenes;
    private Button rollDiceButton;
    private Button endTurnButton;
    private Button buildHousesButton;
    private Button sellHousesButton;
    private Button buyPropertyButton;
    private Button sellPropertyButton;
    private Button buildHotelButton;
    private Button sellHotelButton;
    private Button screenPlayButton;
    private Button screenNextButton;
    private Button startGameButton;
    private Button screenHomeButton;
    private Button screenSettingsButton;
    private Button togglePlayerStatsButton;
    private Button forfeitButton;
    private Button startOverButton;
    private Button cheatMoveButton;
    private Button cheatMoneyButton;
    private Button CPUSpeedButton;
    private Button eventLogButton;
    private Button useJailFreePerkButton;
    private Button auctionTradeButton;
    private Button applySettingsButton;
    private Button changeRuleButton;
    private String[] langList;
    private ChoiceBox languageChoiceBox;
    private Slider musicVolumeSlider;
    private Slider fxVolumeSlider;
    private Map<String, Button> buttonMap;
    private Map<Integer, List<String>> playerInfoMap;
    private List<Player> myPlayers;
    private Dice myDice;
    private Player myWinner;
    private Map<Integer, Tile> tileMap;
    private DiceView myDiceView;

    public MonopolyView(ResourceBundle myResources, Stage primaryStage){
        myStage = primaryStage;
        this.myResources = myResources;
        createButtons();
        createChoiceBox();
        createVolumeSliders();
        initSplashScene();
        myStage.setScene(starterScenes[currentSceneIndex].getScene());
    }

    //================================================================================
    // Setup
    //================================================================================

    public void addSecondScreen() {

    }

    private void createButtons(){
        buttonMap = new HashMap<>();
        rollDiceButton = createButton("rollDiceButton");
        endTurnButton = createButton("endTurnButton");
        buildHousesButton = createButton("buildHouseButton");
        sellHousesButton = createButton("sellHouseButton");
        buildHotelButton = createButton("buildHotel");
        sellHotelButton = createButton("sellHotel");
        sellPropertyButton = createButton("sellPropertyButton");
        buyPropertyButton = createButton("buyPropertyButton");
        screenPlayButton = createButton("playButton");
        screenNextButton = createButton("nextButton");
        startGameButton = createButton("startButton");
        screenHomeButton = createButton("homeButton");
        screenSettingsButton = createButton("settingsButton");
        togglePlayerStatsButton = createButton("playerStatsButton");
        forfeitButton = createButton("forfeitButton");
        startOverButton = createButton("startOverButton");
        cheatMoveButton = createButton("cheatMoveButton");
        cheatMoneyButton = createButton("cheatMoneyButton");
        eventLogButton = createButton("eventLogButton");
        useJailFreePerkButton = createButton("jailFreeButton");
        auctionTradeButton = createButton("auctionTradeButton");
        applySettingsButton = createButton("applySettingsButton");
        CPUSpeedButton = createButton("CPUSpeedButton");
        changeRuleButton = createButton("changeRuleButton");
    }

    private void createChoiceBox(){
        //String[] list = {myResources.getString("english"), myResources.getString("spanish"),myResources.getString("chinese"),myResources.getString("japanese")};
        String strList = myResources.getString("languageList");
        String[] split = strList.split(",");
        langList = split;
        languageChoiceBox = new ChoiceBox(FXCollections.observableArrayList(langList));
        languageChoiceBox.setId("languageBox");
        languageChoiceBox.setTooltip(new Tooltip(myResources.getString("SelectLanguage")));
    }

    private void createVolumeSliders(){
        musicVolumeSlider = new Slider(0,10, 5);
        setInitialSlider(musicVolumeSlider);
        fxVolumeSlider = new Slider(0,10, 5);
        setInitialSlider(fxVolumeSlider);
    }

    private void setInitialSlider(Slider slider) {
        slider.setMin(0);
        slider.setMax(1);
        slider.setValue(0.5);
        slider.setBlockIncrement(0.1);
    }

    private Button createButton(String text){
        Button button = new Button(myResources.getString(text));
        buttonMap.put(text, button);
        return button;
    }

    public void createDiceView(Dice dice) {
        myDice = dice;
        myDiceView = new DiceView(myDice, (int)(SCREEN_WIDTH*.001),(int)(SCREEN_HEIGHT*.7));

    }

    private void initSplashScene(){
        starterScenes = new AbstractScreenView[TOTAL_NUM_SCENES];
        starterScenes[0] = new SplashScreen(getLanguageChoiceBox(), screenPlayButton, myResources, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public void initSetUpScene(){
        starterScenes[1] = new SetUpScreen(screenNextButton, myResources, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public void setupPlayerConfig(){
        playerInfoMap = new HashMap<>();
        starterScenes[2] = new PlayerSettingsScreen(getNumPlayers(), playerInfoMap, startGameButton, myResources, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public void setupGame() {
        starterScenes[3] = new GamePlayScreen(getTheme(),getGameType(), getPlayerColorList(),tileMap, buttonMap, myDiceView, myPlayers, myResources, SCREEN_WIDTH, SCREEN_HEIGHT);
        starterScenes[4] = new SettingsScreen(musicVolumeSlider, fxVolumeSlider, getTheme(), getApplySettingsButton(), myResources, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public void setupEnd() {
        starterScenes[5] = new EndScreen(startOverButton,myResources, SCREEN_WIDTH,SCREEN_HEIGHT,myWinner.getName());
    }

    //================================================================================
    // Updating Scene/Game
    //================================================================================

    private void step(){
        if(currentSceneIndex != nextSceneIndex){
            currentSceneIndex = nextSceneIndex;
            myStage.setScene(starterScenes[currentSceneIndex].getScene());
        }
    }

    public void updateChipTileViewAndMessage(String text){
        starterScenes[3].update(text);
    }

    public void goToNextScene(){
        if (currentSceneIndex == starterScenes.length - 1) {
            currentSceneIndex = 0;
            setNextSceneIndex(currentSceneIndex);
        }
        else {
            setNextSceneIndex(currentSceneIndex+1);
        }
    }

    public void goToHomeScene() {
        setNextSceneIndex(0);
    }

    public void goToGameScene(){
        setNextSceneIndex(3);
    }

    public void goToEndScene(){
        setNextSceneIndex(5);
    }

    public void setNextSceneIndex(int nextSceneIndex) {
        this.nextSceneIndex = nextSceneIndex;
        step();
    }

    public void updateDice() {
        myDiceView.updateAllDice();
        myDiceView.rollAnimation();
    }

    //================================================================================
    // Button Getters
    //================================================================================

    public Button getRollDiceButton() {
        return rollDiceButton;
    }

    public Button getEndTurnButton() {
        return endTurnButton;
    }

    public Button getBuildHousesButton() {
        return buildHousesButton;
    }

    public Button getSellHousesButton() {
        return sellHousesButton;
    }

    public Button getSellPropertyButton() {
        return sellPropertyButton;
    }

    public Button getBuyPropertyButton() {
        return buyPropertyButton;
    }

    public Button getBuildHotelButton() {
        return buildHotelButton;
    }

    public Button getSellHotelButton() { return sellHotelButton; }

    public Button getScreenPlayButton() {
        return screenPlayButton;
    }

    public Button getScreenNextButton() {
        return screenNextButton;
    }

    public Button getStartGameButton() {
        return startGameButton;
    }

    public Button getScreenHomeButton() {
        return screenHomeButton;
    }

    public Button getScreenSettingsButton() {
        return screenSettingsButton;
    }

    public Button getTogglePlayerStatsButton() {
        return togglePlayerStatsButton;
    }

    public Button getForfeitButton() {
        return forfeitButton;
    }

    public Button getCheatMoveButton() {
        return cheatMoveButton;
    }

    public Button getCheatMoneyButton() {
        return cheatMoneyButton;
    }

    public Button getCPUSpeedButton() {
        return CPUSpeedButton;
    }

    public Button getEventLogButton() {
        return eventLogButton;
    }

    public Button getStartOverButton() {
        return startOverButton;
    }

    public Button getAuctionTradeButton() { return auctionTradeButton; }

    public Button getUseJailFreePerkButton() {
        return useJailFreePerkButton;
    }

    public Button getChangeRuleButton() {return changeRuleButton; }

    public Button getApplySettingsButton(){
        return applySettingsButton;
    }

    public ChoiceBox getLanguageChoiceBox() {
        return languageChoiceBox;
    }

    public String[] getLangList() {
        return langList;
    }

    public Slider getMusicVolumeSlider() {
        return musicVolumeSlider;
    }

    public Slider getFxVolumeSlider() {
        return fxVolumeSlider;
    }

    //================================================================================
    // Instance Variables Getter/Setters
    //================================================================================

    public void setPlayers(List<Player> players) {
        this.myPlayers = players;
    }

    public Dice getDice() {
        return myDice;
    }

    public void setDice(Dice myDice) {
        this.myDice = myDice;
    }

    public void setWinner(Player winner) {
        this.myWinner = winner;
    }

    public void setTileMap(Map<Integer, Tile> tileMap){
        this.tileMap = tileMap;
    }

    public ResourceBundle getMyResources() {
        return myResources;
    }

    public void setMyResources(ResourceBundle myResources) {
        this.myResources = myResources;
    }

    public void setGamePlayers(List<Player> players) {
        ((GamePlayScreen) starterScenes[3]).setPlayerList(players);
    }

    //================================================================================
    // Misc Getter/Setters for Controller
    //================================================================================

    public Scene getMyScene() {
        return starterScenes[3].getScene();
    }

    public int getNumPlayers(){
        return starterScenes[1].getNumPlayers();
    }

    public String getGameType(){
        return starterScenes[1].getGameType();
    }

    public String getTheme(){
        return starterScenes[1].getTheme();
    }

    public void setTheme(String theme){
        starterScenes[1].setTheme(theme);
    }

    public void updateThemeToSettings() {
        if(starterScenes[4].getTheme() !=null){
            setTheme(starterScenes[4].getTheme());
            starterScenes[3].setTheme(starterScenes[4].getTheme());
            starterScenes[1].setTheme(starterScenes[4].getTheme());
        }
    }

    public void setType(String type) {
        starterScenes[1].setGameType(type);
    }

    public String getPlayerName(int index){
        String name = playerInfoMap.get(index+1).get(0);
        if(name==null || name.isEmpty()){
            return String.format(myResources.getString("playerNum"), index+1);
        }
        return name;
    }

    public boolean isPlayerCPU (int index){
        return playerInfoMap.get(index+1).get(2).equals(myResources.getString("cpu"));
    }

    public List<Color> getPlayerColorList(){
        List<Color> playerColorList = new ArrayList<>();
        for(int i=1; i<=getNumPlayers(); i++){
            playerColorList.add(Color.valueOf(playerInfoMap.get(i).get(1)));
        }
        return playerColorList;
    }

    public void updateButtonLanguage(){
        for(String buttonName: buttonMap.keySet()){
            buttonMap.get(buttonName).setText(myResources.getString(buttonName));
        }
    }

    //DIALOG BOXES

    public Dialog cheatMoveDialog() { //
        TextInputDialog dialog = new TextInputDialog(myResources.getString("typeHere"));
        dialog.setTitle(myResources.getString("cheatTitle"));
        dialog.setHeaderText(myResources.getString("cheatMoveHeader"));
        dialog.setContentText(myResources.getString("cheatMoveContent"));
        return dialog;
    }

    public Dialog cheatMoneyDialog() { //
        TextInputDialog dialog = new TextInputDialog(myResources.getString("typeHere"));
        dialog.setTitle(myResources.getString("cheatTitle"));
        dialog.setHeaderText(myResources.getString("cheatMoneyHeader"));
        dialog.setContentText(myResources.getString("cheatMoneyContent"));
        return dialog;
    }

    public Dialog CPUSpeedDialog() { //
        TextInputDialog dialog = new TextInputDialog(myResources.getString("typeHere"));
        dialog.setTitle(myResources.getString("CPUSpeedButton"));
        dialog.setHeaderText(myResources.getString("CPUSpeedButton"));
        dialog.setTitle(myResources.getString("CPUSpeedButton"));
        dialog.setContentText("0.1 - 4");
        return dialog;
    }

    public Alert auctionTradeDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(myResources.getString("auctionTradeTitle"));
        alert.setHeaderText(myResources.getString("auctionTradeHeader"));
        return alert;
    }

    public Alert displayEventLog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(myResources.getString("eventLogTitle"));
        alert.setHeaderText(myResources.getString("eventLogHeader"));
        return alert;
    }
}