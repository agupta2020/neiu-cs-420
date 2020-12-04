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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WriteArticles implements FetchPrepareRecords {
    private final String fullFilePath;
    private final String query_url;
    NYTConnection nytConnection;
    private static Date dateToUse;
    private static DateFormat timeDateFormatNoDash;

    public WriteArticles(String fileName, String entityType, String nytEndPoint) throws IOException {
        nytConnection = new NYTConnection();
        CreateAPIDataFiles dataFiles = new CreateAPIDataFiles(fileName);
        this.fullFilePath = dataFiles.getFullFilePath();
        this.query_url = nytEndPoint;
        prepareDateObj();
    }

    @Override
    public void fetchFromAPI(String... strings) throws IOException, InterruptedException {
        Calendar c = Calendar.getInstance();

        for (int diff = 1; diff <= 12; diff++) {
            c.setTime(dateToUse);
            c.add(Calendar.MONTH, -(diff - 1));
            String end_date = timeDateFormatNoDash.format(c.getTime());
            c.setTime(dateToUse);
            c.add(Calendar.MONTH, -diff);
            String begin_date = timeDateFormatNoDash.format(c.getTime());

            for (int i = 0; i <= 2; i++) {
                HttpURLConnection conn = nytConnection.
                        updateNYTHttpConnection(nytConnection
                                .getArticleNYTHttpConnection(query_url, begin_date, end_date, i));

                openAPIConnection(conn);
                TimeUnit.SECONDS.sleep(8);
            }
            TimeUnit.SECONDS.sleep(16);
        }
    }

    public void openAPIConnection(HttpURLConnection conn) throws IOException {
        if (conn != null) {
            final InputStream inputStream = conn.getInputStream();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            bufferedReader.lines().map(line -> new JSONObject(line).getJSONObject("response").getJSONArray("docs")).forEach(res_obj -> {
                for (Object o : res_obj) {
                    JSONObject result = (JSONObject) o;
                    try {
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
        return result.getJSONObject("headline").get("main") + SEP
                + result.get("abstract") + SEP
                + result.get("source") + SEP
                + result.get("pub_date") + SEP
                + result.get("section_name") + SEP
                + result.get("document_type") + SEP
                + result.get("type_of_material") + SEP
                + result.get("word_count")
                + newLine;
    }

    private void prepareDateObj() {
        dateToUse = new Date();
        String timeFormatNoDash = "yyyyMMdd";
        timeDateFormatNoDash = new SimpleDateFormat(timeFormatNoDash);
    }


}
