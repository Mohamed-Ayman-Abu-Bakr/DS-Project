import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

public class Controller {


    XYChart.Series series_insertion, series_merge, series_bubble, series_quick, series_heap, series_expo, series_n_log;
    @FXML
    private Button btn_generate;
    @FXML
    private Button btn_plot;
    @FXML
    private CheckBox chk_bubble;
    @FXML
    private CheckBox chk_expo;
    @FXML
    private CheckBox chk_heap;
    @FXML
    private CheckBox chk_insertion;
    @FXML
    private CheckBox chk_merge;
    @FXML
    private CheckBox chk_n;
    @FXML
    private CheckBox chk_n_log;
    @FXML
    private CheckBox chk_quick;
    @FXML
    private LineChart<String, Number> chrt_chart;
    @FXML
    private TextField txt_num_elements;
    @FXML
    private TextField txt_step;

    @FXML
    void generate(ActionEvent event) {
        Sorting.generate_test(Integer.parseInt(txt_num_elements.getText()));
    }

    @FXML
    void plot(ActionEvent event) throws IOException {

        int size = Integer.parseInt(txt_num_elements.getText());
        int step = Integer.parseInt(txt_step.getText());
        if (chk_insertion.isSelected()) {
            series_insertion = Sorting.generate_insertion_series(size, step);
        }
        if (chk_merge.isSelected()) {
            series_merge = Sorting.generate_merge_series(size, step);
        }
        if (chk_bubble.isSelected()) {
            series_bubble = Sorting.generate_bubble_series(size, step);
        }
        if (chk_quick.isSelected()) {
            series_quick = Sorting.generate_quick_series(size, step);
        }
        if (chk_heap.isSelected()) {
            series_heap = Sorting.generate_heap_series(size, step);
        }
        if (chk_expo.isSelected()) {
            series_expo = Sorting.generate_expo_series(size, step);
        }
        if (chk_n_log.isSelected()) {
            series_n_log = Sorting.generate_n_log_series(size, step);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("graph.fxml"));
        Graph_Controller controller = new Graph_Controller();
        controller.initData(series_insertion, series_merge, series_bubble, series_quick, series_heap, series_expo, series_n_log);
        loader.setController(controller);
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Plot");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    void clear_graph(ActionEvent event) {
        series_insertion = null;
        series_merge = null;
        series_bubble = null;
        series_quick = null;
        series_heap = null;
    }

    @FXML
    void clear_selection(ActionEvent event) {
        chk_insertion.setSelected(false);
        chk_merge.setSelected(false);
        chk_bubble.setSelected(false);
        chk_quick.setSelected(false);
        chk_heap.setSelected(false);
        chk_expo.setSelected(false);
        chk_n_log.setSelected(false);
        txt_num_elements.setText("10000");
        txt_step.setText("50");
    }

}
