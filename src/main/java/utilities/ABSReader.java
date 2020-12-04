package utilities;

import models.Media;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.List;

abstract class ABSReader {
    private final Path inputPath;


    ABSReader(Path inputPath) {
        this.inputPath = inputPath;
    }

    Path getInputPathOBJ() {
        return this.inputPath;
    }

    abstract List<Media> readMovies(String category) throws IOException, ParseException;
    abstract List<Media> readBooks(String category) throws IOException, ParseException;
    abstract List<Media> readArticles(String category) throws IOException, ParseException;
}

