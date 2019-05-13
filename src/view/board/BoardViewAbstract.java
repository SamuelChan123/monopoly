package view.board;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import model.player.Player;
import model.tile.Tile;
import view.CardView.CCCardView;
import view.CardView.CardView;
import view.CardView.ChanceCardView;
import view.TileView.RectangleRealEstateTileView;
import view.TileView.TileView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * BoardViewAbstract is implemented by SquareBoardView
 */
public abstract class BoardViewAbstract {

    private Pane myRoot;
    private List<Color> playerColorList;
    private List<PlayerChipView> playerChipList;
    private final static String MONOPOLY_MAN_IMAGE_NAME = "man150.png";
    private CardView chanceCardView = new ChanceCardView(new Image("chance.jpg"));
    private CardView cCCardView = new CCCardView(new Image("community-chest.jpg"));
    private DialogBox dialogBox;
    private Map<Integer,Tile> tileMap;
    private Map<Integer, TileView> tileViewMap;
    private List<Player> playerList;
    private ImageView centerImage;
    private ResourceBundle myResources;

    public BoardViewAbstract(String theme, List<Player> playerList, List<Color> colorList, Map<Integer, Tile> tileMap, ResourceBundle resourceBundle) {
        setCenterImage(theme);
        myRoot = new Pane();
        myResources = resourceBundle;
        playerColorList = colorList;
        dialogBox = new DialogBox(MONOPOLY_MAN_IMAGE_NAME, 200, 150);
        this.tileMap = tileMap;
        tileViewMap = new HashMap<>();
        this.playerList = playerList;
    }

    protected abstract void makeBoard();

    protected abstract void addTileViewLayout();

    protected void createPlayerChips(Map<Integer, StackPane> tileViewStackPanes) {
        playerChipList = new ArrayList<>();
        for(int i=0; i< playerList.size(); i++){
            playerChipList.add(new PlayerChipView(playerList.get(i), i, playerColorList.get(i), tileViewStackPanes));
        }
    }

    protected void handle(RectangleRealEstateTileView tv, Pane centerPane){
        tv.setOnMouseEntered(e -> centerPane.getChildren().add(tv.getMyPopUp()));
        tv.setOnMouseExited(e -> centerPane.getChildren().remove(tv.getMyPopUp()));
    }

    public void updatePlayerChips(){
        for(PlayerChipView playerChip: playerChipList){
            playerChip.updatePlayerChip();
        }
    }

    public void updateConsoleMessage(String text){
        dialogBox.editText(text);
    }

    public void updateTileView(){
        for(TileView tv:tileViewMap.values()){
            tv.updateTileView();
        }
    }

    public Map<Integer, TileView> getTileViewMap() {
        return tileViewMap;
    }

    public Map<Integer, Tile> getTileMap() {
        return tileMap;
    }

    protected DialogBox getDialogBox() {
        return dialogBox;
    }

    public Node getMyRoot() {
        return myRoot;
    }

    public void setMyRoot(Pane myRoot) {
        this.myRoot = myRoot;
    }

    protected CardView getcCCardView() {
        return cCCardView;
    }

    protected CardView getChanceCardView() {
        return chanceCardView;
    }

    protected void setCenterImage(String theme) {
        try {
            this.centerImage = new ImageView(new Image(new FileInputStream("data/centerimages/" + theme + "_center_image.jpg")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public abstract void updateCenterImage(String theme);

    protected ImageView getCenterImage(){
        return centerImage;
    }

    protected ResourceBundle getMyResources() {
        return myResources;
    }
}
