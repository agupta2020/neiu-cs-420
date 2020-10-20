package utilities;

import models.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class StringReader extends ABSReader {


    private final String SEP = "===>";

    StringReader(Path inputPath) {
        super(inputPath);
    }


    List<Media> read(String category) throws IOException {
        List<Media> mediaList = new ArrayList<>();
        Arrays.asList(Files.readAllLines(getInputPathOBJ(), StandardCharsets.ISO_8859_1)).forEach(fileStr -> fileStr.forEach(str -> {
            String title = str.split(SEP)[0];
            String short_summary = str.split(SEP)[1];
            if (category.equals(MediaCategory.MOVIE.getMediaDetails()))
                mediaList.add(new Movie(title, category, short_summary));
            else if (category.equals(MediaCategory.BOOK.getMediaDetails()))
                mediaList.add(new Book(title, category, short_summary));
            else mediaList.add(new Article(title, category, short_summary));

        }));
        return mediaList;
    }


}
