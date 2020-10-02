package utilities;

import api.WriteOutToFile;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathBuilderUtilities {
    static String prePath="src/main/resources";
    //summary: "Most Shared Articles"
    private static  String mostSharedArticle = "https://api.nytimes.com/svc/mostpopular/v2/emailed/30.json";
    //summary: "Best Sellers List by Date"
    private static String bestSellerByDate = "https://api.nytimes.com/svc/books/v3/lists/2020-09-01/hardcover-fiction.json";
    //summary: "Top Movies By Critics Choice"
    private static String topMoviesCriticChoice = "https://api.nytimes.com/svc/movies/v2/reviews/picks.json";

    static Path getInputPathOBJ(String inputFileName) throws IOException, NullPointerException, ParseException, URISyntaxException {
            Path p1 = Paths.get(prePath, inputFileName);
            if(! Files.isRegularFile(p1)) {
                if(inputFileName.equals("Top Movies Critics Choice"+".txt")) {
                    WriteOutToFile topMovies = new WriteOutToFile(inputFileName, "MOVIES", topMoviesCriticChoice);
                    topMovies.writeDetails();
                }
                else if(inputFileName.equals("Top Rated Books"+".txt")) {
                    WriteOutToFile topBooks = new WriteOutToFile(inputFileName, "BOOK", bestSellerByDate);
                    topBooks.writeDetails();
                }
                else {
                    WriteOutToFile topArticles = new WriteOutToFile(inputFileName, "ARTICLE", mostSharedArticle);
                    topArticles.writeDetails();
                }
            }
            return p1.toAbsolutePath();
    }


    static void printFileNameAndSize(Path path) throws IOException {
        System.out.println("File name is " + path.getFileName() + " And, File Size is " + Files.size(path) + " bytes.");
    }
}
