package fxexample;

import domain.Media;
import domain.Movie;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;
import utilities.ReaderFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DisplayDataApp extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException, URISyntaxException, ParseException {
        final ComboBox<String> categoriesComboBox = new ComboBox<>();
        final ComboBox<Media> mediaComboBox = new ComboBox<>();
        mediaComboBox.setPromptText("--- Select Movies/Book/Articles ---");
        mediaComboBox.setVisible(false);
        categoriesComboBox.setPromptText("---- Select a category ----");

        Map<String, List<Media>> movieMap = ReaderFactory.readApiFile("Top Movies Critics Choice");
        Map<String, List<Media>> bookMap = ReaderFactory.readApiFile("Top Rated Books");
        Map<String, List<Media>> articleMap = ReaderFactory.readApiFile("Most Shared Articles");

        ObservableList<String> movieType = FXCollections.observableArrayList(movieMap.keySet());
        categoriesComboBox.getItems().addAll(movieType);

        ObservableList<String> bookType = FXCollections.observableArrayList(bookMap.keySet());
        categoriesComboBox.getItems().addAll(bookType);

        ObservableList<String> articleType = FXCollections.observableArrayList(articleMap.keySet());
        categoriesComboBox.getItems().addAll(articleType);


        categoriesComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == "Top Movies Critics Choice") {
                    mediaComboBox.getItems().clear();
                    mediaComboBox.getItems().addAll(movieMap.get(newValue));
                    mediaComboBox.setVisible(true);
                } else if (newValue == "Top Rated Books") {
                    mediaComboBox.getItems().clear();
                    mediaComboBox.getItems().addAll(bookMap.get(newValue));
                    mediaComboBox.setVisible(true);

                } else {
                    mediaComboBox.getItems().clear();
                    mediaComboBox.getItems().addAll(articleMap.get(newValue));
                    mediaComboBox.setVisible(true);
                }
            }
        });
        BorderPane pane = new BorderPane();
        pane.setTop(categoriesComboBox);
        pane.setCenter(mediaComboBox);
        Scene scene = new Scene(pane, 300, 500);
        stage.setScene(scene);
        stage.setTitle("FX DEMO");
        stage.show();


    }
}
