package fxexample;

import domain.Media;
import domain.MediaCategory;
import domain.MediaCategoryComparator;
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
import java.util.List;
import java.util.Map;

public class NYTMediaApp extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException, URISyntaxException, ParseException {
        final ComboBox<MediaCategory> categoriesComboBox = new ComboBox<>();
        final ComboBox<Media> mediaComboBox = new ComboBox<>();

        mediaComboBox.setVisible(false);
        categoriesComboBox.setPromptText("---- Select a MediaCategory ----");

        Map<MediaCategory, List<Media>> movieMap = ReaderFactory.readApiFile("Top Movies Critics Choice");
        Map<MediaCategory, List<Media>> bookMap = ReaderFactory.readApiFile("Top Rated Books");
        Map<MediaCategory, List<Media>> articleMap = ReaderFactory.readApiFile("Most Shared Articles");

        ObservableList<MediaCategory> movieType = FXCollections.observableArrayList(movieMap.keySet());
        categoriesComboBox.getItems().addAll(movieType);

        ObservableList<MediaCategory> bookType = FXCollections.observableArrayList(bookMap.keySet());
        categoriesComboBox.getItems().addAll(bookType);

        ObservableList<MediaCategory> articleType = FXCollections.observableArrayList(articleMap.keySet());
        categoriesComboBox.getItems().addAll(articleType);
        // sorting categories by their defined position in ENUM Class
        categoriesComboBox.getItems().sort(new MediaCategoryComparator());

        categoriesComboBox.valueProperty().addListener(new ChangeListener<MediaCategory>() {
            @Override
            public void changed(ObservableValue<? extends MediaCategory> observable, MediaCategory oldValue, MediaCategory newValue) {
                mediaComboBox.setPromptText("--- Select " + newValue.getMediaDetails() + "  ---");

                if (newValue.getMediaDetails().equals(MediaCategory.MOVIE.getMediaDetails())) {

                    mediaComboBox.getItems().clear();
                    mediaComboBox.getItems().addAll(movieMap.get(newValue));
                    mediaComboBox.setVisible(true);
                } else if (newValue.getMediaDetails().equals(MediaCategory.BOOK.getMediaDetails())) {

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
