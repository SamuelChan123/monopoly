package view.special;

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

public class AuctionHandler extends Handler {
    PropertyCard auctionCard;
    Player otherPlayer;
    int currentBid = 0;

    public AuctionHandler(ModelEngine engine) {
        super(engine);
    }

    public void auctionDialog() {
        List<PropertyCard> propertyCardList = new ArrayList<>();
        createPropertyChoices(propertyCardList, modelEngine.getCurrentPlayer());
        ChoiceDialog<PropertyCard> propertyCardChoiceDialog = new ChoiceDialog<>(propertyCardList.get(0),propertyCardList);
        propertyCardChoiceDialog.setTitle("Auction");
        propertyCardChoiceDialog.setHeaderText("Select which property to auction");
        propertyCardChoiceDialog.setContentText("Property Selected:");
        Optional<PropertyCard> result = propertyCardChoiceDialog.showAndWait();

        if(result.isPresent()){
            auctionCard = result.get();
            startingBidDialog();
        }
    }

    private void startingBidDialog(){
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Starting Bid");
        textInputDialog.setHeaderText("Please enter your starting bid");
        textInputDialog.setContentText("Starting Bid:");
        Optional<String> bid = textInputDialog.showAndWait();
        try{
            int startingbidamount = Integer.parseInt(bid.get());
            if(startingbidamount >= currentBid && bidTooHighForAllPlayers(startingbidamount)){
                currentBid = startingbidamount;
            }
            else if(startingbidamount < currentBid){
                invalidStartingBidDialogue("low");
            }
            else{
                invalidStartingBidDialogue("high");
            }
            bidDialog();
        }catch (NumberFormatException e){
            System.out.println("You must input an integer for your starting bid!");
        }
    }

    private boolean bidTooHighForAllPlayers(int bidamount){
        boolean canAfford = false;
        for(Player otherPlayer : modelEngine.getCurrentPlayer().getOtherPlayers()){
            if (otherPlayer.getBalance() > bidamount){
                canAfford = true;
            }
        }
        return canAfford;
    }

    private void bidDialog(){
        List<Player> otherPlayersRaw = modelEngine.getCurrentPlayer().getOtherPlayers();
        List<Player> otherPlayers = new ArrayList<>();
        List<String> otherPlayerNames = new ArrayList<>();
        Button complete = new Button("COMPLETE AUCTION");
        Button exit = new Button("EXIT AUCTION");
        Stage stage = new Stage();
        stage.setTitle("Auction");
        FlowPane root = setOptionScene(stage);

        ComboBox comboBox = new ComboBox();
        createOtherPlayersDropDown(otherPlayersRaw, otherPlayers, otherPlayerNames, comboBox);

        otherPlayer = otherPlayers.get(0);
        comboBox.setOnAction(e -> {
            for (Player player : otherPlayers){
                if (player.getName().equals(comboBox.getValue().toString())){
                    otherPlayer = player;
                }
            }
        });

        TextField textField = new TextField();
        textField.setOnAction(e -> {
            String bidString = textField.getText();
            try{
                determineBidOutcome(stage, bidString);
            }catch(NumberFormatException e3){
                System.out.println("You must input an integer for your starting bid!");
            }
        });

        VBox vBox = new VBox();
        vBox.getChildren().add(new Label("Who wants to bid on this property?"));
        vBox.getChildren().add(comboBox);
        vBox.getChildren().add(new Label("Current Bid Value: " + currentBid));
        vBox.getChildren().add(new Label("Bid Amount (Press Enter When Done):"));
        vBox.getChildren().add(textField);
        vBox.getChildren().add(complete);
        vBox.getChildren().add(exit);
        root.getChildren().add(vBox);
        complete.setOnAction(e -> auctionApproval(modelEngine.getCurrentPlayer(),otherPlayer,auctionCard,currentBid,stage));
        exit.setOnAction(e -> stage.close());
    }

    private void determineBidOutcome(Stage stage, String bidString) {
        int opponentBid = Integer.parseInt(bidString);
        if(opponentBid > currentBid && opponentBid < otherPlayer.getBalance()){
            currentBid = opponentBid;
            bidUpdate(stage);
        }
        else if(opponentBid <= currentBid){
            invalidBidDialogue("low",stage);
        }
        else{
            invalidBidDialogue("high",stage);
        }
    }

    private void bidUpdate(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Successful Bid");
        alert.setContentText(otherPlayer.getName() + " is winning the auction at a bid of $" + currentBid);
        Optional<ButtonType> option = alert.showAndWait();
        if(option.isPresent()){
            bidDialog();
            stage.close();
        }
    }

    private void invalidStartingBidDialogue(String issue){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Invalid Starting Bid");
        if (issue.equals("low")){
            alert.setContentText("Your bid was negative, please big higher!");
        }
        else{
            alert.setContentText("Your bid is more than any other players can afford, please bid lower!");
        }
        Optional<ButtonType> option = alert.showAndWait();
        if(option.isPresent()){
            startingBidDialog();
        }
    }

    private void invalidBidDialogue(String issue, Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Invalid Bid");
        if (issue.equals("low")){
            alert.setContentText("Your bid was lower than the current bid, please big higher!");
        }
        else{
            alert.setContentText("Your bid is more than you can afford, please bid lower!");
        }
        Optional<ButtonType> option = alert.showAndWait();
        if(option.isPresent()){
            bidDialog();
            stage.close();
        }
    }

    private void auctionApproval(Player player, Player otherPlayer, PropertyCard playerCard, int bid, Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Auction Approval");
        alert.setHeaderText("Does " + modelEngine.getCurrentPlayer().getName() +" approve of " + otherPlayer.getName() + "'s bid?");
        alert.setContentText("Bid Value: " + currentBid);

        Optional<ButtonType> option = alert.showAndWait();

        if(option.isPresent()){
            PropertyExchange propertyExchange = new PropertyExchange();
            propertyExchange.giveProperty(player,otherPlayer,playerCard,bid);
            stage.close();
        }
    }
}
