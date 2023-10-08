package admin.allocations;

import admin.app.AppController;
import admin.tasks.FetchQueueManagementTimer;
import dto.RequestsDTO;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.util.Timer;

public class AllocationsController {
    @FXML private TableView<String> AllocationsTableView;
    private AppController appController;

    public final static int REFRESH_RATE = 1000;
    private Timer fetchAllocationsTimer;
    private FetchQueueManagementTimer fetchAllocationsTimerTask;
    private RequestsDTO savedRequestsDTO;

    @FXML
    public void initialize() {
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }
}
