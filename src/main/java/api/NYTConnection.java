package api;

import javax.ws.rs.HttpMethod;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class NYTConnection {
    private static HttpURLConnection apiConn = null;
    private final static int connectionTimeOut = 1000;

    public HttpURLConnection updateNYTHttpConnection(HttpURLConnection apiConn) throws java.io.IOException {
        HttpURLConnection updated_conn;
        apiConn.setConnectTimeout(connectionTimeOut);
        apiConn.setRequestMethod(HttpMethod.GET);
        apiConn.setRequestProperty("format", "json");
        if (apiConn.getResponseCode() == 200) {
            updated_conn = apiConn;
        } else {
            throw new IOException(apiConn.getResponseMessage());
        }
        return updated_conn;
    }


    public HttpURLConnection getArticleNYTHttpConnection(String query_url, String begin_date, String end_date, int page) throws java.io.IOException {
        String partialURL = "?begin_date=" + begin_date + "&end_date=" + end_date + "&page=" + page + "&api-key=";
        final URL articleURL = new URL(query_url + partialURL + System.getenv("API_KEY"));
        apiConn = (HttpURLConnection) articleURL.openConnection();

        return apiConn;
    }

    public HttpURLConnection getBookNYTHttpConnection(String query_url, String date, String listType) throws java.io.IOException {

        final URL bookURL = new URL(query_url + "/" + date + "/" + listType + ".json?api-key=" + System.getenv("API_KEY"));
        apiConn = (HttpURLConnection) bookURL.openConnection();
        return apiConn;
    }

    public HttpURLConnection getMovieReviewNYTHttpConnection(String query_url, String reviewer) throws java.io.IOException {

        final URL movieReviewURL = new URL(query_url + reviewer + "&api-key=" + System.getenv("API_KEY"));
        apiConn = (HttpURLConnection) movieReviewURL.openConnection();
        return apiConn;
    }

}
