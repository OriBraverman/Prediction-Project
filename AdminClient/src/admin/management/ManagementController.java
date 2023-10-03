package admin.management;

import admin.app.AppController;
import admin.tasks.FetchQueueManagementTimer;
import admin.upload.UploadController;
import dto.QueueManagementDTO;
import dto.StatusDTO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.io.File;
import java.util.Timer;

public class ManagementController {
    @FXML private HBox uploadComponent;
    @FXML private UploadController uploadComponentController;
    @FXML private Button setThreadsCountButton;
    @FXML private TextField setThreadsCountTextField;
    @FXML private ListView threadPoolManagementListView;
    @FXML private ListView availableSimulationsDetailsListView;

    public final static int REFRESH_RATE = 1000;
    private Timer fetchWorldsDetailsTimer;
    private FetchQueueManagementTimer fetchWorldsDetailsTimerTask;
    private QueueManagementDTO savedQueueManagementDTO;

    private AppController appController;
    @FXML public void initialize() {
        if (uploadComponentController != null) {
            uploadComponentController.setManagementController(this);
        }
        Platform.runLater(() -> {
            fetchWorldsDetailsTimer = new Timer();
            fetchWorldsDetailsTimerTask = new FetchQueueManagementTimer(appController.getClient(), this);
            fetchWorldsDetailsTimer.scheduleAtFixedRate(fetchWorldsDetailsTimerTask, 0, REFRESH_RATE);
        });
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void uploadWorldFromXML(File selectedFile) {
        try {
            appController.loadXML(selectedFile.getPath());
            uploadComponentController.setFileChosenStringProperty(selectedFile.toString());
            uploadComponentController.isXMLLoadedProperty().set(true);
            updateAvailableSimulationsDetails();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error loading XML file");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void updateAvailableSimulationsDetails() {
        if (availableSimulationsDetailsListView != null) {
            availableSimulationsDetailsListView.getItems().clear();
        }

    }

    @FXML
    void setThreadsCountButtonAction() {
        String threadsCount = setThreadsCountTextField.getText();
        try {
            appController.setThreadsCount(threadsCount);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    public UploadController getUploadComponentController() {
        return uploadComponentController;
    }

    public void updateQueueManagement(QueueManagementDTO queueManagementDTO) {
        if (savedQueueManagementDTO != null) {
            if (savedQueueManagementDTO.equals(queueManagementDTO)) {
                return;
            }
        }
        savedQueueManagementDTO = queueManagementDTO;
        threadPoolManagementListView.getItems().clear();
        Label label = new Label("Queue Management");
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-underline: true;");
        threadPoolManagementListView.getItems().add(label);
        threadPoolManagementListView.getItems().add("Pending: " + queueManagementDTO.getPending());
        threadPoolManagementListView.getItems().add("Active: " + queueManagementDTO.getActive());
        threadPoolManagementListView.getItems().add("Completed: " + queueManagementDTO.getCompleted());
    }

    public void showAlert(StatusDTO statusDTO) {
        appController.showAlert(statusDTO);
    }
}
