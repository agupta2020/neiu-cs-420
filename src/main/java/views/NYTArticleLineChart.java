package views;

import javafx.geometry.Side;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import models.Article;
import models.Media;
import models.MediaCategory;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class NYTArticleLineChart {

    private LineChart<String, Number> lineChart;

    // it should be only article count series
    private static XYChart.Series<String, Number> sportsArticleSeries;
    private static XYChart.Series<String, Number> artsArticleSeries;
    private static XYChart.Series<String, Number> worldsArticleSeries;
    private static Map<MediaCategory, List<Media>> mediaMap;

    public NYTArticleLineChart() throws ParseException, InterruptedException, IOException {
        // Get API data
        mediaMap = NYTComboBox.callReadApiFile();
        initViews();
        lineChart = getLineChartData();

    }

    private void initViews() {
        sportsArticleSeries = new XYChart.Series<>();
        artsArticleSeries = new XYChart.Series<>();
        worldsArticleSeries = new XYChart.Series<>();
        sportsArticleSeries.setName("Sports");
        artsArticleSeries.setName("Arts");
        worldsArticleSeries.setName("World");


    }

    public LineChart<String, Number> getLineChart() {
        return lineChart;
    }

    private LineChart<String, Number> getLineChartData() {
        createLineChart();
        createSeries();
        addLineSeries();
        return lineChart;

    }

    private void addLineSeries() {
        lineChart.getData().add(sportsArticleSeries);
        lineChart.getData().add(artsArticleSeries);
        lineChart.getData().add(worldsArticleSeries);
    }

    private void updateHashMapKeyValue(HashMap<String, Integer> sportsArticleHashMap, HashMap<String, Integer> wordsArticleHashMap, HashMap<String, Integer> artsArticleHashMap) {

        HashSet<String> set = new HashSet<>(sportsArticleHashMap.keySet());
        set.addAll(new HashSet<>(wordsArticleHashMap.keySet()));
        set.addAll(new HashSet<>(artsArticleHashMap.keySet()));
        set.forEach(e -> {
            if (!sportsArticleHashMap.containsKey(e)) sportsArticleHashMap.put(e, 0);
            if (!wordsArticleHashMap.containsKey(e)) wordsArticleHashMap.put(e, 0);
            if (!artsArticleHashMap.containsKey(e)) artsArticleHashMap.put(e, 0);
        });
    }

    private void createSeries() {
        HashMap<String, Integer> sportsArticleHashMap = new HashMap<>();
        HashMap<String, Integer> artsArticleHashMap = new HashMap<>();
        HashMap<String, Integer> wordsArticleHashMap = new HashMap<>();
        mediaMap.get(MediaCategory.ARTICLE).forEach(media -> {
            Article article = ((Article) media);
            LocalDate localDate = article.getPublish_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int IntegerLocalDate = localDate.getMonthValue();
            String monthYear = (Integer.toString(IntegerLocalDate).length() == 1 ? "0" + localDate.getMonthValue() : localDate.getMonthValue()) + "-" + localDate.getYear();
            int sportsCount = sportsArticleHashMap.getOrDefault(monthYear, 0);
            int artsCount = artsArticleHashMap.getOrDefault(monthYear, 0);
            int worldsCount = wordsArticleHashMap.getOrDefault(monthYear, 0);
            switch (article.getSection_name().toLowerCase()) {
                case "sports":
                    sportsArticleHashMap.put(monthYear, sportsCount + 1);
                    break;
                case "arts":
                    artsArticleHashMap.put(monthYear, artsCount + 1);
                    break;
                case "world":
                    wordsArticleHashMap.put(monthYear, worldsCount + 1);
                    break;
            }
            updateHashMapKeyValue(sportsArticleHashMap, wordsArticleHashMap, artsArticleHashMap);
        });


        sportsArticleHashMap.forEach((monthYear, count) -> sportsArticleSeries.getData().add(new XYChart.Data<>(monthYear, count)));
        artsArticleHashMap.forEach((monthYear, count) -> artsArticleSeries.getData().add(new XYChart.Data<>(monthYear, count)));
        wordsArticleHashMap.forEach((monthYear, count) -> worldsArticleSeries.getData().add(new XYChart.Data<>(monthYear, count)));
        // Sorting the series data by month/Year
        sportsArticleSeries.getData().sort(Comparator.comparingInt(monthYear -> Integer.parseInt(monthYear.getXValue().substring(3) + "" + monthYear.getXValue().substring(0, 2))));
        worldsArticleSeries.getData().sort(Comparator.comparingInt(monthYear -> Integer.parseInt(monthYear.getXValue().substring(3) + "" + monthYear.getXValue().substring(0, 2))));
        artsArticleSeries.getData().sort(Comparator.comparingInt(monthYear -> Integer.parseInt(monthYear.getXValue().substring(3) + "" + monthYear.getXValue().substring(0, 2))));

    }

    private void createLineChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Month-Year(mm-yyyy)");
        xAxis.setSide(Side.TOP);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Article Count");
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Section Sports,World &  Arts");

    }


}
