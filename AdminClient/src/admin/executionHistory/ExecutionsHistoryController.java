package admin.executionHistory;

import admin.app.AppController;
import javafx.fxml.FXML;

public class ExecutionsHistoryController {
    private AppController appController;

    @FXML
    public void initialize() {
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }
}
