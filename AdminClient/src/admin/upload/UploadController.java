package admin.upload;


import admin.app.AppController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;


import java.io.File;

public class UploadController {
    @FXML private Button LoadFileButton;
    @FXML private Label fileChosenLabel;
    @FXML private Label fileLabel;
    @FXML private AppController appController;
    private final SimpleStringProperty fileChosenStringProperty;
    private final SimpleBooleanProperty isXMLLoaded;

    public UploadController() {
        fileChosenStringProperty = new SimpleStringProperty();
        isXMLLoaded = new SimpleBooleanProperty(false);
    }
    @FXML
    public void initialize(){
        fileChosenLabel.textProperty().bind(fileChosenStringProperty);
        fileLabel.visibleProperty().bind(isXMLLoaded);

    }

    public void setFileChosenStringProperty(String fileChosenStringProperty) {
        this.fileChosenStringProperty.set(fileChosenStringProperty);
    }

    @FXML
    void uploadWorldFromXMLButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open XML file");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(LoadFileButton.getScene().getWindow());
        if (selectedFile == null) {
            return;
        }
        //appController.uploadWorldFromXML(selectedFile);
    }


    public SimpleBooleanProperty isXMLLoadedProperty() {
        return isXMLLoaded;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }
}
