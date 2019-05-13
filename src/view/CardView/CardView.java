package view.CardView;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardView extends ImageView {

    public CardView(Image image){
        this.setImage(image);
        this.setFitWidth(100);
        this.setPreserveRatio(true);
    }

}
