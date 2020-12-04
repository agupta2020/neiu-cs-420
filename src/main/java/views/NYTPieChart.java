package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import models.Article;
import models.Media;
import models.MediaCategory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NYTPieChart {

    private final PieChart pieChart;
    private final PieChart articlePieChart;
    private static Map<MediaCategory, List<Media>> mediaMap;

    NYTPieChart() throws  IOException, InterruptedException, java.text.ParseException {
        // Get API data
        mediaMap= NYTComboBox.callReadApiFile();
        pieChart = getPieChartData();
        articlePieChart = getArticlePieChartData();
    }

    private PieChart getArticlePieChartData(){
        HashMap<String, Integer> sectionHashMap= new HashMap<>();
        ObservableList<PieChart.Data>  articlePieChartData = FXCollections.observableArrayList();
        mediaMap.get(MediaCategory.ARTICLE).forEach(media -> {
            int count = sectionHashMap.getOrDefault(((Article) media).getSection_name(), 0);
            sectionHashMap.put(((Article)media).getSection_name(),count+1);
        });

        sectionHashMap.forEach((section_name, count) -> articlePieChartData.add(new PieChart.Data(section_name+"="+count, count)));


        final PieChart pieChart = new PieChart(articlePieChartData);
        pieChart.setTitle("Number of Articles by Section Type");
        return pieChart;
    }

    private PieChart getPieChartData(){

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data(MediaCategory.MOVIE.name()+"="+ mediaMap.get(MediaCategory.MOVIE).size(), mediaMap.get(MediaCategory.MOVIE).size()),
                        new PieChart.Data(MediaCategory.BOOK.name()+"="+  mediaMap.get(MediaCategory.BOOK).size(),  mediaMap.get(MediaCategory.BOOK).size()),
                        new PieChart.Data(MediaCategory.ARTICLE.name()+"="+  mediaMap.get(MediaCategory.ARTICLE).size(), mediaMap.get(MediaCategory.ARTICLE).size()));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Count by Media Categories");
        return chart;
    }

    public PieChart getPieChart() {
        return pieChart;
    }

    public PieChart getArticlePieChart() {
        return articlePieChart;
    }

}
