package view.board;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.player.Player;
import model.tile.PerkTile;
import model.tile.RealEstateTile;
import model.tile.Tile;
import view.TileView.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class SquareBoardView extends BoardViewAbstract {

    private static final int OFFSET = 2;
    private static final int HEIGHT_FACTOR = 4;
    private static final int WIDTH_FACTOR = 5;
    private static final int TEXT_SIZE = 6;
    private static final int ROTATION1 = 0;
    private static final int ROTATION2 = 45;
    private static final int ROTATION3 = 90;
    private static final int ROTATION4 = 135;


    protected GridPane myGrid;
    protected HBox myCenterRow;
    private int myEachRow;
    private int myTextSize;
    private StackPane myCenterPane;
    private Map<Integer, StackPane> tileViewStackPanes;
    private HBox hbottom;
    private HBox htop;
    protected VBox vleft;
    protected VBox vright;
    private int tileWidth;
    private int tileHeight;

    public SquareBoardView(String theme, List<Player> playerList, List<Color> colorList, Map<Integer, Tile> tileMap, int tileWidth, int tileHeight, ResourceBundle resourceBundle) {
        super(theme, playerList,colorList,tileMap,resourceBundle);
        myGrid = new GridPane();
        myCenterRow = new HBox();
        myCenterPane = new StackPane();
        tileViewStackPanes = new HashMap<>();
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        addTileViewLayout();
        fixCenterPaneDimensions(myCenterPane,tileWidth,tileHeight);
        fillCenterPane(myCenterPane);
        makeBoard();
        createPlayerChips(this.tileViewStackPanes);
        editSpacing();
        setMyRoot(myGrid);
    }

    private void fixCenterPaneDimensions(StackPane centerPane, int tileWidth, int tileHeight) {
        centerPane.setMinWidth(myEachRow*(tileWidth+OFFSET));
        centerPane.setMinHeight(myEachRow*(tileWidth+OFFSET));
        centerPane.setMaxWidth(myEachRow*(tileWidth+OFFSET));
        centerPane.setMaxHeight(myEachRow*(tileWidth+OFFSET));
        getChanceCardView().setFitHeight((myEachRow*tileHeight)/HEIGHT_FACTOR);
        getChanceCardView().setFitWidth((myEachRow*tileWidth)/WIDTH_FACTOR);
        getcCCardView().setFitHeight((myEachRow*tileHeight)/HEIGHT_FACTOR);
        getcCCardView().setFitWidth((myEachRow*tileWidth)/WIDTH_FACTOR);
        StackPane.setAlignment(getChanceCardView(), Pos.BOTTOM_LEFT);
        StackPane.setAlignment(getcCCardView(),Pos.TOP_RIGHT);
        getCenterImage().fitWidthProperty().bind(centerPane.widthProperty());
        getCenterImage().fitHeightProperty().bind(centerPane.heightProperty());
        centerPane.getChildren().addAll(getCenterImage(),getDialogBox(), getChanceCardView(), getcCCardView());
    }

    @Override
    protected void addTileViewLayout() {
        int eachLoop = getTileMap().size() / 4;
        myEachRow = eachLoop - 1;
        myTextSize = TEXT_SIZE;
        int bottomRightCornerIndex = 0;
        int bottomLeftCornerIndex = eachLoop;
        int topLeftCornerIndex = bottomLeftCornerIndex + eachLoop;
        int topRightCornerIndex = topLeftCornerIndex + eachLoop;
        hbottom = new HBox(0);
        makeCorner(bottomLeftCornerIndex, hbottom,ROTATION2);
        for (int i = eachLoop-1; i > 0; i--) {
            makeTileView(i,hbottom,0);
        }
        makeCorner(bottomRightCornerIndex, hbottom,-ROTATION2);

        vleft = new VBox(0);
        for (int i = 2*eachLoop-1; i > eachLoop; i--) {
            makeTileView(i,vleft,ROTATION3);
        }

        htop = new HBox(0);
        makeCorner(topLeftCornerIndex, htop,ROTATION4);
        for (int i = 2*eachLoop+1; i < 3*eachLoop; i++) {
            makeTileView(i,htop,ROTATION3+ROTATION3);
        }
        makeCorner(topRightCornerIndex,htop,-ROTATION4);
        vright = new VBox(0);
        for (int i = 3*eachLoop+1; i < 4*eachLoop; i++) {
            makeTileView(i,vright,-ROTATION3);
        }
    }

    @Override
    protected void makeBoard() {
        editSpacing();
        myGrid.setPadding(new Insets(0,10,0,10));
        myCenterRow.getChildren().addAll(vleft,myCenterPane,vright);
        myGrid.setVgap(-tileHeight/6.25);
        myGrid.add(myCenterRow,0,1);
        myGrid.add(htop,0,0);
        myGrid.add(hbottom,0,2);
        myGrid.getChildren().get(0).setId("monopolyGreen");
    }

    private void makeCorner(int index, Pane pane, int rotate){
        TileView corner = new RectangleCornerTileView(tileHeight,tileHeight,getTileMap().get(index),rotate,myTextSize);
        getTileViewMap().put(index, corner);
        myGrid.setHgap(25);
        StackPane stackPane =new StackPane();
        stackPane.getChildren().add(corner);
        tileViewStackPanes.put(index, stackPane);
        pane.getChildren().add(stackPane);
    }

    private void makeTileView(int index, Pane pane, int angle) {
        TileView tv;
        StackPane stackPane = new StackPane();
        Tile tile = getTileMap().get(index);
        int propertyGroup = tile.getPropertyGroup();
        if(tile instanceof RealEstateTile) {
            tv = new RectangleRealEstateTileView(tileWidth, tileHeight, propertyGroup,(RealEstateTile) tile, myTextSize,getMyResources());
            handle((RectangleRealEstateTileView) tv, myCenterPane);
        } else if(tile instanceof PerkTile){
            tv = new RectanglePerkTileView(tileWidth,tileHeight,(PerkTile) tile, myTextSize);
        } else {
            tv = new RectangleUtilityTileView(tileWidth, tileHeight, getTileMap().get(index), myTextSize);
        }
        stackPane.getChildren().add(tv);
        stackPane.setRotate(angle);
        getTileViewMap().put(index, tv);
        tileViewStackPanes.put(index, stackPane);
        pane.getChildren().add(stackPane);
    }

    public void updateCenterImage(String theme){
        if(myCenterPane!=null && myCenterPane.getChildren().size()>2){
            setCenterImage(theme);
            getCenterImage().fitWidthProperty().bind(myCenterPane.widthProperty());
            getCenterImage().fitHeightProperty().bind(myCenterPane.heightProperty());
            myCenterPane.getChildren().set(0, getCenterImage());
        }
    }

    protected abstract void fillCenterPane(Pane centerPane);

    protected abstract void editSpacing();

}
