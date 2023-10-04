package admin.management.scene;

import admin.app.AppController;
import admin.management.tree.OpenableItem;
import admin.management.tree.WorldsDetailsItem;
import admin.tasks.FetchQueueManagementTimer;
import admin.tasks.FetchWorldsDetailsTimer;
import admin.upload.UploadController;
import dto.QueueManagementDTO;
import dto.StatusDTO;
import dto.world.WorldDTO;
import dto.world.WorldsDTO;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
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
    @FXML private TreeView<String> detailsTreeView;
    @FXML private TreeView<String> fullInfoTree;

    public final static int REFRESH_RATE = 1000;
    private Timer fetchQueueManagementTimer;
    private FetchQueueManagementTimer fetchQueueManagementTimerTask;
    private QueueManagementDTO savedQueueManagementDTO;
    private Timer fetchWorldsDetailsTimer;
    private FetchWorldsDetailsTimer fetchWorldsDetailsTimerTask;

    private AppController appController;
    @FXML public void initialize() {
        if (uploadComponentController != null) {
            uploadComponentController.setManagementController(this);
        }
        Platform.runLater(() -> {
            fetchQueueManagementTimer = new Timer();
            fetchQueueManagementTimerTask = new FetchQueueManagementTimer(appController.getClient(), this);
            fetchQueueManagementTimer.scheduleAtFixedRate(fetchQueueManagementTimerTask, 0, REFRESH_RATE);
            fetchWorldsDetailsTimer = new Timer();
            fetchWorldsDetailsTimerTask = new FetchWorldsDetailsTimer(this, appController.getClient());
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
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error loading XML file");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
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
        threadPoolManagementListView.getItems().add("Capacity: " + queueManagementDTO.getCapacity());
        threadPoolManagementListView.getItems().add("Pending: " + queueManagementDTO.getPending());
        threadPoolManagementListView.getItems().add("Active: " + queueManagementDTO.getActive());
        threadPoolManagementListView.getItems().add("Completed: " + queueManagementDTO.getCompleted());
    }

    public void showAlert(StatusDTO statusDTO) {
        appController.showAlert(statusDTO);
    }

    public void updateDetailsTreeView(WorldsDTO worldsDTO) {
        if (detailsTreeView != null && detailsTreeView.getRoot() instanceof WorldsDetailsItem) {
            WorldsDetailsItem worldsDetailsItem = (WorldsDetailsItem) detailsTreeView.getRoot();
            if (sameWorlds(worldsDetailsItem.getWorldsDTO(), worldsDTO)) {
                return;
            }
        }
        TreeItem<String> root = new WorldsDetailsItem(worldsDTO);
        detailsTreeView.setRoot(root);
        detailsTreeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        detailsTreeView.setCellFactory(param -> new TreeCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(item);

                selectedProperty().addListener(e -> {
                    if (((ReadOnlyBooleanProperty)e).getValue()) {
                        if (getTreeItem() instanceof OpenableItem) {
                            OpenableItem openableItem = (OpenableItem) getTreeItem();
                            TreeItem<String> root = openableItem.getFullView();
                            fullInfoTree.setRoot(root);
                        }
                    }
                });
            }
        });
    }

    private boolean sameWorlds(WorldsDTO worldsDTO1, WorldsDTO worldsDTO2) {
        if (worldsDTO1.getWorlds().size() != worldsDTO2.getWorlds().size()) {
            return false;
        }
        if (worldsDTO1.getWorlds().size() == 0 && worldsDTO2.getWorlds().size() == 0) {
            return true;
        }
        for (WorldDTO worldDTO1 : worldsDTO1.getWorlds()) {
            boolean found = false;
            for (WorldDTO worldDTO2 : worldsDTO2.getWorlds()) {
                if (worldDTO1.getName().equals(worldDTO2.getName())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }
}
