package utilities;

import domain.Media;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;

import static domain.Media.getMediaMap;


public class ReaderFactory {


    public static Map<String, List<Media>> readApiFile(String inputFile) throws IOException, URISyntaxException, ParseException {

        System.out.println("Reading -> " + inputFile + "  API File Now");
        return readMediaFile(new StringReader(PathBuilderUtilities.getInputPathOBJ(inputFile + ".txt")), inputFile);
    }

    private static Map<String, List<Media>> readMediaFile(ABSReader rwOBJ, String inputFile) throws ParseException, IOException, URISyntaxException {
        return getMediaMap(inputFile, rwOBJ.read(inputFile));
    }


}