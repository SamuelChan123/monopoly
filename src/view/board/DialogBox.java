package view.board;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DialogBox extends BorderPane {

    private StringProperty valueProperty;

    public DialogBox(String imageFile, int dialogWidth, int dialogHeight){
        this.setMaxHeight(dialogHeight);
        this.setMaxWidth(dialogWidth);
        ImageView imageView = new ImageView(new Image (imageFile));
        VBox textVBox = new VBox();
        textVBox.setId("whiteBox");
        textVBox.setPrefSize(dialogWidth, dialogHeight);
        textVBox.setMaxWidth(dialogWidth);

        Text myText = new Text();
        myText.setFont(Font.font ("Monopoly", 20));
        valueProperty = new SimpleStringProperty();
        myText.textProperty().bind(valueProperty);
        myText.setWrappingWidth(dialogWidth);
        textVBox.getChildren().add(myText);
        this.setLeft(imageView);
        this.setRight(textVBox);
    }

    public void editText(String text){
        valueProperty.setValue(text);
    }


}
