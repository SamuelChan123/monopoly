package model.engine;

import model.card.perkcard.*;
import model.card.propertycard.PropertyCard;
import model.configreader.BoardConfig;
import model.configreader.PerkConfig;
import model.configreader.PropertyConfig;
import model.dice.Dice;
import model.EventLog;
import model.player.Player;
import model.board.Board;
import model.tile.*;
import model.win.WinStandard;
import model.win.WinCondition;
import model.win.WinMillionaire;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class ModelEngine {

    private List<Player> allPlayers;
    private int currentPlayerIndex;
    private int houseToHotelThreshold;
    private Player currentPlayer;
    private List<PropertyCard> myPropertyCardList;
    private List<String> myPropertyCardTitles;
    private List<PerkCard> myPerkCardList;
    private List<String> myPerkCardTitles;
    private List<Integer> diceRolledList;
    private String gameType;
    private Board myBoard;
    private Dice myDice;
    private JailTile jailTile;
    private FreeParkTile freeParkTile;
    private WinCondition winCond;
    private ResourceBundle languageFile;
    private File propertiesInfoFile;
    private File perksInfoFile;
    private File tilesLayoutFile;
    private String consoleMessage;
    private EventLog eventLog;
    private ModelActions modelActions;

    public ModelEngine() {
        allPlayers = new ArrayList<>();
        eventLog = new EventLog();
        myPropertyCardTitles = new ArrayList<>();
        myPerkCardTitles = new ArrayList<>();
    }

    //================================================================================
    // Game Setup
    //================================================================================

    public void setup(File propertiesInfoFile, File perksInfoFile, File tilesLayoutFile, ResourceBundle languageFile) {
        setFiles(propertiesInfoFile, perksInfoFile, tilesLayoutFile, languageFile);
        setupObjects();
    }

    private void setFiles(File propertiesInfoFile, File perksInfoFile, File tilesLayoutFile, ResourceBundle languageFile) {
        this.propertiesInfoFile = propertiesInfoFile;
        this.perksInfoFile = perksInfoFile;
        this.tilesLayoutFile = tilesLayoutFile;
        this.languageFile = languageFile;
    }

    public void setUpPlayers(List<String> playerNames, List<Boolean> playerCPUs, int initialBalance) {
        allPlayers.clear();
        for (int i = 0; i <playerNames.size(); i++) {
            String name = playerNames.get(i);
            boolean isCPU = playerCPUs.get(i);
            if (! name.equals("")) {
                Player player = new Player(name, 0, initialBalance, isCPU);
                player.setDice(myDice);
                allPlayers.add(player);
            }
        }
        currentPlayerIndex = 0;
        currentPlayer = allPlayers.get(currentPlayerIndex);
        for(Player player : allPlayers){
            player.setOtherPlayers(allPlayers);
        }
        modelActions = new ModelActions(this);
    }

    public void setUpOtherPlayers(List<Player> players){
        for (Player player : players){
            player.setOtherPlayers(players);
        }
    }

    private void setupObjects() {
        BoardConfig boardConfig = new BoardConfig();
        PerkConfig perkConfig = new PerkConfig();
        PropertyConfig propertyConfig = new PropertyConfig();
        myBoard = boardConfig.makeBoardFromXML(tilesLayoutFile);
        myPropertyCardList = propertyConfig.makePropertiesInfoFromXML(propertiesInfoFile);
        myPerkCardList = perkConfig.makePerkCardsFromXML(perksInfoFile);
        populatePropertyTilesWithCards(myBoard,myPropertyCardList);
        populatePerkTilesWithDecks(myBoard,myPerkCardList);
        setJailInfo(myBoard,myPerkCardList);
        setFreeParkInfo(myBoard,myPerkCardList);
    }

    public void createDice(int numberOfDie, int numberOfDice ){ myDice = new Dice(numberOfDie, numberOfDice); }

    public void setHouseToHotelThreshold(int threshold) { houseToHotelThreshold = threshold; }

    public void setupMode(String mode) {
        consoleMessage = languageFile.getString("welcome") + mode;
        gameType = mode;
        eventLog.addAndPrintEvent(consoleMessage);
        if (mode.equalsIgnoreCase("Standard")) {
            winCond = new WinStandard();
        }
        if (mode.equalsIgnoreCase("Millionaire")) {
            winCond = new WinMillionaire();
        }
        if (mode.equalsIgnoreCase("Junior")) {
            winCond = new WinStandard();
        }
    }

    public void setTileColors(ResourceBundle colorFile) {
        for (int i = 0; i < myBoard.getMaxIndex(); i++) {
            Tile tile = myBoard.getTile(i);
            if (tile instanceof PropertyTile) {
                int propertyGroup = tile.getPropertyGroup();
                tile.setColor(colorFile.getString("propertygroup" + propertyGroup));
            }
            else {
                tile.setColor(colorFile.getString("other"));
            }
        }
    }

    private void populatePropertyTilesWithCards(Board board, List<PropertyCard> propertyCardList){
        Map<Integer,Tile> myBoardMap = board.getBoardMap();
        for(int i = 0; i < myBoardMap.size(); i++){
            Tile tile = myBoardMap.get(i);
            if(tile instanceof PropertyTile){
                setPropertyCards((PropertyTile) tile, propertyCardList);
            }
            if(tile instanceof RealEstateTile) {
                ((RealEstateTile) tile).getCard().setThreshold(houseToHotelThreshold);
            }
        }
    }

    private void setPropertyCards(PropertyTile tile, List<PropertyCard> list){

        PropertyCard acard = null;
        int propertyGroup = tile.getPropertyGroup();
        int groupNum = tile.getGroupNum();
        for (PropertyCard card : list) {
            if (card.getPropertyGroup() == 9) {
                acard = card;
            }
            if (card.getPropertyGroup() == propertyGroup && card.getGroupNum() == groupNum) {
                tile.setCard(card);
            }
        }
    }

    private void populatePerkTilesWithDecks(Board board, List<PerkCard> perkCardList){
        Map<Integer,Tile> myBoardMap = board.getBoardMap();
        for(int i = 0; i < myBoardMap.size(); i++){
            Tile tile = myBoardMap.get(i);
            if(tile instanceof ChancePerkTile || tile instanceof CommunityPerkTile){
                ((PerkTile) tile).setDeck(perkCardList);
            }
        }
    }

    private void setJailInfo(Board board, List<PerkCard> perkCardList){
        Map<Integer,Tile> myBoardMap = board.getBoardMap();
        for(int i = 0; i < myBoardMap.size(); i++){
            Tile tile = myBoardMap.get(i);
            if(tile instanceof JailTile){
                jailTile = (JailTile) tile;
                populateJailInfo(board, perkCardList, myBoardMap);
            }
        }
    }

    private void populateJailInfo(Board board, List<PerkCard> perkCardList, Map<Integer,Tile> myBoardMap) {
        for (PerkCard perkCard : perkCardList) {
            if (perkCard instanceof JailPerkCard) {
                ((JailPerkCard) perkCard).setJailTile(jailTile);
            }
            if (perkCard instanceof NearTilesPerkCard) {
                ((NearTilesPerkCard) perkCard).setBoard(board);
            }
        }
        for(Tile goToJailTile : myBoardMap.values()){
            if (goToJailTile instanceof GoToJailTile){
                ((GoToJailTile) goToJailTile).setJailTile(jailTile);
            }
        }
    }

    private void setFreeParkInfo(Board board, List<PerkCard> perkCardList){
        Map<Integer,Tile> myBoardMap = board.getBoardMap();
        for(int i = 0; i < myBoardMap.size(); i++){
            Tile tile = myBoardMap.get(i);
            if(tile instanceof FreeParkTile){
                freeParkTile = (FreeParkTile) tile;
                populateFreePark(perkCardList, myBoardMap);
            }
        }
    }

    private void populateFreePark(List<PerkCard> perkCardList, Map<Integer,Tile> myBoardMap) {
        for (PerkCard perkCard : perkCardList) {
            if (perkCard instanceof ChangeFundsPerkCard) {
                ((ChangeFundsPerkCard) perkCard).setFreeParkTile(freeParkTile);
            }
        }
        for(Tile taxTile : myBoardMap.values()){
            if (taxTile instanceof TaxTile){
                ((TaxTile) taxTile).setFreeParkTile(freeParkTile);
            }
        }
    }

    public void saveGame() {
        File toWrite = new File("./data/saveGame.csv");

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(toWrite);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Filename cannot be found", e);
        }

        StringBuilder builder = new StringBuilder();
        builder.append(allPlayers.size()+"\n");

        for(Player p : allPlayers) {

            builder.append(p.getName()+";"+p.getPosition()+";"+p.getBalance()+";"+p.getIsCPU()+"\n");
            if(p.getNumProps() > 0) {
                builder.append(p.getNumProps()+";");
                builder.append(p.saveProperties()+"\n");
            } else {
                builder.append(p.getNumProps()+"\n");
            }
            if(p.getPerkCards().size() > 0) {
                builder.append(p.getPerkCards().size()+";");
                builder.append(p.savePerks()+"\n");
            } else {
                builder.append(p.getPerkCards().size()+"\n");
            }

        }

        writer.print(builder.toString());
        writer.close();
    }

    public void loadGame() {
        Scanner input;
        try {
            input = new Scanner(this.getClass().getClassLoader().getResourceAsStream("saveGame.csv"));
            input.useDelimiter(";|\\n");
        } catch (NullPointerException e){
            throw new IllegalArgumentException("Filename cannot be found", e);
        }

        int numOfPlayers = Integer.parseInt(input.next());
        this.allPlayers.clear();
        for(int i = 0; i < numOfPlayers; i++) {
            String name = input.next();
            int pos = Integer.parseInt(input.next());
            int bal = Integer.parseInt(input.next());
            boolean isCPU = input.nextBoolean();
            Player p = new Player(name, pos, bal, isCPU);
            int propSize = Integer.parseInt(input.next());
            for(int j = 0; j < propSize; j++) {
                p.addToProperties(getPropertyCardFromTitle(input.next()));
            }
            int perkSize = Integer.parseInt(input.next());
            for(int k = 0; k < perkSize; k++) {
                p.addToPerks(getPerkCardFromTitle(input.next()));
            }
            allPlayers.add(p);
        }
        System.out.println(getPlayers());
        for (Player p : getPlayers()) {
            System.out.println(p.getPropertiesMap());
            System.out.println(p.getBalance());
        }
    }

    private PropertyCard getPropertyCardFromTitle(String title) {
        if (myPropertyCardTitles.isEmpty()) {
            myPropertyCardTitles = new ArrayList<>();
            for (PropertyCard card : myPropertyCardList) {
                myPropertyCardTitles.add(card.getTitle());
            }
        }
        int index = myPropertyCardTitles.indexOf(title);
        return myPropertyCardList.get(index);
    }

    private PerkCard getPerkCardFromTitle(String title) {
        if (myPerkCardTitles.isEmpty()) {
            myPerkCardTitles = new ArrayList<>();
            for (PerkCard card : myPerkCardList) {
                myPerkCardTitles.add(card.getText());
            }
        }
        int index = myPerkCardTitles.indexOf(title);
        return myPerkCardList.get(index);
    }

    //================================================================================
    // Action Methods to Change model Game Parameters
    //================================================================================

    public int rollDice() {
        myDice.rollDie();
        diceRolledList = myDice.getRolledDice();
        return diceRolledList.stream().mapToInt(Integer::intValue).sum();
    }

    public void rollDiceAndMovePlayer() { modelActions.rollDiceAndMovePlayer(); }

    public void moveCurrentPlayer(int diceRolled) { modelActions.moveCurrentPlayer(diceRolled); }

    public void buyProperty() { modelActions.buyProperty(); }

    public void sellProperty() { modelActions.sellProperty(); }

    public void buildHouse() { modelActions.buildHouse(); }

    public void sellHouse() { modelActions.sellHouse(); }

    public void buildHotel() { modelActions.buildHotel(); }

    public void sellHotel() { modelActions.sellHotel(); }

    public void useFreeJailCard() { modelActions.useFreeJailCard(); }

    public void giveOutOfJail() {
        modelActions.giveOutOfJail();
    }

    public void endCurrentPlayerTurn(boolean playerRemoval) {
        if (currentPlayerIndex == allPlayers.size() - 1) {
            currentPlayerIndex = 0;
            currentPlayer = allPlayers.get(currentPlayerIndex);
        }
        else {
            if (playerRemoval) {
                currentPlayer = allPlayers.get(currentPlayerIndex + 1);
            }
            else {
                currentPlayerIndex ++;
                currentPlayer = allPlayers.get(currentPlayerIndex);
            }
        }
        updateConsoleAndEventLog(languageFile.getString("itIsNowYourTurnText") + currentPlayer.getName());
    }

    public void performTileAction() {
        //System.out.println(currentPlayer.getPosition());
        Tile newTile = myBoard.getTile(currentPlayer.getPosition());
        newTile.performAction(currentPlayer);
        if (newTile instanceof PerkTile) {
            updateConsoleAndEventLog(languageFile.getString("drawnPerkTileText") + ((PerkTile) newTile).getDrawnCardContent());
        }
    }

    public void passGoAndCollect() {
        int amount = 0;
        for(Map.Entry<Integer, Tile> entry : myBoard.getBoardMap().entrySet()) {
            Tile tile = entry.getValue();
            if(tile instanceof GoTile) {
                tile.performAction(currentPlayer);
                amount = ((GoTile) tile).getAmountBonus();
            }
        }
        updateConsoleAndEventLog(currentPlayer.getName() + languageFile.getString("passedGoAndCollectText") + amount);
    }

    public void changeCurrentPlayerFunds(int amountOfMoney) { currentPlayer.changeBalance(amountOfMoney); }

    public void jailCurrentPlayer() { jailTile.jailTurn(currentPlayer,jailTile); }

    //================================================================================
    // ConsoleMessage method
    //================================================================================

    public void updateConsoleAndEventLog(String message) {
        consoleMessage = message;
        eventLog.addAndPrintEvent(consoleMessage);
    }

    //================================================================================
    // End Game Conditions
    //================================================================================

    private boolean isPlayerBankrupt() { return currentPlayer.getMortageWorth() + currentPlayer.getBalance() <= 0; }

    public void bankruptPlayer() {
        if(isPlayerBankrupt()) {
            consoleMessage = currentPlayer.getName() + languageFile.getString("hasGoneBankrupt");
            eventLog.addAndPrintEvent(consoleMessage);
            forfeit();
        }
    }

    public void forfeit() {
        removePlayer();
        if(allPlayers.size() == 1) {
            consoleMessage = "1" + languageFile.getString("playersRemain");
        }
        else {
            consoleMessage = allPlayers.size() + languageFile.getString("playersRemain");
        }
        eventLog.addAndPrintEvent(consoleMessage);
    }

    public boolean isGameEnd() { return winCond.isGameEnd(allPlayers); }

    public Player whoIsWinner() { return winCond.whoIsWinner(allPlayers); }
    private void removePlayer() {
        if (allPlayers.size() > 1) {
            Player oldPlayer = currentPlayer;
            endCurrentPlayerTurn(true);
            setPlayerPropsUnowned(oldPlayer);
            allPlayers.remove(oldPlayer);
        }
    }

    public void removeAllButYou() {
        allPlayers.clear();
        allPlayers.add(currentPlayer);
    }

    private void setPlayerPropsUnowned(Player player) {
        Map<Integer, List<PropertyCard>> props =  player.getPropertiesMap();
        for (Map.Entry<Integer, List<PropertyCard>> entry : props.entrySet()) {
            for(PropertyCard pc : entry.getValue()) {
                PropertyTile tile = (PropertyTile) findTileOwningCard(pc);
                tile.setCardOwned(false);
            }
        }
    }

    private Tile findTileOwningCard(PropertyCard card) {
        Map<Integer, Tile> map = myBoard.getBoardMap();
        for(Map.Entry<Integer, Tile> entry : map.entrySet()) {
            Tile tile = entry.getValue();
            if(tile instanceof  PropertyTile) {
                if(((PropertyTile) tile).getCard() == card) {
                    return tile;
                }
            }
        }
        throw new IllegalArgumentException("No Tile contains this card!");
    }

    //================================================================================
    // Getters/Setters
    //================================================================================

    public List<Player> getPlayers() { return this.allPlayers; }

    public int getHouseToHotelThreshold() { return houseToHotelThreshold;}

    public Board getBoard() { return this.myBoard; }
    public void setPropList(List<PropertyCard> cards) {
        this.myPropertyCardList = cards;
    }

    public Player getCurrentPlayer() { return currentPlayer; }

    public Dice getDice() { return myDice; }

    public List<Integer> getDiceRolledList() { return diceRolledList; }

    public String getConsoleMessage() { return consoleMessage; }

    public EventLog getEventLog() { return eventLog; }

    public void setWinCondition(WinCondition condition) { winCond = condition; }

    public void setLanguageFile(ResourceBundle languageFile) {
        this.languageFile = languageFile;
    }
    public ResourceBundle getLanguageFile() { return languageFile; }

    public Tile getCurrentPlayerTile() { return myBoard.getTile(currentPlayer.getPosition());}

    public int getBoardMaxIndex() { return myBoard.getMaxIndex(); }

    public void setPlayers(List<Player> players) { this.allPlayers = players; }

    public void setBoard(Board board) { this.myBoard = board; }

    public void setDice(Dice myDice) { this.myDice = myDice; }

    public String getGameType() {
        return gameType;
    }
}