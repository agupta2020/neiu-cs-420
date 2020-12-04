package views;

import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.text.ParseException;


public class NYTRadioButtons {

    private final BorderPane borderPane;
    private final ToggleGroup group;
    private final PieChart mediaPieChart;
    private final PieChart articlePieChart;
    private final LineChart<String, Number> lineChart;
    private final BarChart<String, Number> barChart;



    public NYTRadioButtons() throws InterruptedException, ParseException, IOException {
        group = new ToggleGroup();
        mediaPieChart=new NYTPieChart().getPieChart();
        articlePieChart=new NYTPieChart().getArticlePieChart();
        lineChart=new NYTArticleLineChart().getLineChart();
        barChart=new NYTBarChart().getBarChart();
        borderPane = radioGroup();
    }

    private BorderPane radioGroup() {
        RadioButton rb1 = getRadioButton(group, "Count by media categories", "s1");
        RadioButton rb2 = getRadioButton(group, "Get distribution of Book listing type", "s2");
        RadioButton rb3 = getRadioButton(group, "Articles distribution by section in last year", "s3");
        RadioButton rb4 = getRadioButton(group, "Number of article published on Sports, Arts, Worlds by month/Year", "s4");
        radioButtonListener(group);
        return getBorderPane(rb1, rb2, rb3, rb4);
    }

    private RadioButton getRadioButton(ToggleGroup group, String s, String s1) {
        RadioButton rb1 = new RadioButton(s);
        rb1.setToggleGroup(group);
        rb1.setUserData(s1);
        return rb1;
    }

    private void radioButtonListener(ToggleGroup group) {
        group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (group.getSelectedToggle() != null) prepareDifferentCharts(group);
        });
    }

    private void prepareDifferentCharts(ToggleGroup group) {
        if (group.getSelectedToggle().getUserData() == "s1")
            borderPane.setCenter(mediaPieChart);
        else if (group.getSelectedToggle().getUserData() == "s2")
            borderPane.setCenter(barChart);
        else if (group.getSelectedToggle().getUserData() == "s3")
            borderPane.setCenter(articlePieChart);
        else if (group.getSelectedToggle().getUserData() == "s4")
            borderPane.setCenter(lineChart);
    }


    private BorderPane getBorderPane(RadioButton rb1, RadioButton rb2, RadioButton rb3, RadioButton rb4) {
        return getBorderPaneVBox(rb1, rb2, rb3, rb4);
    }


    private BorderPane getBorderPaneVBox(RadioButton rb1, RadioButton rb2, RadioButton rb3, RadioButton rb4) {
        VBox vbox = getVBox(rb1, rb2, rb3, rb4);
        HBox hbox = new HBox();
        hbox.getChildren().add(vbox);
        hbox.setSpacing(50);
        hbox.setPadding(new Insets(20, 10, 10, 20));
        BorderPane pane1 = new BorderPane();
        pane1.setTop(hbox);
        return pane1;
    }


    private VBox getVBox(RadioButton rb1, RadioButton rb2, RadioButton rb3, RadioButton rb4) {
        VBox vbox = new VBox();
        vbox.getChildren().add(rb1);
        vbox.getChildren().add(rb2);
        vbox.getChildren().add(rb3);
        vbox.getChildren().add(rb4);
        vbox.setSpacing(10);
        return vbox;
    }

    public BorderPane getRadioBorderPane() {
        return borderPane;
    }

}
