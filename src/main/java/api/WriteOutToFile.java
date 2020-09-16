package api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;


public class WriteOutToFile {

    NYTConnection nytConnection = new NYTConnection();
    private final String fullFilePath;
    private final String entityType;
    private final String query_url;

    private final String bookDataFileTitle = "Top Rated Books Title";
    private final String articleDataFileTitle = "Most Shared Articles";
    private final String movieDataFileTitle = "Top Movies By Critics Choice";
    private final String newLine = "\r\n";
    private final String underline = "-------------------------------------";

    public  WriteOutToFile(String fileName, String entityType, String nytEndPoint) throws URISyntaxException {
        CreateAPIDataFiles dataFiles=new CreateAPIDataFiles(fileName);
        this.fullFilePath=dataFiles.getFullFilePath();
        this.entityType=entityType;
        this.query_url=nytEndPoint+"?api-key=";
    }

    public void writeBookToResources(JSONArray res_obj)throws IOException{
        FileWriter fileWriter = new FileWriter( this.fullFilePath );
        fileWriter.write(bookDataFileTitle+newLine+newLine+underline+newLine);
        fileWriter.write("RANK"+ "  "+"TITLE"+newLine);
        for (Object o : res_obj) {
            JSONObject result = (JSONObject) o;
            fileWriter.write(result.get("rank").toString() +"     ");
            fileWriter.write((String) result.get("title")+newLine);
        }
        fileWriter.close();
    }

    public void writeArticleToResources(JSONArray res_obj)throws IOException{
        FileWriter fileWriter = new FileWriter( this.fullFilePath );
        fileWriter.write(articleDataFileTitle+newLine+underline+newLine);
        for (Object o : res_obj) {
            JSONObject result = (JSONObject) o;
            fileWriter.write((String) result.get("title")+newLine);
        }
        fileWriter.close();
    }

    public void writeMovieToResources(JSONArray res_obj)throws IOException{
        FileWriter fileWriter = new FileWriter( this.fullFilePath );
        fileWriter.write(movieDataFileTitle+newLine+underline+newLine);
        for (Object o : res_obj) {
            JSONObject result = (JSONObject) o;
            fileWriter.write((String) result.get("display_title")+newLine);
        }
        fileWriter.close();
    }
    public  JSONObject createJSONObject(BufferedReader bufferedReader) throws  IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject response = (JSONObject) parser.parse(bufferedReader.readLine());
        return response;
    }
    public  void buildJSONObject(JSONObject response ) throws  IOException {
//        JSONObject result= (JSONObject) response.get("results");
        JSONArray res_obj=null;
        if (entityType=="BOOK") { res_obj = (JSONArray) ((JSONObject) response.get("results")).get("books"); writeBookToResources(res_obj); }
        else if (entityType=="ARTICLE") {  res_obj = (JSONArray) response.get("results"); writeArticleToResources(res_obj);}
        else {  res_obj = (JSONArray) response.get("results"); writeMovieToResources(res_obj); }

    }

    public void writeDetails() throws IOException,ParseException {
        HttpURLConnection conn = nytConnection.updateNYTHttpConnection(nytConnection.getNYTHttpConnection(query_url));
            if (conn != null) {
                final InputStream inputStream = conn.getInputStream();
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                JSONObject response = createJSONObject(bufferedReader);
                buildJSONObject(response);

            }
            else System.out.println("The connection is denied, therefore we can't write the file");
    }
}
