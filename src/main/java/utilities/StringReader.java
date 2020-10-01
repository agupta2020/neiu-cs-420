package utilities;

import domain.Article;
import domain.Book;
import domain.Media;
import domain.Movie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class StringReader extends ABSReader {

    private String lines;

     StringReader(Path inputPath) {
        super(inputPath);
    }

    /*List read() throws IOException {
        List moviesList= new ArrayList<>();
        for (String str : Files.readAllLines(getInputPathOBJ())){
               moviesList.add(new Movie(str, "MOVIE"));
        }
        return moviesList;
    }*/

    List<? extends Media> read(String category) throws IOException {
        List mediaList= new ArrayList<>();
        for (String str : Files.readAllLines(getInputPathOBJ())){
            if(category == "Top Movies Critics Choice")
            mediaList.add(new Movie(str,category));
            else if(category == "Top Rated Books")
                mediaList.add(new Book(str,category));
            else {
                mediaList.add(new Article(str, category));
            }
        }
        return mediaList;
    }


}
