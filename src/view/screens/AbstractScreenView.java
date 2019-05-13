package view.screens;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import view.Video.VideoEffects;
import java.util.ResourceBundle;

/**
 * AbstractScreenView is extended by the scene classes of the program (i.e. GamePlayScreen, PlayerSettingScreen);
 * has an abstract void update(String text) method header for each subclass to implement their own update conditions;
 * Helps organize all of the scene classes into a data structure for facilitated indexing.
 */
public abstract class AbstractScreenView {

    private final static String REGULAR_BACKGROUND_IMAGE = "light-wooden.jpg";
    private ResourceBundle myResources;
    private VideoEffects videoEffects;
    private Scene myScene;
    private int numPlayers;
    private String gameType;
    private String theme;

    public AbstractScreenView(ResourceBundle resources){
        myResources = resources;
        videoEffects = new VideoEffects();
    }

    /**
     * Updates the elements of the class; primarily used by MonopolyView to update game
     * objects in GamePlayScreen.
     *
     * @param text : console text to be updated
     */
    public abstract void update(String text);

    protected void setBackground(String imageFileName, Pane root){
        if(imageFileName.equals(myResources.getString("space")) || imageFileName.equals(myResources.getString("nature"))){
            setVideoAsBackground(imageFileName, root);
        } else{
            ImageView startImage;
            if(imageFileName.equals(myResources.getString("regular"))){
                startImage = new ImageView(REGULAR_BACKGROUND_IMAGE);
            } else{
                startImage = new ImageView(imageFileName);
            }
            startImage.fitWidthProperty().bind(root.widthProperty());
            startImage.fitHeightProperty().bind(root.heightProperty());
            addNodeToFirstIndex(root, startImage);
        }
    }

    private void setVideoAsBackground(String imageFileName, Pane root) {
        MediaView mv;
        if(imageFileName.equals(myResources.getString("nature"))){
            mv = videoEffects.getNatureVideo();
        }
        else{
            mv = videoEffects.getSpaceVideo();
        }
        mv.setPreserveRatio(false);
        addNodeToFirstIndex(root, mv);
        MediaView finalMv = mv;
        mv.getParent().layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            finalMv.setFitHeight(newValue.getHeight());
            finalMv.setFitWidth(newValue.getWidth());
        });
    }

    private void addNodeToFirstIndex(Pane root, Node startImage) {
        if(root != null && root.getChildren().size()>1){
            root.getChildren().set(0, startImage);
        } else{
            root.getChildren().add(startImage);
        }
    }

    public ResourceBundle getMyResources() {
        return myResources;
    }

    public Scene getScene() {
        return myScene;
    }

    public void setScene(Scene scene) {
        this.myScene = scene;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }



}
