package pdf.extractor.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.apache.commons.lang3.StringUtils;

/**
 * Controller class for this application that defines the behaviour of each button in the UI.
 */
public class Controller implements Initializable {

    @FXML
    private Button openFileButton;

    @FXML
    private Button submitButton;

    @FXML
    private TextField fileLocationTextField;
    private File inputFile;

    @FXML
    private TextField outputFileNameTextField;

    @FXML
    private TextField listOfPagesTextField;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        /*
         * On click of open File button, open the file chooser and update the text field with the file location.
         */
        openFileButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                File selectedFile = getFileFromFileChooser();
                if (selectedFile != null) {
                    inputFile = selectedFile;
                    fileLocationTextField.setText(selectedFile.getPath());
                }
            }
        });

        /*
         * On click handler for the submit button.
         */
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                validateAllInputs();
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

    /**
     * Displays an alert box with the given type and text. The alert box waits till user clicks on a button.
     *
     * @param alertType type of Alert to display
     * @param alertText The alert message to be displayed.
     */
    private void displayAlert(final Alert.AlertType alertType, final String alertText){
        Alert alert = new Alert(alertType);
        alert.setContentText(alertText);
        alert.showAndWait();
    }

    /**
     * Validates all the inputs from the text fields.
     */
    private void validateAllInputs(){

        //Validate that all the textFields have some value and if the given input file is valid.
        List<String> listOfErrors = new ArrayList<>();
        if(StringUtils.isBlank(fileLocationTextField.getText()) || inputFile == null || !inputFile.exists()){
            listOfErrors.add("Please enter a valid input file location");
        }
        if(StringUtils.isBlank(outputFileNameTextField.getText())){
            listOfErrors.add("Please enter a valid output file name");
        }
        if(StringUtils.isBlank(listOfPagesTextField.getText())){
            listOfErrors.add("Please enter a valid list of pages");
        }
        if(listOfErrors.size() > 0) {
            final String errorString = StringUtils.join(listOfErrors, System.lineSeparator());
            displayAlert(Alert.AlertType.ERROR, errorString);
        }
    }

}
