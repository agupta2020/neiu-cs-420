package api;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.ws.rs.HttpMethod;
import java.io.*;
import java.net.URISyntaxException;

public class NYTConnection {
    private  HttpURLConnection apiConn=null;
    private final int connectionTimeOut = 1000;

    private final String apiKey = System.getenv("API_KEY");

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
