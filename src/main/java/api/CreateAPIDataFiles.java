package api;

import java.io.File;
import java.net.URISyntaxException;

public class CreateAPIDataFiles {
    private String fullFilePath;



     public CreateAPIDataFiles (String fileName)  throws URISyntaxException {
         File dir = new File( getPath() );
         if ( !dir.exists() ) {
             dir.mkdirs();
         }
         this.fullFilePath=getPath() + fileName;

     }

    public String getFullFilePath() {
        return fullFilePath;
    }

    private static String getPath() throws URISyntaxException {

        String path = ClassLoader.getSystemClassLoader().getResource("").toURI().getPath();
        path = path.substring( 0, path.indexOf("classes") );
        path += "resources" + File.separator + "nytdatafiles" + File.separator;
        return path;
    }

}
