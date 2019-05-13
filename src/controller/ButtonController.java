package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import model.engine.ModelCheck;
import view.special.AuctionHandler;
import view.special.ChangeRuleHandler;
import model.PropertyExchange;
import view.special.TradeHandler;
import model.engine.ModelEngine;
import view.MonopolyView;
import java.util.Optional;

public class ButtonController {

    ModelEngine modelEngine;
    MonopolyView viewer;
    StatusController statusController;
    PropertyExchange propertyExchange;
    private AudioEffects audioEffects;
    private KeyCode[] lastFour = {KeyCode.UNDEFINED, KeyCode.UNDEFINED, KeyCode.UNDEFINED, KeyCode.UNDEFINED};
    private CPUController CPU;
    private ModelCheck modelCheck;

    public ButtonController(ModelEngine modelEngine, MonopolyView viewer) {
        this.modelEngine = modelEngine;
        this.viewer = viewer;
        statusController = new StatusController(modelEngine, viewer);
        setButtonActions();
        audioEffects = new AudioEffects(viewer);
        audioEffects.playGameMusic();
        CPU = new CPUController(viewer);
        this.modelCheck = new ModelCheck(modelEngine);
    }

    public void handleKeyPress(KeyCode code) {

        ActionEvent e = new ActionEvent();
        if (lastFour.length - 1 >= 0) System.arraycopy(lastFour, 1, lastFour, 0, lastFour.length - 1);
        lastFour[lastFour.length-1] = code;

        if (code.equals(KeyCode.R)) { rollDice(e); }
        if (code.equals(KeyCode.S)) { sellProperty(e); }
        if (code.equals(KeyCode.B)) { buyProperty(e); }
        if (code.equals(KeyCode.E)) { endTurn(e); }
        if (code.equals(KeyCode.F)) { forfeit(e); }
        if(code.equals(KeyCode.M)) { cheatMove(e); }
        if(code.equals(KeyCode.C)) { cheatMoney(e); }
        if(code.equals(KeyCode.P)) { saveGame(e); }
        if(code.equals(KeyCode.O)) { loadGame(e); }

        if(lastFour[0].equals(KeyCode.J) && lastFour[1].equals(KeyCode.A) && lastFour[2].equals(KeyCode.I) && lastFour[3].equals(KeyCode.L)) {
            modelEngine.giveOutOfJail();
        }

        if(lastFour[0].equals(KeyCode.DIGIT1) && lastFour[1].equals(KeyCode.DIGIT2) && lastFour[2].equals(KeyCode.DIGIT3) && lastFour[3].equals(KeyCode.DIGIT4)) {
            statusController.cheatsStatus(true);
        }
        if(lastFour[0].equals(KeyCode.DIGIT4) && lastFour[1].equals(KeyCode.DIGIT3) && lastFour[2].equals(KeyCode.DIGIT2) && lastFour[3].equals(KeyCode.DIGIT1)) {
            statusController.cheatsStatus(false);
        }
        if(lastFour[0].equals(KeyCode.DIGIT1) && lastFour[1].equals(KeyCode.DIGIT1) && lastFour[2].equals(KeyCode.DIGIT1) && lastFour[3].equals(KeyCode.DIGIT1)) {
            statusController.CPUSpeedStatus(true);
        }
        if(lastFour[0].equals(KeyCode.DIGIT2) && lastFour[1].equals(KeyCode.DIGIT2) && lastFour[2].equals(KeyCode.DIGIT2) && lastFour[3].equals(KeyCode.DIGIT2)) {
            statusController.CPUSpeedStatus(false);
        }
        if(lastFour[0].equals(KeyCode.Y) && lastFour[1].equals(KeyCode.E) && lastFour[2].equals(KeyCode.E) && lastFour[3].equals(KeyCode.T)) {
            modelEngine.removeAllButYou();
            endTurn(e);
        }
    }

    private void setButtonActions() {
        viewer.getRollDiceButton().setOnAction(this::rollDice);
        viewer.getEndTurnButton().setOnAction(this::endTurn);
        viewer.getBuildHousesButton().setOnAction(this::buildHouse);
        viewer.getSellHousesButton().setOnAction(this::sellHouse);
        viewer.getSellPropertyButton().setOnAction(this::sellProperty);
        viewer.getBuyPropertyButton().setOnAction(this::buyProperty);
        viewer.getBuildHotelButton().setOnAction(this::buildHotel);
        viewer.getSellHotelButton().setOnAction(this::sellHotel);
        viewer.getScreenHomeButton().setOnAction(this::screenHomeButton);
        viewer.getScreenSettingsButton().setOnAction(this::screenSettingsButton);
        viewer.getForfeitButton().setOnAction(this::forfeit);
        viewer.getStartOverButton().setOnAction(this::startOverGame);
        viewer.getCheatMoveButton().setOnAction(this::cheatMove);
        viewer.getCheatMoneyButton().setOnAction(this::cheatMoney);
        viewer.getEventLogButton().setOnAction(this::eventLog);
        viewer.getUseJailFreePerkButton().setOnAction(this::jailFree);
        viewer.getAuctionTradeButton().setOnAction(this::auctionTrade);
        viewer.getApplySettingsButton().setOnAction(this::applySettings);
        viewer.getChangeRuleButton().setOnAction(this::changeRules);
        viewer.getCPUSpeedButton().setOnAction(this::CPUSpeed);
    }

    //================================================================================
    // Button Event Actions
    //================================================================================

    public void startGame(String gameType) throws Exception {
        statusController.newTurnButtons();
        viewer.createDiceView(modelEngine.getDice());
        viewer.setPlayers(modelEngine.getPlayers());
        viewer.setTileMap(modelEngine.getBoard().getBoardMap());
        viewer.setupGame();
        viewer.goToNextScene();
        modelEngine.setupMode(gameType);
        updateGameSceneView();
        statusController.newTurnButtons();
        statusController.cheatsStatus(false);
        statusController.CPUSpeedStatus(false);
        checkAndActivateCPU();
    }

    private void rollDice(ActionEvent e) {
        audioEffects.playDice();
        modelEngine.rollDiceAndMovePlayer();
        viewer.updateDice();
        statusController.checkIfRolledSpecial();
        statusController.checkAboutTile();
        updateGameSceneView();
        checkBankrupt();
        checkGameEnd();
    }

    private void endTurn(ActionEvent e) {
        audioEffects.playNewTurn();
        modelEngine.endCurrentPlayerTurn(false);
        statusController.newTurnButtons();
        updateGameSceneView();
        checkGameEnd();
        checkAndActivateCPU();
    }

    private void buildHouse(ActionEvent e) {
        audioEffects.playClick();
        modelEngine.buildHouse();
        statusController.checkAboutTile();
        updateGameSceneView();
    }

    private void sellHouse(ActionEvent e) {
        audioEffects.playClick();
        modelEngine.sellHouse();
        updateGameSceneView();
        statusController.checkAboutTile();
        updateGameSceneView();
    }

    private void sellProperty(ActionEvent e) {
        audioEffects.playClick();
        modelEngine.sellProperty();
        statusController.checkAboutTile();
        updateGameSceneView();
    }

    private void buyProperty(ActionEvent e) {
        audioEffects.playClick();
        modelEngine.buyProperty();
        statusController.checkAboutTile();
        updateGameSceneView();
    }

    private void buildHotel(ActionEvent e) {
        audioEffects.playClick();
        modelEngine.buildHotel();
        statusController.checkAboutTile();
        updateGameSceneView();
    }

    private void sellHotel(ActionEvent e) {
        audioEffects.playClick();
        modelEngine.sellHotel();
        statusController.checkAboutTile();
        updateGameSceneView();
    }

    private void screenHomeButton(ActionEvent e) { viewer.goToHomeScene(); }

    private void forfeit(ActionEvent e) {
        modelEngine.forfeit();
        updateGameSceneView();
        if (! checkGameEnd()) {
            audioEffects.playForfeit();
        }
        else {
            audioEffects.playWoo();
        }
    }

    private void startOverGame(ActionEvent e) {
        audioEffects.playClick();
        audioEffects.stopEndMusic();
        viewer.goToHomeScene();
        audioEffects.playGameMusic();
    }

    private void jailFree(ActionEvent e) {
        audioEffects.playClick();
        modelEngine.useFreeJailCard();
        viewer.getUseJailFreePerkButton().setDisable(true);
    }

    private void auctionTrade(ActionEvent e){
        audioEffects.playClick();
        auctionTradeDialog();
        updateGameSceneView();
    }

    private void screenSettingsButton(ActionEvent e) { viewer.goToNextScene(); }

    private void eventLog(ActionEvent e) { displayEventLog(); }

    private void applySettings(ActionEvent e){
        viewer.updateThemeToSettings();
        viewer.goToGameScene();
    }

    private void cheatMove(ActionEvent e) {
        cheatMoveDialog();
        modelEngine.performTileAction();
        statusController.checkAboutTile();
        updateGameSceneView();
        checkBankrupt();
        checkGameEnd();
    }

    private void cheatMoney(ActionEvent e) {
        cheatMoneyDialog();
        updateGameSceneView();
        checkBankrupt();
        checkGameEnd();
    }

    private void CPUSpeed(ActionEvent e) {
        CPUSpeedDialog();
        updateGameSceneView();
    }

    private void changeRules(ActionEvent e){
        changeRuleDialog();
        updateGameSceneView();
    }

    private void saveGame(ActionEvent e) {
        modelEngine.saveGame();
    }

    private void loadGame(ActionEvent e) {
        modelEngine.loadGame();
        viewer.setGamePlayers(modelEngine.getPlayers());
    }

    //================================================================================
    // Update/check methods
    //================================================================================

    private void checkAndActivateCPU() {
        statusController.checkIfCPU();
        if (modelCheck.isCurrentPlayerCPU()) {
            CPU.actionSequence();
        }
    }

    private void updateGameSceneView(){ viewer.updateChipTileViewAndMessage(modelEngine.getConsoleMessage()); }

    private boolean checkGameEnd() {
        if (modelEngine.isGameEnd()) {
            viewer.setWinner(modelEngine.whoIsWinner());
            viewer.setupEnd();
            viewer.goToEndScene();
            audioEffects.stopGameMusic();
            audioEffects.playEndMusic();
            modelEngine.updateConsoleAndEventLog("Game Ended!");
            return true;
        }
        return false;
    }

    private void checkBankrupt() {
        modelEngine.bankruptPlayer();
    }

    //================================================================================
    // Dialog methods
    //================================================================================

    private void cheatMoveDialog() {
        Dialog dialog = viewer.cheatMoveDialog();
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(numberOfSpaces -> {
            int simulatedDiceRolled = Integer.parseInt(result.get());
            modelEngine.moveCurrentPlayer(simulatedDiceRolled);
        });
    }

    private void cheatMoneyDialog() {
        Dialog dialog = viewer.cheatMoneyDialog();
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(money -> {
            int amountOfMoney = Integer.parseInt(result.get());
            modelEngine.changeCurrentPlayerFunds(amountOfMoney);
        });
    }

    private void CPUSpeedDialog() {
        Dialog dialog = viewer.CPUSpeedDialog();
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(thinkingTime -> {
            double speed = Double.parseDouble(result.get());
            CPU.setTHINKING_TIME_CONSTANT(speed);
        });
    }

    private void auctionTradeDialog() {
        Alert alert = viewer.auctionTradeDialog();
        ButtonType auction = new ButtonType("Auction");
        ButtonType trade = new ButtonType("Trade");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(auction, trade);
        Optional<ButtonType> option = alert.showAndWait();
        if(option.get() == auction){ auctionDialog(); }
        else if(option.get() == trade){ tradeDialog(); }
    }

    private void auctionDialog() {
        AuctionHandler auctionHandler = new AuctionHandler(modelEngine);
        auctionHandler.auctionDialog();
    }

    private void tradeDialog(){
        TradeHandler tradeHandler = new TradeHandler(modelEngine);
        tradeHandler.tradeDialog();
    }

    private void changeRuleDialog(){
        ChangeRuleHandler changeRuleHandler = new ChangeRuleHandler(modelEngine);
        changeRuleHandler.ruleDialog();
    }

    private void displayEventLog() {
        Alert dialog = viewer.displayEventLog();
        TextArea textArea = new TextArea(modelEngine.getEventLog().getLogAsString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        dialog.getDialogPane().setContent(textArea);
        dialog.showAndWait();
    }
}