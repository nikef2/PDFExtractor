package pdf.extractor.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Controller class for this application that defines the behaviour of each button in the UI.
 */
public class Controller implements Initializable {

    @FXML
    private Button openFileButton;

    @FXML
    private TextField fileLocationTextField;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        /*
         * On click, open the file chooser and update the text field with the file location.
         */
        openFileButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                File selectedFile = getFileFromFileChooser();
                if (selectedFile != null) {
                    fileLocationTextField.setText(selectedFile.getPath());
                }
            }
        });
    }

    /**
     * Opens a FileChooser window and returns the file that is chosen.
     * @return
     */
    private File getFileFromFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        return fileChooser.showOpenDialog(openFileButton.getScene().getWindow());
    }
}
