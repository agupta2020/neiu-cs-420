package views;


import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import models.Book;
import models.Media;
import models.MediaCategory;

import java.io.IOException;

import java.util.List;
import java.util.Map;

public class NYTBarChart {

    private BarChart<String, Number> barChart;
    private static XYChart.Series<String, Number> series1;
    private static XYChart.Series<String, Number> series2;
    private static XYChart.Series<String, Number> series3;
    private static XYChart.Series<String, Number> series4;
    private static XYChart.Series<String, Number> series5;
    private static Map<MediaCategory, List<Media>> mediaMap;


    public NYTBarChart() throws  IOException, InterruptedException, java.text.ParseException {
        mediaMap= NYTComboBox.callReadApiFile();
        initViews();
        barChart = getBarChartData();
    }

    private void initViews() {
        series1 = new XYChart.Series<>();
        series2 = new XYChart.Series<>();
        series3 = new XYChart.Series<>();
        series4 = new XYChart.Series<>();
        series5 = new XYChart.Series<>();
        setSeriesProperties();
    }

    private BarChart<String, Number> getBarChartData() {

        createBarGraph();
        createSeries(series1, "Hardcover Fiction");
        createSeries(series2, "Hardcover Nonfiction");
        createSeries(series3, "Paperback Nonfiction");
        createSeries(series4, "Picture Books");
        createSeries(series5, "Series Books");
        addDataSeries();
        return barChart;

    }

    private void addDataSeries() {
        barChart.getData().add(series1);
        barChart.getData().add(series2);
        barChart.getData().add(series3);
        barChart.getData().add(series4);
        barChart.getData().add(series5);
    }

    static void createSeries(XYChart.Series<String, Number> series, String condition){
        series.getData().add(new XYChart.Data<>(condition, mediaMap
                .get(MediaCategory.BOOK).stream()
                .filter(e -> ((Book)e).getBook_type().replaceAll("\\s", "")
                        .toLowerCase().equals(condition.toLowerCase().replaceAll("\\s", "")))
                .count()));

    }

    private void setSeriesProperties() {
        series1.setName("Fiction");
        series2.setName("Nonfiction");
        series3.setName("Paperback");
        series4.setName("Picture Books");
        series5.setName("Series Books");
    }

    private void createBarGraph() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Book Type Count Distribution");
        xAxis.setLabel("Book Listing Type");
        yAxis.setLabel("Count");

    }

    public BarChart<String, Number> getBarChart() {
        return barChart;
    }


}
