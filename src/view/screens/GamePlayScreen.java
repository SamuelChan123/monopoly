package view.screens;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.player.Player;
import model.tile.Tile;
import view.board.BoardViewAbstract;
import view.infopanel.PlayerInformationBox;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import view.infopanel.Buttons;
import view.infopanel.DiceView;

public class GamePlayScreen extends AbstractScreenView {

    private static final String STYLESHEET = "styles.css";
    private List<Color> colorList;
    private BoardViewAbstract boardView;
    private List<Player> playerList;
    private BorderPane myRoot;
    private Map<Integer, Tile> tileMap;
    private String theme;

    public GamePlayScreen(String theme, String type, List<Color> playerColorList, Map<Integer, Tile> tileMap, Map<String, Button> buttonMap, DiceView diceview, List<Player> players, ResourceBundle resourceBundle, int sceneWidth, int sceneHeight) {
        super(resourceBundle);
        this.theme = theme;
        myRoot = new BorderPane();
        colorList = playerColorList;
        Scene scene = new Scene(myRoot, sceneWidth, sceneHeight);
        scene.getStylesheets().add(STYLESHEET);
        setBackground(theme, myRoot);
        setGameType(type);
        setScene(scene);
        setPlayerList(players);
        setTileMap(tileMap);
        setUpBoard(getGameType());
        setUpScene(buttonMap, diceview,sceneHeight);
    }

    private void setUpScene(Map<String, Button> buttonMap, DiceView diceView, int sceneHeight){
        PlayerInformationBox playerInformationBox = new PlayerInformationBox(getMyResources(), playerList, colorList, 10);
        Buttons buttons = new Buttons(buttonMap, sceneHeight);
        myRoot.setPadding(new Insets(10,0,0,0));
        myRoot.setLeft(boardView.getMyRoot());
        Pane p = new Pane();
        p.getChildren().addAll(playerInformationBox, diceView,buttons.getButtonsView(),buttons.getTopBox());
        myRoot.setCenter(p);
    }

    private void setUpBoard(String type){
        try {
            boardView = (BoardViewAbstract) Class.forName(getMyResources().getString(type)).getConstructors()[0].newInstance(this.theme, playerList, colorList, tileMap,getMyResources());
        } catch (ClassNotFoundException e){
            System.out.println("Class matching name found in configuration file does not exist!");
        } catch (IllegalAccessException e){
            System.out.println("Class matching name found in configuration file is not accessible!");
        }  catch (InstantiationException e) {
            System.out.println("Issue with object being made, check to see if parameters are appropriate!");
        }  catch (InvocationTargetException e){
            e.printStackTrace();
            System.out.println("Your underlying method is non-functional!");
        }
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    private void setTileMap(Map<Integer, Tile> tileMap) {
        this.tileMap = tileMap;
    }

    public void update(String consoleText) {
        boardView.updatePlayerChips();
        boardView.updateConsoleMessage(consoleText);
        boardView.updateTileView();
    }

    public void setTheme(String theme){
        this.theme = theme;
        setBackground(theme, myRoot);
        boardView.updateCenterImage(theme);
    }


}
