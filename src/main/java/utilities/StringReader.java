package utilities;

import domain.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class StringReader extends ABSReader {

    private String lines;

    StringReader(Path inputPath) {
        super(inputPath);
    }


    List<? extends Media> read(String category) throws IOException {
        List mediaList = new ArrayList<>();
        for (String str : Files.readAllLines(getInputPathOBJ())) {
            if (category == MediaCategory.MOVIE.getMediaDetails())
                mediaList.add(new Movie(str, category));
            else if (category == MediaCategory.BOOK.getMediaDetails())
                mediaList.add(new Book(str, category));
            else {
                mediaList.add(new Article(str, category));
            }
        }
        return mediaList;
    }


}
