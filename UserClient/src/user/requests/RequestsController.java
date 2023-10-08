package user.requests;

import dto.StatusDTO;
import javafx.beans.property.SimpleBooleanProperty;
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

    private SimpleBooleanProperty isTerminationByUser;

    public RequestsController() {
        isTerminationByUser = new SimpleBooleanProperty(false);
    }

    @FXML
    public void initialize() {
        terminationUserCheckBox.selectedProperty().bindBidirectional(isTerminationByUser);
        terminationBySecondsLabel.disableProperty().bind(isTerminationByUser);
        terminationByTicksLabel.disableProperty().bind(isTerminationByUser);
    }

    @FXML
    void executeRequestButtonAction(ActionEvent event) {

    }

    @FXML
    void submitRequestButtonAction(ActionEvent event) {
        String simulationName = simulationNameLabel.getText();
        String userName = appController.getUsername();
        boolean isTerminationByUser = terminationUserCheckBox.isSelected();
        int amount, terminationByTicks, terminationBySeconds;
        try {
            amount = Integer.parseInt(amountLabel.getText());
            if (isTerminationByUser) {
                terminationByTicks = -1;
                terminationBySeconds = -1;
            } else {
                terminationByTicks = Integer.parseInt(terminationByTicksLabel.getText());
                terminationBySeconds = Integer.parseInt(terminationBySecondsLabel.getText());
            }
        } catch (NumberFormatException e) {
            appController.showAlert(new StatusDTO(false, "Please enter a valid number"));
            return;
        }
        appController.getConnection().submitRequest(simulationName, amount, terminationByTicks, terminationBySeconds, isTerminationByUser);
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }
}
