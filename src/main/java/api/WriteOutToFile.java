package api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;


public class WriteOutToFile {

    NYTConnection nytConnection = new NYTConnection();
    private final String fullFilePath;
    private final String entityType;
    private final String query_url;
    final static String SEP = "===>";

    private final String newLine = "\r\n";


    public WriteOutToFile(String fileName, String entityType, String nytEndPoint) throws URISyntaxException {
        CreateAPIDataFiles dataFiles = new CreateAPIDataFiles(fileName);
        this.fullFilePath = dataFiles.getFullFilePath();
        this.entityType = entityType;
        this.query_url = nytEndPoint + "?api-key=";
    }


    public void writeDetails() throws IOException {
        HttpURLConnection conn = nytConnection.updateNYTHttpConnection(nytConnection.getNYTHttpConnection(query_url));
        FileWriter fileWriter = new FileWriter(this.fullFilePath);
        if (conn != null) {
            final InputStream inputStream = conn.getInputStream();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            bufferedReader.lines().map(line -> {
                if (entityType == "BOOK") return new JSONObject(line).getJSONObject("result").get("book");
                else return new JSONObject(line).getJSONObject("result");
            }).forEach(res_obj -> {
                for (Object o : (JSONArray) res_obj) {
                    JSONObject result = (JSONObject) o;
                    try {
                        if (entityType == "BOOK") {
                            fileWriter.write((String) result.get("title") + SEP + (String) result.get("description") + newLine);
                        } else if (entityType == "ARTICLE") {
                            fileWriter.write((String) result.get("title") + SEP + (String) result.get("abstract") + newLine);
                        } else {
                            fileWriter.write((String) result.get("display_title") + SEP + (String) result.get("summary_short") + newLine);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else System.out.println("The connection is denied, therefore we can't write the file");
        fileWriter.close();
    }
}