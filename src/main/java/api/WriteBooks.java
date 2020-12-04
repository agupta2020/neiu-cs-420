package api;

import org.json.JSONArray;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WriteBooks implements FetchPrepareRecords{
    private final String fullFilePath;
    private final String entityType;
    private final String query_url;
    NYTConnection nytConnection;
    private static Date dateToUse;
    private static DateFormat timeFormat;

    public WriteBooks(String fileName, String entityType, String nytEndPoint) throws IOException {
        nytConnection = new NYTConnection();
        CreateAPIDataFiles dataFiles = new CreateAPIDataFiles(fileName);
        this.fullFilePath = dataFiles.getFullFilePath();
        this.entityType = entityType;
        this.query_url = nytEndPoint;
        prepareDateObj();
    }
    @Override
    public void fetchFromAPI(String... strings) throws IOException, InterruptedException {
        Calendar c = Calendar.getInstance();
        // pulling weekly data as data is published weekly only
        for (int i = 0; i < 30; i = i + 7) {
            c.setTime(dateToUse);
            c.add(Calendar.DATE, -i);
            HttpURLConnection conn = nytConnection.
                    updateNYTHttpConnection(nytConnection
                            .getBookNYTHttpConnection(query_url, timeFormat.format(c.getTime()), strings[0]));
            openAPIConnection(conn);
            TimeUnit.SECONDS.sleep(7);

        }
    }

    public void openAPIConnection(HttpURLConnection conn) throws IOException {
        if (conn != null) {
            final InputStream inputStream = conn.getInputStream();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            bufferedReader.lines()
                    .map(line -> {
                        JSONObject resultObj = (JSONObject) new JSONObject(line).get("results");
                        String list_name = resultObj.get("list_name").toString();
                        String bestsellers_date = resultObj.get("bestsellers_date").toString();
                        JSONArray resultArr = new JSONObject(line).getJSONObject("results").getJSONArray("books");
                        JSONArray jsonArray = new JSONArray();
                        resultArr.forEach(jsonObject -> jsonArray.put(prepareBookJsonObject(list_name, bestsellers_date, (JSONObject) jsonObject)));
                        return jsonArray;

                    }).forEach(jsonObj -> {
                for (Object o : jsonObj) {
                    JSONObject result = (JSONObject) o;
                    try {
                        // It should Append
                        Files.writeString(Paths.get(this.fullFilePath), prepareRecord(result), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            });
        } else System.out.println("The connection is denied, therefore we can't write the file");
    }

    @Override
    public String prepareRecord(JSONObject result) {
        return result.get("title") + SEP
                + result.get("description") + SEP
                +  result.get("bestsellers_date") + SEP
                +  result.get("list_name") + SEP
                +  result.get("rank")
                + newLine;
    }
    private void prepareDateObj(){
        dateToUse = new Date();
        String timeFormatString = "yyyy-MM-dd";
        timeFormat = new SimpleDateFormat(timeFormatString);
    }

    private JSONObject prepareBookJsonObject(String list_name, String bestsellers_date, JSONObject jsonObject) {
        JSONObject obj = new JSONObject();
        obj.put("rank", jsonObject.get("rank"));
        obj.put("title", jsonObject.get("title"));
        obj.put("description", jsonObject.get("description"));
        obj.put("list_name", list_name);
        obj.put("bestsellers_date", bestsellers_date);
        return obj;
    }
}
