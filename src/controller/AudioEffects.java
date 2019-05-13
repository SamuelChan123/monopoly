package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import view.MonopolyView;

import java.net.URL;
import java.nio.file.Paths;

public class AudioEffects {

    private MonopolyView viewer;
    private String newturn;
    private String forfeit;
    private String dice;
    private String click;
    private String woo;
    private String endmusic;
    private String gamemusic;
    private AudioClip clickaudio;
    private AudioClip diceaudio;
    private AudioClip newturnaudio;
    private AudioClip forfeitaudio;
    private AudioClip wooaudio;
    private MediaPlayer gameMusicPlayer;
    private MediaPlayer endMusicPlayer;
    private String DEFAULT_RESOURCE_LOCATION = "data/audio/";

    public AudioEffects(MonopolyView viewer) {
        this.viewer = viewer;
        click = DEFAULT_RESOURCE_LOCATION + "click.wav";
        dice = DEFAULT_RESOURCE_LOCATION + "dice.wav";
        newturn = DEFAULT_RESOURCE_LOCATION + "cash.wav";
        forfeit = DEFAULT_RESOURCE_LOCATION + "aww.wav";
        woo = DEFAULT_RESOURCE_LOCATION + "woo.wav";
        endmusic = DEFAULT_RESOURCE_LOCATION + "bankaccount.wav";
        gamemusic = DEFAULT_RESOURCE_LOCATION + "redbonesax.mp3";
        createPlayers();
        setUpSettingSliders();
    }

    private void createPlayers(){
        clickaudio = createAudioClip(click);
        diceaudio = createAudioClip(dice);
        newturnaudio = createAudioClip(newturn);
        forfeitaudio = createAudioClip(forfeit);
        wooaudio = createAudioClip(woo);
        endMusicPlayer = createMediaPlayer(endmusic);
        gameMusicPlayer = createMediaPlayer(gamemusic);
    }

    private AudioClip createAudioClip(String path) {
        AudioClip clip = new AudioClip(Paths.get(path).toUri().toString());
        clip.setVolume(0.5);
        return clip;
    }

    private MediaPlayer createMediaPlayer(String path) {
        MediaPlayer player = new MediaPlayer(new Media(Paths.get(path).toUri().toString()));
        player.setVolume(0.5);
        return player;
    }

    public void playNewTurn() {
        newturnaudio.play();
    }

    public void playForfeit() {
        forfeitaudio.play();
    }

    public void playDice() {
        diceaudio.play();
    }

    public void playClick() {
        clickaudio.play();
    }

    public void playWoo() {
        wooaudio.play();
    }

    public void playEndMusic() {
        endMusicPlayer.setOnEndOfMedia(() -> endMusicPlayer.seek(Duration.ZERO));
        endMusicPlayer.play();
    }

    public void stopEndMusic() {
        endMusicPlayer.stop();
    }

    public void playGameMusic() {
        gameMusicPlayer.setOnEndOfMedia(() -> gameMusicPlayer.seek(Duration.ZERO));
        gameMusicPlayer.play();
    }

    public void stopGameMusic() {
        gameMusicPlayer.stop();
    }

    private void setUpSettingSliders() {
        viewer.getFxVolumeSlider().valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                setFXVolume(newValue.doubleValue());
            }
        });
        viewer.getMusicVolumeSlider().valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                setMusicVolume(newValue.doubleValue());
            }
        });
    }

    private void setMusicVolume(double value) {
        gameMusicPlayer.setVolume(value);
        endMusicPlayer.setVolume(value);
    }

    private void setFXVolume(double value) {
        newturnaudio.setVolume(value);
        forfeitaudio.setVolume(value);
        clickaudio.setVolume(value);
        wooaudio.setVolume(value);
        diceaudio.setVolume(value);
    }
}