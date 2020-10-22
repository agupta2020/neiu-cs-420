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
    final static  String SEP="===>";

    private final String newLine = "\r\n";


    public  WriteOutToFile(String fileName, String entityType, String nytEndPoint) throws URISyntaxException {
        CreateAPIDataFiles dataFiles=new CreateAPIDataFiles(fileName);
        this.fullFilePath=dataFiles.getFullFilePath();
        this.entityType=entityType;
        this.query_url=nytEndPoint+"?api-key=";
    }

    public void writeBookToResources(JSONArray res_obj)throws IOException{
        FileWriter fileWriter = new FileWriter( this.fullFilePath );
        for (Object o : res_obj) {
            JSONObject result = (JSONObject) o;
            fileWriter.write((String) result.get("title")+SEP+(String) result.get("description")+newLine);
        }
        fileWriter.close();
    }

    public void writeArticleToResources(JSONArray res_obj)throws IOException{
        FileWriter fileWriter = new FileWriter( this.fullFilePath );
        for (Object o : res_obj) {
            JSONObject result = (JSONObject) o;
            fileWriter.write((String) result.get("title")+SEP+(String) result.get("abstract")+newLine);
        }
        fileWriter.close();
    }

    public void writeMovieToResources(JSONArray res_obj)throws IOException{
        FileWriter fileWriter = new FileWriter( this.fullFilePath );
        for (Object o : res_obj) {
            JSONObject result = (JSONObject) o;
            fileWriter.write((String) result.get("display_title")+SEP+(String) result.get("summary_short")+newLine);
        }
        fileWriter.close();
    }
    public  JSONObject createJSONObject(BufferedReader bufferedReader) throws  IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject response = (JSONObject) parser.parse(bufferedReader.readLine());
        return response;
    }
    public  void buildJSONObject(JSONObject response ) throws  IOException {
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
