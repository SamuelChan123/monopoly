package view.infopanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import java.util.*;

public class Buttons {
    private VBox myVBox;
    private VBox myVBoxTop;
    private static final String STYLESHEET = "styles.css";

    public Buttons(Map<String, Button> buttonMap, int sceneHeight) {
        myVBox = new VBox(8);
        myVBox.setLayoutY(sceneHeight*0.79);
        myVBox.getStylesheets().add(STYLESHEET);
        createDefaultButtons(buttonMap);
        createTopButtons(buttonMap);
    }

    private void createDefaultButtons(Map<String, Button> buttonMap) {
        Button rollButton = buttonMap.get("rollDiceButton");
        Button sellPButton = buttonMap.get("sellPropertyButton");
        Button buyButton = buttonMap.get("buyPropertyButton");
        Button endTurnButton = buttonMap.get("endTurnButton");
        Button forfeitButton = buttonMap.get("forfeitButton");
        Button buildHousesButton = buttonMap.get("buildHouseButton");
        Button sellHousesButton = buttonMap.get("sellHouseButton");
        Button buildHotelButton = buttonMap.get("buildHotel");
        Button sellHotelButton = buttonMap.get("sellHotel");
        Button useJailFreePerkButton = buttonMap.get("jailFreeButton");
        Button auctionTradeButton = buttonMap.get("auctionTradeButton");
        Button changeRuleButton = buttonMap.get("changeRuleButton");

        List<Button> myButtonList1 = new ArrayList<>(Arrays.asList(rollButton, endTurnButton, forfeitButton, useJailFreePerkButton));
        List<Button> myButtonList2 = new ArrayList<>(Arrays.asList(sellPButton, buyButton, buildHousesButton, sellHousesButton));
        List<Button> myButtonList3 = new ArrayList<>(Arrays.asList(sellHotelButton, buildHotelButton, auctionTradeButton, changeRuleButton));
        myVBox.getChildren().addAll(getButtonHBox(myButtonList1), getButtonHBox(myButtonList2), getButtonHBox(myButtonList3));
    }

    private HBox getButtonHBox(List<Button> myButtonList1) {
        HBox box1 = new HBox(6);
        setButtonId(myButtonList1);
        for (Button b : myButtonList1) {
            box1.getChildren().add(b);
        }
        return box1;
    }

    private void setButtonId(List<Button> myButtonList){
        for (Button b : myButtonList) {
            b.setId("smallWhiteButtons");
        }
    }

    private void createTopButtons(Map<String, Button> buttonMap) {
        myVBoxTop = new VBox(5);
        myVBoxTop.setAlignment(Pos.TOP_RIGHT);
        myVBoxTop.setPadding(new Insets(10,10,10,10));
        Button homeButton = buttonMap.get("homeButton");
        Button settingsButton = buttonMap.get("settingsButton");
        Button cheatMoveButton = buttonMap.get("cheatMoveButton");
        Button cheatMoneyButton = buttonMap.get("cheatMoneyButton");
        Button CPUSpeedButton = buttonMap.get("CPUSpeedButton");
        Button eventLogButton = buttonMap.get("eventLogButton");
        List<Button> myButtonListTop = new ArrayList<>(Arrays.asList(homeButton, settingsButton, eventLogButton, cheatMoveButton, cheatMoneyButton, CPUSpeedButton));
        setButtonId(myButtonListTop);
        myVBoxTop.setLayoutX(390);
        myVBoxTop.getChildren().addAll(homeButton, settingsButton, eventLogButton, cheatMoveButton, cheatMoneyButton, CPUSpeedButton);
    }

    public VBox getButtonsView() {
        return myVBox;
    }

    public VBox getTopBox() {return myVBoxTop;}


}
