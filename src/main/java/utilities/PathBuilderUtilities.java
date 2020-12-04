package utilities;

import api.WriteArticles;
import api.WriteBooks;
import api.WriteMovies;
import models.MediaCategory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class PathBuilderUtilities {
    private PathBuilderUtilities() {
    }

    private static final String prePath = "build/resources/main";
    private static final String articleEndPoint = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
    private static final String bookEndPoint = "https://api.nytimes.com/svc/books/v3/lists";
    private static final String movieReviewEndPoint = "https://api.nytimes.com/svc/movies/v2/reviews/search.json?opening-date=1980-01-01;2020-01-01&order=by-publication-date&critics_pick=1&reviewer=";


    static Path getInputPathOBJ(String inputFileName) throws IOException, NullPointerException, InterruptedException {
        Path p1 = Paths.get(prePath, inputFileName);
        if (!Files.isRegularFile(p1)) {
            if (inputFileName.equals(MediaCategory.ARTICLE.getMediaDetails() + ".txt")) {
                TimeUnit.SECONDS.sleep(60);
                WriteArticles betaArticle = new WriteArticles(inputFileName, "ARTICLE", articleEndPoint);
                betaArticle.fetchFromAPI();
            } else if (inputFileName.equals(MediaCategory.BOOK.getMediaDetails() + ".txt")) createBooks(inputFileName);
            else createMovies(inputFileName);
        }
        return p1.toAbsolutePath();
    }

    private static void createMovies(String inputFileName) throws InterruptedException, IOException {
        TimeUnit.SECONDS.sleep(60);
        WriteMovies Movie = new WriteMovies(inputFileName, "MOVIE", movieReviewEndPoint);
        Movie.fetchFromAPI("A. O. Scott");
        TimeUnit.SECONDS.sleep(8);
        Movie.fetchFromAPI("Manohla Dargis");
        TimeUnit.SECONDS.sleep(8);
        Movie.fetchFromAPI("Lawrence Van Gelder");
        TimeUnit.SECONDS.sleep(8);
        Movie.fetchFromAPI("Nina Darnton");
    }

    private static void createBooks(String inputFileName) throws InterruptedException, IOException {
        TimeUnit.SECONDS.sleep(60);
        WriteBooks Book = new WriteBooks(inputFileName, "BOOK", bookEndPoint);
        Book.fetchFromAPI("Hardcover Fiction");
        TimeUnit.SECONDS.sleep(8);
        Book.fetchFromAPI("Hardcover NonFiction");
        TimeUnit.SECONDS.sleep(8);
        Book.fetchFromAPI("Paperback Nonfiction");
        TimeUnit.SECONDS.sleep(8);
        Book.fetchFromAPI("Picture Books");
        TimeUnit.SECONDS.sleep(8);
        Book.fetchFromAPI("Series Books");
    }


}
