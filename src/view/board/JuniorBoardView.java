package view.board;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.player.Player;
import model.tile.Tile;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class JuniorBoardView extends SquareBoardView {

    private final static int TILE_WIDTH = 80;
    private final static int TILE_HEIGHT = 80;

    public JuniorBoardView(String theme, List<Player> playerList, List<Color> colorList, Map<Integer, Tile> tileMap, ResourceBundle resourceBundle) {
        super(theme, playerList, colorList, tileMap, TILE_WIDTH, TILE_HEIGHT,resourceBundle);
    }

    @Override
    protected void fillCenterPane(Pane centerPane) {
        centerPane.setPadding(new Insets(25,12.5,25,12.5));
    }

    @Override
    protected void editSpacing() {
        myCenterRow.setPadding(new Insets(TILE_WIDTH/8,0,TILE_WIDTH/8,0));
    }
}
