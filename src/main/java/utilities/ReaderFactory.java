package utilities;

import models.Media;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import models.MediaCategory;
import org.json.simple.parser.ParseException;

import static models.Media.getMediaMap;


public class ReaderFactory {
    private ReaderFactory() { }

    public static Map<MediaCategory, List<Media>> readApiFile(String inputFile) throws IOException, URISyntaxException, ParseException {

        System.out.println("Reading -> " + inputFile + "  API File Now");
        MediaCategory inputCategory = null;
        for (MediaCategory mediaType : MediaCategory.values()) {
            if (inputFile == mediaType.getMediaDetails()) inputCategory = mediaType;
        }
        return readMediaFile(new StringReader(PathBuilderUtilities.getInputPathOBJ(inputFile + ".txt")), inputCategory);
    }

    private static Map<MediaCategory, List<Media>> readMediaFile(ABSReader rwOBJ, MediaCategory inputCategory) throws ParseException, IOException, URISyntaxException {
        return getMediaMap(inputCategory, rwOBJ.read(inputCategory.getMediaDetails()));
    }

}