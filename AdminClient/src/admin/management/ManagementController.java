package admin.management;

import admin.app.AppController;
import admin.upload.UploadController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class ManagementController {
    @FXML private HBox UploadComponent;
    @FXML private UploadController uploadComponentController;
    @FXML private Button SetThreadsCountButton;
    @FXML private ListView threadPoolManagementListView;
    @FXML private ListView availableSimulationsDetailsListView;

    private AppController appController;
    @FXML public void initialize(){
        if (uploadComponentController != null) {
            uploadComponentController.setAppController(appController);
        }
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    void setThreadsCountButtonAction() {
        //appController.setThreadsCount();
    }
}
