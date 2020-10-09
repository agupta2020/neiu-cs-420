package utilities;

import domain.Media;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

abstract class ABSReader {
    private Path inputPath;

    ABSReader(Path inputPath) {
        this.inputPath = inputPath;
    }

    Path getInputPathOBJ() {
        return this.inputPath;
    }


    abstract List<Media> read(String category) throws IOException;
}

