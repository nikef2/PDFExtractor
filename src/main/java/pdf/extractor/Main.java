package pdf.extractor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class which is the entry point to the application. It creates a stage
 * and scene and uses the fxml file for creating the UI.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("layout.fxml"));
        primaryStage.setTitle("PDF Extractor");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
