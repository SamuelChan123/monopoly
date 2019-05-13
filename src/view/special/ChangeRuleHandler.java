package view.special;

import javafx.scene.control.*;
import model.engine.ModelEngine;
import model.player.Player;
import model.win.WinMillionaire;
import model.win.WinStandard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ChangeRuleHandler extends Handler{
    int voteCount = 0;
    int playerCount = modelEngine.getPlayers().size();
    Player player;
    String rule;
    List<String> playerNames = new ArrayList<>();
    List<String> classNamesFull = new ArrayList<>();
    List<String> classNames = new ArrayList<>();

    public ChangeRuleHandler(ModelEngine engine){ super(engine); }

    public void ruleDialog() {
        File file = new File("data/gamefiles/Rules.csv");
        try {
            Scanner ruleScan = new Scanner(file);
            while (ruleScan.hasNext()) {
                Class winClass = Class.forName(ruleScan.next());
                classNamesFull.add(winClass.getName());
                classNames.add(winClass.getName().split("Win")[1]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Invalid file!");
        } catch (ClassNotFoundException e) {
            System.out.println("Class does not exist!");
        }
        ChoiceDialog ruleChoiceDialog = new ChoiceDialog(classNames.get(0),classNames);
        ruleChoiceDialog.setTitle("Rule Change");
        ruleChoiceDialog.setHeaderText("Select Which Rule To Attempt To Change To");
        ruleChoiceDialog.setContentText("Rule Selected");
        Optional<String> result = ruleChoiceDialog.showAndWait();
        if (result.isPresent()) {
            rule = result.get();
            voteDialog();
        }
    }

    private void voteDialog() {
        if (voteCount == 0){
            for (Player player : modelEngine.getPlayers()) {
                playerNames.add(player.getName());
            }
        }
        ChoiceDialog playerChoiceDialog = new ChoiceDialog(playerNames.get(0),playerNames);
        playerChoiceDialog.setTitle("Vote on Rule");
        playerChoiceDialog.setHeaderText("Select which player to vote in favor of the rule " + rule);
        playerChoiceDialog.setContentText("Current vote count: " + voteCount);
        Optional<String> result = playerChoiceDialog.showAndWait();
        if(result.isPresent()){
            for(Player aplayer : modelEngine.getPlayers()){
                if((result.get().equals(aplayer.getName()))){
                    player = aplayer;
                }
            }
            playerNames.remove(player.getName());
            setVoteCount(player,rule);
        }
    }

    private void setVoteCount(Player player, String rule){
        voteCount++;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Vote");
        alert.setContentText(player.getName() + " has voted for " + rule + ". The new vote count is " + voteCount);
        Optional<ButtonType> option = alert.showAndWait();
        if (option.isPresent() && voteCount <= ((double) playerCount/2)) {
            voteDialog();
        }
        else if (option.isPresent() && voteCount > ((double) playerCount/2)) {
            ruleChangeDialog(rule);
        }
    }

    private void ruleChangeDialog(String rule) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Rules Changed!");
        alert.setContentText("Because a majority of " + voteCount + " votes has been reached, the game rules have now changed to " + rule);
        Optional<ButtonType> option = alert.showAndWait();
        try{
            if(rule.equals("Standard")){
                modelEngine.setWinCondition(new WinStandard());
            }
            else if(rule.equals("Millionaire")){
                modelEngine.setWinCondition(new WinMillionaire());
            }
            System.out.println("Win condition changed to " + rule);
        }
        catch (Exception e){
            System.out.println("Win condition class not found, error in naming or classpath!");
        }
    }
}
