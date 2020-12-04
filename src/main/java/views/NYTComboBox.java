package views;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import models.Media;
import models.MediaCategory;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javafx.collections.FXCollections.observableArrayList;
import static utilities.ReaderFactory.readApiFile;


public class NYTComboBox {
    private ComboBox<MediaCategory> mediaCategoriesComboBox;
    private ComboBox<Media> mediaComboBox;
    private final Text textBox;
    private static Map<MediaCategory, List<Media>> mediaMap;
    private final ObservableList<MediaCategory> mediaType;
    private final String newLine = "\r\n";
    private final String underline = "----------------------";

    private static class MediaStringConverter extends StringConverter<Media> {
        @Override
        public String toString(Media media) {
            return (media == null)? null : media.getTitle();
        }

        @Override
        public Media fromString(String title) {

            List<Media> listMediaItems = mediaMap.values()
                    .stream()
                    .flatMap(List::stream)
                    .filter(media -> media.getTitle().equals(title))
                    .limit(1)
                    .collect(Collectors.toList());
            return listMediaItems.isEmpty() ? null : listMediaItems.get(0);
        }
    }

    static HashMap<MediaCategory, List<Media>> callReadApiFile() throws IOException, InterruptedException, java.text.ParseException {
        mediaMap = readApiFile("SearchedArticle");
        mediaMap.putAll(readApiFile("BestBooks"));
        mediaMap.putAll(readApiFile("MovieReview"));

        return (HashMap<MediaCategory, List<Media>>) mediaMap;
    }

    public NYTComboBox() throws IOException, InterruptedException, java.text.ParseException {
        mediaType = observableArrayList(callReadApiFile().keySet());
        textBox = new Text();
        setUpMedia();
        setUpMediaCategories();
    }



    private void setUpMediaCategories() {
        mediaCategoriesComboBox = new ComboBox<>();
        mediaCategoriesComboBox.getItems().addAll(mediaType);

        // sorting categories by their defined position in ENUM Class
        mediaCategoriesComboBox.getItems().sort(Comparator.comparingInt(MediaCategory::getPosition));
        mediaCategoriesComboBox.setPromptText("---- Select a MediaCategory ----");

        mediaCategoriesComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            mediaComboBox.setPromptText("--- Select " + newValue.getMediaDetails() + "  ---");
            textBox.setVisible(false);
            textBox.setWrappingWidth(200);
            mediaComboBox.getItems().clear();
            mediaComboBox.getItems().addAll(mediaMap.get(newValue));
            mediaComboBox.setVisible(true);
        });
    }

    private void setUpMedia() {
        mediaComboBox = new ComboBox<>();
        mediaComboBox.setConverter(new MediaStringConverter());
        mediaComboBox.setVisible(false);
        createMediaSelectorListener();
        handleMediaComboBoxUpdate();

    }

    private void handleMediaComboBoxUpdate() {
        mediaComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Media item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("-- Please make a selection --");

                } else {
                    MediaStringConverter mediaStringConverter = new MediaStringConverter();
                    setText(mediaStringConverter.toString(item));
                }
            }
        });
    }

    private void createMediaSelectorListener() {
        mediaComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String displayText = "Concise Summary" + newLine + underline + newLine + newValue.getShort_summary();
                textBox.setText(displayText);
                textBox.setVisible(true);
            }
        });
    }

    public ComboBox<MediaCategory> getMediaCategoriesComboBox() {
        return mediaCategoriesComboBox;
    }

    public ComboBox<Media> getMediaComboBox() {
        return mediaComboBox;
    }

    public Text getTextBox() {
        return textBox;
    }
}
