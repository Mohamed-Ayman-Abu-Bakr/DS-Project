import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class Graph_Controller implements Initializable {

    XYChart.Series<String, Number> series_insertion, series_merge, series_bubble, series_quick, series_heap, series_expo, series_n_log;
    @FXML
    private LineChart<String, Number> chrt_chart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (series_insertion != null) chrt_chart.getData().add(series_insertion);
        if (series_merge != null) chrt_chart.getData().add(series_merge);
        if (series_bubble != null) chrt_chart.getData().add(series_bubble);
        if (series_quick != null) chrt_chart.getData().add(series_quick);
        if (series_heap != null) chrt_chart.getData().add(series_heap);
        if (series_expo != null) chrt_chart.getData().add(series_expo);
        if (series_n_log != null) chrt_chart.getData().add(series_n_log);
    }

    public void initData(XYChart.Series<String, Number> series_insertion, XYChart.Series<String, Number> series_merge, XYChart.Series<String, Number> series_bubble, XYChart.Series<String, Number> series_quick, XYChart.Series<String, Number> series_heap, XYChart.Series<String, Number> series_expo, XYChart.Series<String, Number> series_n_log) {
        this.series_insertion = series_insertion;
        this.series_merge = series_merge;
        this.series_bubble = series_bubble;
        this.series_quick = series_quick;
        this.series_heap = series_heap;
        this.series_expo = series_expo;
        this.series_n_log = series_n_log;
    }
}
