package view.infopanel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.player.Player;
import java.util.List;
import java.util.ResourceBundle;

public class PlayerInformationBox extends HBox{

    private final ToggleGroup playerGroup = new ToggleGroup();
    private ResourceBundle myResources;
    private List<Player> playerList;
    private List<Color> colorList;

    public PlayerInformationBox(ResourceBundle resourceBundle, List<Player> playerList, List<Color> colorList, int sceneHeight){
        super();
        this.setPadding(new Insets(10,10,10,10));
        myResources = resourceBundle;
        this.playerList = playerList;
        this.colorList = colorList;
        createPlayerToggleBox();
        this.setLayoutY(sceneHeight*0.1);
    }

    private void createPlayerToggleBox(){
        VBox playerButtons = new VBox();
        playerButtons.setAlignment(Pos.CENTER_RIGHT);
        playerButtons.setId("playerName");
        for(Player player: playerList){
            ToggleButton playerTB = createPlayerToggleButton(player);
            playerButtons.getChildren().add(playerTB);
        }
        getChildren().add(playerButtons);
        playerGroup.selectedToggleProperty().addListener((ov, toggle, new_toggle) -> {
            if(getChildren().size()>1){
                getChildren().remove(1);
            }
            if(playerGroup.getSelectedToggle() != null){
                createPlayerStatsTextBox();
            }
        });
    }

    private void createPlayerStatsTextBox() {
        Player selectedPlayer = (Player)playerGroup.getSelectedToggle().getUserData();
        VBox textBox = new VBox(10);
        textBox.setPrefWidth(250);
        textBox.setId("textBoxBorder");
        textBox.getChildren().addAll(createText("balanceText", Integer.toString(selectedPlayer.getBalance())),
                createText("propertiesText", selectedPlayer.propertyPrint()),
                createText("cardsText", selectedPlayer.perkPrint()));
        getChildren().add(textBox);
    }

    private Text createText(String resourceString, String playerStat){
        String stats;
        if(playerStat != null && !playerStat.isEmpty()){
            stats = playerStat;
        }
        else{
            stats = "";
        }
        Text text = new Text(String.format(myResources.getString(resourceString), stats));
        text.setWrappingWidth(250);
        return text;
    }

    private ToggleButton createPlayerToggleButton(Player player){
        ToggleButton tb = new ToggleButton(player.getName());
        tb.setToggleGroup(playerGroup);
        tb.setTextFill(colorList.get(playerList.indexOf(player))); //set the color with the player color
        tb.setUserData(player);
        tb.setId("playerToggleButton");
        return tb;
    }



}
