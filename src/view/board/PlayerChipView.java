package view.board;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.player.Player;
import java.util.Map;

public class PlayerChipView extends Circle {

    private final static int CHIP_SIZE = 10;
    private int playerNum;
    private Player player;
    private Map<Integer, StackPane> tileViewStackPanes;
    private Pos[] posArray;


    public PlayerChipView(Player player, int playerNum, Color color, Map<Integer,StackPane> tileViewStackPanes) {
        super(CHIP_SIZE, color);
        this.setStroke(Color.BLACK);
        this.player = player;
        this.playerNum = playerNum;
        this.tileViewStackPanes = tileViewStackPanes;
        posArray = new Pos[]{Pos.CENTER, Pos.CENTER_RIGHT, Pos.CENTER_LEFT,
                Pos.TOP_CENTER, Pos.BOTTOM_CENTER, Pos.TOP_LEFT,
                Pos.TOP_RIGHT, Pos.BOTTOM_RIGHT, Pos.BOTTOM_LEFT};
        updatePlayerChip();
    }

    public void updatePlayerChip(){
        int tileIndex = player.getPosition();
        int jailIndex = tileViewStackPanes.size()/4;
        StackPane tilePane = tileViewStackPanes.get(tileIndex);
        if(!player.getInJail() && playerNum==0 && tileIndex==jailIndex){
            if(tilePane != null && !tilePane.getChildren().contains(this)){
                tilePane.setAlignment(this, Pos.BOTTOM_LEFT);
                tilePane.getChildren().add(this);
            }
        }else if(player.getInJail()) {
            if(tilePane != null && !tilePane.getChildren().contains(this)){
                tilePane.setAlignment(this, Pos.CENTER);
                tilePane.getChildren().add(this);
            }
        }else if(tilePane != null && !tilePane.getChildren().contains(this)){
                tilePane.setAlignment(this, posArray[playerNum % 9]);
                tilePane.getChildren().add(this);
        }
    }
}
