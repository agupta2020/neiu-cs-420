package api;


import java.net.HttpURLConnection;
import java.net.URL;
import javax.ws.rs.HttpMethod;

public class NYTConnection {
    private  static HttpURLConnection apiConn=null;
    private final static int connectionTimeOut = 1000;

    private final static String apiKey = System.getenv("API_KEY");

    public HttpURLConnection updateNYTHttpConnection(HttpURLConnection apiConn) throws java.io.IOException{
        HttpURLConnection updated_conn=null;
        apiConn.setConnectTimeout(connectionTimeOut);
        apiConn.setRequestMethod(HttpMethod.GET);
        apiConn.setRequestProperty("format", "json");
        if (apiConn.getResponseCode() == 200) updated_conn= apiConn;
        return updated_conn;
    }

    public HttpURLConnection getNYTHttpConnection(String query_url) throws java.net.MalformedURLException, java.io.IOException{

        final URL bookURL= new URL(query_url+ System.getenv("API_KEY"));
        apiConn = (HttpURLConnection) bookURL.openConnection();
        return apiConn;
    }
}
