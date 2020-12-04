package api;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface FetchPrepareRecords {
    String SEP = "===>";
    String newLine = "\r\n";
    void fetchFromAPI(String... strings) throws IOException, InterruptedException;
    String prepareRecord(JSONObject result);
    void openAPIConnection(HttpURLConnection conn) throws IOException;
}
