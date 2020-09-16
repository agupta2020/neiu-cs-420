import api.WriteOutToFile;

public class Main {

    //summary: "Most Shared Articles"
    private static  String mostSharedArticle = "https://api.nytimes.com/svc/mostpopular/v2/emailed/30.json";
    //summary: "Best Sellers List by Date"
    private static String bestSellerByDate = "https://api.nytimes.com/svc/books/v3/lists/2020-09-01/hardcover-fiction.json";
    //summary: "Top Movies By Critics Choice"
    private static String topMoviesCriticChoice = "https://api.nytimes.com/svc/movies/v2/reviews/picks.json";
    public static void main(String args[]) {
        try {
            WriteOutToFile topRatedBooks = new WriteOutToFile("Top Rated Books.txt", "BOOK", bestSellerByDate);
            topRatedBooks.writeDetails();
            WriteOutToFile mostSharedArticles = new WriteOutToFile("Most Shared Articles.txt", "ARTICLE", mostSharedArticle);
            mostSharedArticles.writeDetails();
            WriteOutToFile topMovies = new WriteOutToFile("Top Movies Critics Choice.txt", "MOVIES", topMoviesCriticChoice);
            topMovies.writeDetails();
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
}

