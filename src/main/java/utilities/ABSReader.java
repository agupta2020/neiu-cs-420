package utilities;

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


    abstract List read(String category) throws IOException;

}

