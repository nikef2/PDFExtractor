package pdf.extractor.controller;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import org.apache.commons.lang3.StringUtils;
import pdf.extractor.lib.PDFExtractor;

/**
 * Controller class for this application that defines the behaviour of each button in the UI.
 *
 * @author Nikhilesh
 */
public class Controller implements Initializable {

    @FXML
    private Button openFileButton;

    @FXML
    private Button submitButton;

    @FXML
    private TextField inputFileNameTextField;
    private File inputFile;

    @FXML
    private TextField outputFileNameTextField;

    @FXML
    private TextField listOfPagesTextField;

    private final PDFExtractor extractor = new PDFExtractor();

    private static final String COMMA = ",";
    private static final String HYPHEN = "-";

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        /*
         * On click of open File button, open the file chooser and update the text field with the file location.
         */
        openFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openFileAction();
            }
        });

        /*
         * On click handler for the submit button.
         */
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                performSubmitAction();
            }
        });

    }


    /**
     * Opens a FileChooser window and allows user to open a PDF file. The chosen file is stored as inputFile and the
     * text field is updated with the file location.
     *
     */
    private void openFileAction() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File selectedFile = fileChooser.showOpenDialog(openFileButton.getScene().getWindow());

        if (selectedFile != null) {
            inputFile = selectedFile;
            inputFileNameTextField.setText(selectedFile.getPath());

        }
    }

    /**
     * Steps to be done once a user clicks on Submit.
     */
    private void performSubmitAction(){
        if(isValidInputs()) {
            try {
                List<Integer> pagesList = getListOfPages();
                Path outputPath = getOutputPath();
                //Do the actual PDF Extraction/Creation.
                if(extractor.createNewPDF(inputFile.toPath(), pagesList, outputPath)){
                    displayAlert(Alert.AlertType.INFORMATION, "Successfully created PDF in " + outputPath.toString());
                }
                else{
                    displayAlert(Alert.AlertType.ERROR, "Error when trying to create PDF. Please check inputs.");
                }
            }catch (Exception e){
                displayAlert(Alert.AlertType.ERROR, "Error when trying to create PDF. Please check inputs.");
            }
        }
    }

    /**
     * Validates all the inputs from the text fields.
     */
    private boolean isValidInputs(){

        //Validate that all the textFields have some value and if the given input file is valid.
        List<String> listOfErrors = new ArrayList<>();
        if(StringUtils.isBlank(inputFileNameTextField.getText()) || inputFile == null || !inputFile.exists()){
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
            return false;
        }
        else {
            return true;
        }
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
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    /**
     * Gets the list of valid pages from the TextField.
     * @return list of integers with page numbers. Can contain duplicates depending on input.
     */
    private List<Integer> getListOfPages(){
        List<Integer> pageList = new ArrayList<>();

        List<String> stringPageList = Arrays.asList(listOfPagesTextField.getText().split(COMMA));

        for(String pageNumber : stringPageList){

            /*
             * Identify if the pageNumber is actually a list of pages.
             */
            if(pageNumber.contains("-")){
                // Add each page between starting and end to the pageList
                Integer startingPage = Integer.valueOf(StringUtils.substringBefore(pageNumber, HYPHEN));
                Integer endingPage = Integer.valueOf(StringUtils.substringAfter(pageNumber, HYPHEN));
                for(int i=startingPage; i<=endingPage; i++){
                    pageList.add(i);
                }
            }else{
                pageList.add(Integer.valueOf(pageNumber));
            }
        }
        return pageList;
    }

    /**
     * Gets complete path of output file name.
     * @return Path of outputFile.
     */
    private Path getOutputPath(){
        Path outputPath;
        String inputFileParent = inputFile.getParent();
        outputPath = Paths.get(new StringBuilder().append(inputFileParent)
                .append(File.separator)
                .append(outputFileNameTextField.getText())
                .append(".pdf").toString());
        return outputPath;
    }
}
