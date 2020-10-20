import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URISyntaxException;


import models.Media;
import models.MediaCategory;
import org.json.simple.parser.ParseException;
import views.NYTComboBox;




public class NYTMediaApp extends Application {
    private NYTComboBox displayComboBox;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException, URISyntaxException, ParseException {
        displayComboBox = new NYTComboBox();


        BorderPane borderPane = new BorderPane();
        setUpBoarderPane(borderPane);
        Scene scene = new Scene(borderPane, 600, 500);
        stage.setScene(scene);
        stage.setTitle("FX DEMO");
        stage.show();
    }

    private void setUpBoarderPane(BorderPane boarderPane){
        HBox hBox = new HBox();
        seUpHBox(hBox);
        boarderPane.setTop(hBox);
}

    private void seUpHBox(HBox hBox) {
        hBox.setSpacing(10);
        ComboBox<MediaCategory> mediaCategoryComboBox = displayComboBox.getMediaCategoriesComboBox();
        ComboBox<Media> mediaComboBox= displayComboBox.getMediaComboBox();
        Text textBox = displayComboBox.getTextBox();
        hBox.getChildren().addAll(mediaCategoryComboBox,mediaComboBox, textBox);
        HBox.setMargin(mediaCategoryComboBox, new Insets(20,5,5,10));
        HBox.setMargin(mediaComboBox, new Insets(20,5,5,10));
        HBox.setMargin(textBox, new Insets(20,5,5,10));
    }

}
