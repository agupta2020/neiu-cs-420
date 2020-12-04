package utilities;

import models.Article;
import models.Book;
import models.Media;
import models.Movie;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

class StringReader extends ABSReader {


    private final String SEP = "===>";
    private String title;
    private String short_summary;
    private Date publish_date;

    StringReader(Path inputPath) {
        super(inputPath);
    }

    List<Media> readMovies(String category) throws IOException {
        List<Media> mediaList = new ArrayList<>();
        Collections.singletonList(Files.lines(getInputPathOBJ(), StandardCharsets.ISO_8859_1))
                .forEach(fileStr -> fileStr.forEach(str -> {
                    getStandardFields(str);
                    String reviewer = str.split(SEP)[3];
                    mediaList.add(new Movie(category, title, short_summary, publish_date, reviewer));
                }));
        return mediaList;
    }

    private void getStandardFields(String str) {
        title = str.split(SEP)[0];
        short_summary = str.split(SEP)[1];
        String str_date = str.split(SEP)[2];
        try {
            publish_date = new SimpleDateFormat("yyyy-MM-dd").parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    List<Media> readBooks(String category) throws IOException {
        List<Media> mediaList = new ArrayList<>();
        Collections.singletonList(Files.lines(getInputPathOBJ(), StandardCharsets.ISO_8859_1))
                .forEach(fileStr -> fileStr.forEach(str -> {
                    getStandardFields(str);
                    String book_type = str.split(SEP)[3];
                    String rank = str.split(SEP)[4];
                    mediaList.add(new Book(category, title, short_summary, publish_date, book_type, rank));
                }));
        return mediaList;
    }

    List<Media> readArticles(String category) throws IOException {
        List<Media> mediaList = new ArrayList<>();
        for (Stream<String> fileStr : Collections.singletonList(Files.lines(getInputPathOBJ(), StandardCharsets.ISO_8859_1))) {
            fileStr.forEach(str -> {
                String title = str.split(SEP)[0];
                String short_summary = str.split(SEP)[1];
                String source = str.split(SEP)[2];
                String str_date = str.split(SEP)[3];
                Date publish_date = null;
                try {
                    publish_date = new SimpleDateFormat("yyyy-MM-dd").parse(str_date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mediaList(category, mediaList, str, title, short_summary, source, publish_date);
            });
        }
        return mediaList;
    }

    private void mediaList(String category, List<Media> mediaList, String str, String title, String short_summary, String source, Date publish_date) {
        String section_name = str.split(SEP)[4];
        String document_type = str.split(SEP)[5];
        String type_of_material = str.split(SEP)[6];
        String word_count = str.split(SEP)[7];
        mediaList.add(new Article(title, category, short_summary, source, section_name, publish_date, document_type, type_of_material, Integer.parseInt(word_count)));
    }
}
