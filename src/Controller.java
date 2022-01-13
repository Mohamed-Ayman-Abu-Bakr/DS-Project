import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Controller {

    XYChart.Series<String,Number> series_insertion, series_merge, series_bubble, series_quick, series_heap, series_expo, series_n_log;

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
    private CheckBox chk_n_log;
    @FXML
    private CheckBox chk_quick;
    @FXML
    private TextField txt_num_elements;
    @FXML
    private TextField txt_step;

    int getSize() throws NumberFormatException {
        return Integer.parseInt(txt_num_elements.getText());
    }
    int getStepSize() throws NumberFormatException{
        return Integer.parseInt(txt_step.getText());
    }

    void show_Error(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }
    void show_Message(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }

    @FXML
    void generate() {
        int size;
        try{
            size = getSize();
        }catch (NumberFormatException e){
            show_Error("Invalid Number, Please enter a valid number");
            return;
        }

        if (size<1||size>10000){
            show_Error("Please enter an array size between 1 and 10000");
            return;
        }
        Sorting.generate_test(size);
        show_Message("Test File generated successfully, you can find it in \"test_data.txt\" on the desktop");
    }

    void show_Graph_Window() throws IOException{
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
    void plot() throws IOException {
        clear_graph();
        int size;
        int step;
        try{
            size = getSize();
            step = getStepSize();
        }catch (NumberFormatException e){
            show_Error("Invalid Number, Please enter a valid number");
            return;
        }


        if(size<1||size>10000){
            show_Error("Please enter an array size between 1 and 10000");
            return;
        }
        if(step<1){
            show_Error("Please enter a step size greater than 0");
            return;
        }

        if (chk_insertion.isSelected()) series_insertion = Sorting.generate_series("Insertion", size, step);
        if (chk_merge.isSelected()) series_merge = Sorting.generate_series("Merge", size, step);
        if (chk_bubble.isSelected()) series_bubble = Sorting.generate_series("Bubble", size, step);
        if (chk_quick.isSelected()) series_quick = Sorting.generate_series("Quick", size, step);
        if (chk_heap.isSelected()) series_heap = Sorting.generate_series("Heap", size, step);
        if (chk_expo.isSelected()) series_expo = Sorting.generate_series("Expo", size, step);
        if (chk_n_log.isSelected()) series_n_log = Sorting.generate_series("n log", size, step);

        show_Graph_Window();
    }

    @FXML
    void clear_graph() {
        series_insertion = null;
        series_merge = null;
        series_bubble = null;
        series_quick = null;
        series_heap = null;
        series_expo = null;
        series_n_log = null;
        //show_Message("Graph cleared successfully");
    }

    @FXML
    void clear_selection() {
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

    String get_Sorting_Algo(){
        int cnt = 0;
        String algo = "";
        if (chk_insertion.isSelected()) {
            cnt++;
            algo = "Insertion";
        }
        if (chk_merge.isSelected()) {
            cnt++;
            algo = "Merge";
        }
        if (chk_bubble.isSelected()) {
            cnt++;
            algo = "Bubble";
        }
        if (chk_quick.isSelected()) {
            cnt++;
            algo = "Quick";
        }
        if (chk_heap.isSelected()) {
            cnt++;
            algo = "Heap";
        }
        if (cnt != 1) {
            String msg;
            if(cnt>1)msg="Please choose only one algorithm";
            else msg="Please choose a sorting algorithm";

            show_Error(msg);
            return null;
        }
        return algo;
    }

    @FXML
    void sort() {
        String algo = get_Sorting_Algo();
        if(algo!=null)
        try {
            Sorting.sort_test(algo);
            show_Message("Test File sorted successfully, you can find the result in \"test_data_sorted.txt\" on the desktop");
        } catch (FileNotFoundException e) {
            show_Error("The file \"test_data.txt\" doesn't exist, Check that it exists and try again.");
        }
    }

}
