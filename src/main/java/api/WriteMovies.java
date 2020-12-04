package api;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class WriteMovies implements FetchPrepareRecords{
    private final String fullFilePath;
    private final String entityType;
    private final String query_url;
    NYTConnection nytConnection;

    public WriteMovies(String fileName, String entityType, String nytEndPoint) throws IOException {
        nytConnection = new NYTConnection();
        CreateAPIDataFiles dataFiles = new CreateAPIDataFiles(fileName);
        this.fullFilePath = dataFiles.getFullFilePath();
        this.entityType = entityType;
        this.query_url = nytEndPoint;
    }


    @Override
    public void fetchFromAPI(String... reviewer) throws IOException {
        HttpURLConnection conn = nytConnection.
                updateNYTHttpConnection(nytConnection
                        .getMovieReviewNYTHttpConnection(query_url, reviewer[0]));
        openAPIConnection(conn);
    }

    @Override
    public void openAPIConnection(HttpURLConnection conn) throws IOException {
        if (conn != null) {
            final InputStream inputStream = conn.getInputStream();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            bufferedReader.lines().map(line -> new JSONObject(line).getJSONArray("results")).forEach(res_obj ->{

                for (Object o : res_obj) {
                    JSONObject result = (JSONObject) o;
                    try {
                        // It should Append
                        Files.writeString(Paths.get(this.fullFilePath), prepareRecord(result), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
        else System.out.println("The connection is denied, therefore we can't write the file");
    }

    @Override
    public String prepareRecord(JSONObject result) {
        return result.get("display_title") + SEP
                +  result.get("summary_short") + SEP
                + result.get("date_updated") + SEP
                +  result.get("byline")
                + newLine;
    }




}
