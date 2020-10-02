package api;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class CreateAPIDataFiles {
    private String fullFilePath;



     public CreateAPIDataFiles (String fileName)  throws URISyntaxException {
         File dir = new File( getPath() );
         if ( !dir.exists() ) {
             dir.mkdirs();
         }
         this.fullFilePath=getPath() + File.separator + fileName;

     }

    public String getFullFilePath() {
        return fullFilePath;
    }

    private static String getPath() throws URISyntaxException {
        String path1 = Path.of("src","main","resources").toAbsolutePath().toString();
        return path1;

    }

}
