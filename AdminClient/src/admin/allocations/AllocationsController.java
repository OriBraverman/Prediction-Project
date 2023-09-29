package admin.allocations;

import admin.app.AppController;
import javafx.fxml.FXML;

public class AllocationsController {
    private AppController appController;

    @FXML
    public void initialize() {
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }
}
