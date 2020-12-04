import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Media;
import models.MediaCategory;
import views.NYTComboBox;
import views.NYTRadioButtons;
import views.SplashScreenLoader;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;




public class NYTMediaApp extends Application {
    private NYTComboBox displayComboBox;
    private static final String preMsg="Please wait!  there is an error. \r\n ==>";
    private static final String postMsg=" <==\r\n Kindly correct the issue and re-run the application";
    private static final String preloadingMsg="This application may take 5 minutes to pull complete data files from NYT. " +
            "Please wait!!!";

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        try{

            System.out.println(preloadingMsg);
            BorderPane borderPane = new BorderPane();
            Scene scene = new Scene(borderPane, 800, 700);
            stage.setScene(scene);
            stage.setTitle("FX DEMO");
            displayComboBox = new NYTComboBox();
            setUpBoarderPane(borderPane);
            stage.show();
            secondScene();
        }
        catch(Exception e){
            e.printStackTrace();
            errorScene(e.getMessage());
        }
    }



    private void errorScene(String exceptionMsg) {
        BorderPane errBorderPane = new BorderPane();
        HBox errorHBox = new HBox();
        Text t = getText(exceptionMsg);
        errorHBox.getChildren().add(t);
        errBorderPane.setCenter(errorHBox);
        Scene errScene = new Scene(errBorderPane, 600, 300);
        Stage errorStage = new Stage();
        errorStage.setScene(errScene);
        errorStage.setTitle("Error in FX Demo");
        errorStage.show();
    }

    private Text getText(String exceptionMsg) {
        Text t = new Text (10, 20, preMsg + exceptionMsg + postMsg);
        t.setFont(Font.font ("Verdana", 20));
        t.setFill(Color.RED);
        return t;
    }

    private void secondScene() throws InterruptedException, ParseException, IOException {

        Scene scene1 = new Scene(new NYTRadioButtons().getRadioBorderPane(), 800, 600);
        Stage secondStage = new Stage();
        secondStage.setScene(scene1);
        secondStage.setTitle("Aggregation Analysis");
        secondStage.show();

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
