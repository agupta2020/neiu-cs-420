package api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateAPIDataFiles {
    private final String fullFilePath;

    public CreateAPIDataFiles(String fileName) throws IOException {

        File dir = new File(getPath());
        if (!dir.exists()) dir.mkdirs();
        this.fullFilePath = getPath() + File.separator + fileName;
        // creating the file
        Files.createFile(Paths.get(this.fullFilePath));
    }

    public String getFullFilePath() {
        return fullFilePath;
    }

    private static String getPath() {
        return Path.of("build", "resources", "main").toAbsolutePath().toString();

    }

}
