package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.Duration;
import view.MonopolyView;
import java.util.Random;

public class CPUController {

    private MonopolyView viewer;
    private int DECISION_CONSTANT = 2;
    private double THINKING_TIME_CONSTANT = 2; // changeable speed of CPU decisions (ex. change to 0.1 for fast responses)

    public CPUController(MonopolyView viewer) {
        this.viewer = viewer;
    }

    public void actionSequence() {
        KeyFrame kf1 = new KeyFrame(Duration.seconds(THINKING_TIME_CONSTANT), e -> viewer.getRollDiceButton().fire());
        KeyFrame kf2 = new KeyFrame(Duration.seconds(THINKING_TIME_CONSTANT * 2), e -> sequenceChoices());
        KeyFrame kf3 = new KeyFrame(Duration.seconds(THINKING_TIME_CONSTANT * 3), e -> viewer.getEndTurnButton().fire());
        Timeline timeline = new Timeline(kf1, kf2, kf3);
        Platform.runLater(timeline::play);
    }

    private void sequenceChoices() {
        if (! viewer.getSellPropertyButton().isDisabled()) {
            if (! viewer.getBuildHousesButton().isDisabled()) {
                if (randomNumberGenerator(DECISION_CONSTANT)) {
                    viewer.getBuildHousesButton().fire();
                }
            }
        }
        if (! viewer.getBuyPropertyButton().isDisabled()) {
            if (randomNumberGenerator(DECISION_CONSTANT)) {
                viewer.getBuyPropertyButton().fire();
            }
        }
        if (! viewer.getUseJailFreePerkButton().isDisabled()) {
            viewer.getUseJailFreePerkButton().fire();
        }
    }

    private boolean randomNumberGenerator(int outOfNumber) {
        Random rand = new Random();
        return rand.nextInt(outOfNumber) == 1;
    }

    public void setTHINKING_TIME_CONSTANT(double speed) {
        THINKING_TIME_CONSTANT = speed;
    }
}