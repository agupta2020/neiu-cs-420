package utilities;

import models.Media;
import models.MediaCategory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static models.Media.getMediaMap;


public class ReaderFactory {
    private ReaderFactory() {
    }

    public static Map<MediaCategory, List<Media>> readApiFile(String inputFile) throws IOException, InterruptedException, java.text.ParseException {
        MediaCategory inputCategory = null;
        for (MediaCategory mediaType : MediaCategory.values()) {
            if (inputFile.equals(mediaType.getMediaDetails())) inputCategory = mediaType;
        }
        return readMediaFile(new StringReader(PathBuilderUtilities.getInputPathOBJ(inputFile + ".txt")), inputCategory);
    }

    private static Map<MediaCategory, List<Media>> readMediaFile(ABSReader rwOBJ, MediaCategory inputCategory) throws IOException, java.text.ParseException {

        if (inputCategory.getMediaDetails().equals("SearchedArticle")) {
            return getMediaMap(inputCategory, rwOBJ.readArticles(inputCategory.getMediaDetails()));
        } else if (inputCategory.getMediaDetails().equals( "BestBooks")) {
            return getMediaMap(inputCategory, rwOBJ.readBooks(inputCategory.getMediaDetails()));
        } else {
            return getMediaMap(inputCategory, rwOBJ.readMovies(inputCategory.getMediaDetails()));
        }

    }

}