package view.special;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.card.propertycard.PropertyCard;
import model.engine.ModelEngine;
import model.player.Player;

import java.util.List;
import java.util.Map;

public class Handler {
    ModelEngine modelEngine;

    public Handler(ModelEngine engine){
        modelEngine = engine;
    }

    public FlowPane setOptionScene(Stage stage) {
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(5));
        root.setHgap(5);
        Scene scene = new Scene(root,350,300);
        stage.setScene(scene);
        stage.show();
        return root;
    }

    public void createOtherPlayersDropDown(List<Player> otherPlayersRaw, List<Player> otherPlayers, List<String> otherPlayerNames, ComboBox comboBox) {
        for(Player player : otherPlayersRaw){
            if (! otherPlayers.contains(player) && player.getPropertiesMap().size() > 0){
                otherPlayerNames.add(player.getName());
                otherPlayers.add(player);
            }
        }
        ObservableList<String> otherplayerList = FXCollections.observableArrayList(otherPlayerNames);
        comboBox.setItems(otherplayerList);
        comboBox.getSelectionModel().selectFirst();
    }

    public void createPropertyChoices(List<PropertyCard> list, Player player) {
        Map<Integer, List<PropertyCard>> playerpropertymap = player.getPropertiesMap();
        for (Map.Entry<Integer, List<PropertyCard>> entry : playerpropertymap.entrySet()) {
            for (PropertyCard propertyCard : entry.getValue()) {
                list.add(propertyCard);
            }
        }
    }
}
