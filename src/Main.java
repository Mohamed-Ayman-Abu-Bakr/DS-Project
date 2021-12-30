import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main_window.fxml")));
        stage.initStyle(StageStyle.DECORATED);
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setOnHidden(e -> Platform.exit());
        stage.setScene(scene);
        stage.show();
    }
}
