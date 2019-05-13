package view.special;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PropertyExchange;
import model.card.propertycard.PropertyCard;
import model.engine.ModelEngine;
import model.player.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TradeHandler extends Handler{
    PropertyCard playerCard;
    PropertyCard otherPlayerCard;

    public TradeHandler(ModelEngine engine) {
        super(engine);
    }

    public void tradeDialog(){
        List<PropertyCard> propertyCardList = new ArrayList<>();
        createPropertyChoices(propertyCardList, modelEngine.getCurrentPlayer());
        ChoiceDialog<PropertyCard> propertyCardChoiceDialog = new ChoiceDialog<>(propertyCardList.get(0),propertyCardList);
        propertyCardChoiceDialog.setTitle("Trade");
        propertyCardChoiceDialog.setHeaderText("Select which property to trade");
        propertyCardChoiceDialog.setContentText("Property Selected:");
        Optional<PropertyCard> result = propertyCardChoiceDialog.showAndWait();

        if(result.isPresent()){
            boolean hasProperty = false;
            for(Player player : modelEngine.getCurrentPlayer().getOtherPlayers()){
                if(player.getPropertiesMap().size() != 0){
                    hasProperty = true;
                }
            }
            if(hasProperty){
                playerCard = result.get();
                tradeOtherPlayerDialog();
            }
            else{
                tradeDenial();
            }
        }
    }

    private void tradeDenial(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Trade Not Available");
        alert.setHeaderText("No other players have tradeable property!");

        Optional<ButtonType> option = alert.showAndWait();
    }


    private void tradeOtherPlayerDialog(){
        List<PropertyCard> propertyCardList = new ArrayList<>();
        List<Player> otherPlayersRaw = modelEngine.getCurrentPlayer().getOtherPlayers();
        List<Player> otherPlayers = new ArrayList<>();
        List<String> otherPlayerNames = new ArrayList<>();
        Button ok = new Button("OK");
        Button cancel = new Button("Cancel");
        Stage stage = new Stage();
        stage.setTitle("Trade");
        FlowPane root = setOptionScene(stage);

        ComboBox comboBox = new ComboBox();
        createOtherPlayersDropDown(otherPlayersRaw, otherPlayers, otherPlayerNames, comboBox);

        ComboBox comboBox2 = new ComboBox();
        createPropertyChoices(propertyCardList, otherPlayers.get(0));
        setPropertyTradeOptions(propertyCardList, comboBox2);
        Player finalOtherPlayerDefault = otherPlayers.get(0);
        ok.setOnAction(e2 -> tradeApproval(modelEngine.getCurrentPlayer(), finalOtherPlayerDefault,playerCard,otherPlayerCard,stage));
        comboBox.setOnAction(e -> {
            propertyCardList.removeAll(propertyCardList);

            String selected = comboBox.getValue().toString();

            Player otherPlayer = new Player("",0,0,true);
            for (Player player : otherPlayers){
                if (player.getName().equals(selected)){
                    otherPlayer = player;
                }
            }
            createPropertyChoices(propertyCardList,otherPlayer);
            setPropertyTradeOptions(propertyCardList, comboBox2);
            Player finalOtherPlayer = otherPlayer;
            ok.setOnAction(e2 -> tradeApproval(modelEngine.getCurrentPlayer(), finalOtherPlayer,playerCard,otherPlayerCard,stage));
        });

        VBox vBox = new VBox();
        vBox.getChildren().add(new Label("Select Opponent to Trade with:"));
        vBox.getChildren().add(comboBox);
        vBox.getChildren().add(new Label("Select Opponent's Property to Trade:"));
        vBox.getChildren().add(comboBox2);
        vBox.getChildren().add(ok);
        vBox.getChildren().add(cancel);
        root.getChildren().add(vBox);
        cancel.setOnAction(e -> stage.close());
    }

    private void setPropertyTradeOptions(List<PropertyCard> propertyCardList, ComboBox comboBox2) {
        ObservableList<PropertyCard> tradePropertiesListDefault = FXCollections.observableArrayList(propertyCardList);
        comboBox2.setItems(tradePropertiesListDefault);
        comboBox2.getSelectionModel().selectFirst();
        otherPlayerCard = (PropertyCard) comboBox2.getValue();
        comboBox2.valueProperty().addListener(e -> {
            otherPlayerCard = (PropertyCard) comboBox2.getValue();
        });
    }

    private void tradeApproval(Player player, Player otherPlayer, PropertyCard playerCard, PropertyCard otherPlayerCard, Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Trade Approval");
        alert.setHeaderText("Does " + otherPlayer.getName() + " approve of this trade?");

        Optional<ButtonType> option = alert.showAndWait();

        if(option.isPresent()){
            PropertyExchange propertyExchange = new PropertyExchange();
            propertyExchange.tradeProperty(player,otherPlayer,playerCard,otherPlayerCard);
            stage.close();
        }
    }
}

