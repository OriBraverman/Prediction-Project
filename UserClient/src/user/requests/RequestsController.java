package user.requests;

import user.app.AppController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class RequestsController {
    @FXML private TableView<?> RequestsTableView;
    @FXML private TextField amountLabel;
    @FXML private Button executeRequestButton;
    @FXML private TextField simulationNameLabel;
    @FXML private Button submitRequestButton;
    @FXML private TextField terminationBySecondsLabel;
    @FXML private TextField terminationByTicksLabel;
    @FXML private CheckBox terminationUserCheckBox;

    private AppController appController;

    @FXML
    void executeRequestButtonAction(ActionEvent event) {

    }

    @FXML
    void submitRequestButtonAction(ActionEvent event) {

    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }
}
