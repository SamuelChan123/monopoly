package view.Video;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.nio.file.Paths;

public class VideoEffects {

    private String DEFAULT_RESOURCE_LOCATION = "data/videos/";
    private String SLOW_STARS = DEFAULT_RESOURCE_LOCATION + "slow_stars.mp4";
    private String WAVES = DEFAULT_RESOURCE_LOCATION + "drone_waves_2.mp4";
    private MediaPlayer slowStarPlayer;
    private MediaPlayer wavesPlayer;
    private MediaView backgroundMediaView;


    public VideoEffects() {
        backgroundMediaView = new MediaView();
        slowStarPlayer = createMediaPlayer(SLOW_STARS);
        wavesPlayer = createMediaPlayer(WAVES);
    }

    public MediaView getSpaceVideo() {
        slowStarPlayer.play();
        backgroundMediaView.setMediaPlayer(slowStarPlayer);
        return backgroundMediaView;
    }

    public MediaView getNatureVideo(){
        wavesPlayer.play();
        backgroundMediaView.setMediaPlayer(wavesPlayer);
        return backgroundMediaView;
    }

    private MediaPlayer createMediaPlayer(String videoName){
        MediaPlayer player = new MediaPlayer(new Media(Paths.get(videoName).toUri().toString()));
        player.setOnEndOfMedia(() -> player.seek(Duration.ZERO));
        return player;
    }

}