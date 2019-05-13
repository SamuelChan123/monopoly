package view.board;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.player.Player;
import model.tile.Tile;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class RegularBoardView extends SquareBoardView {

    private final static int TILE_WIDTH = 50;
    private final static int TILE_HEIGHT = 75;

    public RegularBoardView(String theme, List<Player> playerList, List<Color> colorList, Map<Integer, Tile> tileMap, ResourceBundle resourceBundle) {
        super(theme, playerList, colorList, tileMap, TILE_WIDTH, TILE_HEIGHT,resourceBundle);
    }

    @Override
    protected void fillCenterPane(Pane centerPane) {
        centerPane.setPadding(new Insets(50,12.5,25,12.5));
    }

    @Override
    protected void editSpacing() {
        myCenterRow.setPadding(new Insets(0,0,0,TILE_HEIGHT/6.25));
        myCenterRow.setSpacing(TILE_HEIGHT/5.75);
        vleft.setSpacing(TILE_WIDTH-TILE_HEIGHT);
        vright.setSpacing(TILE_WIDTH-TILE_HEIGHT);
    }
}
