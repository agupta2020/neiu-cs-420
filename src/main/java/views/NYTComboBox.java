package views;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import models.Media;
import models.MediaCategory;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            if (media == null) {
                return null;
            } else {
                return media.getTitle();
            }

        }

        @Override
        public Media fromString(String title) {
            for( List<Media> media: mediaMap.values()){
                for(Media justMedia: media){
                    if (justMedia.getTitle().equals(title)) return justMedia;
                }
            }
            return null;
        }
    }

    private HashMap<MediaCategory, List<Media>> callReadApiFile() throws IOException, URISyntaxException, ParseException {
        mediaMap = readApiFile("Top Movies Critics Choice");
        mediaMap.putAll(readApiFile("Top Rated Books"));
        mediaMap.putAll(readApiFile("Most Shared Articles"));
        return (HashMap<MediaCategory, List<Media>>) mediaMap;

    }
    public NYTComboBox() throws IOException, URISyntaxException, ParseException {
        mediaType = observableArrayList(callReadApiFile().keySet());
        textBox = new Text();
        setUpMedia();
        setUpMediaCategories();
    }

    private void setUpMediaCategories() {
        mediaCategoriesComboBox = new ComboBox<>();
        mediaCategoriesComboBox.getItems().addAll(mediaType);

        // sorting categories by their defined position in ENUM Class
        mediaCategoriesComboBox.getItems().sort((o1, o2) -> {
            if (o1.getPosition() < o2.getPosition()) {
                return -1;
            } else if (o1.getPosition() > o2.getPosition()) {
                return 1;
            } else
                return 0;
        });
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
                    setText("-- Select a value --");

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
//                    String displayText = newValue.getCategory() + "\n" + "Item Title" + newValue.getTitle();
                String displayText = "Concise Summary"+newLine+underline+newLine+ newValue.getShort_summary();
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
