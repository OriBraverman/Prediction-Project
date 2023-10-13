package user.requests;

import dto.RequestDTO;
import dto.RequestsDTO;
import dto.StatusDTO;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import user.app.AppController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import user.tasks.FetchRequestsTimer;

import java.util.Timer;

public class RequestsController {
    @FXML private TableView<RequestDTO> RequestsTableView;
    @FXML private TableColumn<RequestDTO, String> statusColumn;
    @FXML private TableColumn<RequestDTO, Integer> requestIdColumn;
    @FXML private TableColumn<RequestDTO, String> userNameColumn;
    @FXML private TableColumn<RequestDTO, String> simulationNameColumn;
    @FXML private TableColumn<RequestDTO, Integer> amountColumn;
    @FXML private TableColumn<RequestDTO, String> terminationSecondsColumn;
    @FXML private TableColumn<RequestDTO, String> terminationTicksColumn;
    @FXML private TableColumn<RequestDTO, String> terminationUserColumn;
    @FXML private TableColumn<RequestDTO, Integer> runningColumn;
    @FXML private TableColumn<RequestDTO, Integer> completedColumn;
    @FXML private TextField amountLabel;
    @FXML private Button executeRequestButton;
    @FXML private TextField simulationNameLabel;
    @FXML private Button submitRequestButton;
    @FXML private TextField terminationBySecondsLabel;
    @FXML private TextField terminationByTicksLabel;
    @FXML private CheckBox terminationUserCheckBox;

    private AppController appController;

    private SimpleBooleanProperty isTerminationByUser;
    public final static int REFRESH_RATE = 1000;
    private Timer fetchRequestsTimer;
    private FetchRequestsTimer fetchRequestsTimerTask;
    private RequestsDTO savedRequestsDTO;

    public RequestsController() {
        isTerminationByUser = new SimpleBooleanProperty(false);
    }

    @FXML
    public void initialize() {
        RequestsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        statusColumn.setCellValueFactory(cellData -> {
            String status = cellData.getValue().getStatus();
            return Bindings.createObjectBinding(() -> status);
        });
        requestIdColumn.setCellValueFactory(cellData -> {
            int requestId = cellData.getValue().getId();
            return Bindings.createObjectBinding(() -> requestId);
        });
        userNameColumn.setCellValueFactory(cellData -> {
            String userName = cellData.getValue().getUserName();
            return Bindings.createObjectBinding(() -> userName);
        });
        simulationNameColumn.setCellValueFactory(cellData -> {
            String simulationName = cellData.getValue().getWorldName();
            return Bindings.createObjectBinding(() -> simulationName);
        });
        amountColumn.setCellValueFactory(cellData -> {
            int amount = cellData.getValue().getNumberOfExecutions();
            return Bindings.createObjectBinding(() -> amount);
        });
        terminationSecondsColumn.setCellValueFactory(cellData -> {
            String terminationSeconds = cellData.getValue().getTermination().getSecondsCount() == -1 ? "N/E" : String.valueOf(cellData.getValue().getTermination().getSecondsCount());
            return Bindings.createObjectBinding(() -> terminationSeconds);
        });
        terminationTicksColumn.setCellValueFactory(cellData -> {
            String terminationTicks = cellData.getValue().getTermination().getTicksCount() == -1 ? "N/E" : String.valueOf(cellData.getValue().getTermination().getTicksCount());
            return Bindings.createObjectBinding(() -> terminationTicks);
        });
        terminationUserColumn.setCellValueFactory(cellData -> {
            String terminationUser = cellData.getValue().getTermination().isByUser() ? "Yes" : "No";
            return Bindings.createObjectBinding(() -> terminationUser);
        });
        runningColumn.setCellValueFactory(cellData -> {
            int running = cellData.getValue().getRunningExecutions();
            return Bindings.createObjectBinding(() -> running);
        });
        completedColumn.setCellValueFactory(cellData -> {
            int completed = cellData.getValue().getCompletedExecutions();
            return Bindings.createObjectBinding(() -> completed);
        });
        terminationUserCheckBox.selectedProperty().bindBidirectional(isTerminationByUser);
        terminationBySecondsLabel.disableProperty().bind(isTerminationByUser);
        terminationByTicksLabel.disableProperty().bind(isTerminationByUser);
        Platform.runLater(() -> {
            fetchRequestsTimer = new Timer();
            fetchRequestsTimerTask = new FetchRequestsTimer(appController.getConnection().getClient(), this);
            fetchRequestsTimer.scheduleAtFixedRate(fetchRequestsTimerTask, 0, REFRESH_RATE);
        });
    }

    @FXML
    void executeRequestButtonAction(ActionEvent event) {
        RequestDTO requestDTO = RequestsTableView.getSelectionModel().getSelectedItem();
        if (requestDTO == null) {
            appController.showAlert(new StatusDTO(false, "Please select a request"));
            return;
        }
        if (appController.getConnection().executeRequest(requestDTO.getId())) {
            appController.selectTab(AppController.Tab.EXECUTION);
        }
    }

    @FXML
    void submitRequestButtonAction(ActionEvent event) {
        String simulationName = simulationNameLabel.getText();
        String userName = appController.getUsername();
        boolean isTerminationByUser = terminationUserCheckBox.isSelected();
        int amount, terminationByTicks, terminationBySeconds;
        try {
            amount = Integer.parseInt(amountLabel.getText());
        } catch (NumberFormatException e) {
            appController.showAlert(new StatusDTO(false, "Please enter a valid number of threads"));
            return;
        }
        if (isTerminationByUser) {
            terminationByTicks = -1;
            terminationBySeconds = -1;
        } else {
            try {
                terminationByTicks = Integer.parseInt(terminationByTicksLabel.getText());
            } catch (NumberFormatException e) {
                appController.showAlert(new StatusDTO(false, "Please enter a valid number of ticks"));
                return;
            }
            try {
                terminationBySeconds = Integer.parseInt(terminationBySecondsLabel.getText());
            } catch (NumberFormatException e) {
                appController.showAlert(new StatusDTO(false, "Please enter a valid number of seconds"));
                return;
            }
        }
        appController.getConnection().submitRequest(simulationName, amount, terminationByTicks, terminationBySeconds, isTerminationByUser);
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void updateRequests(RequestsDTO requestsDTO) {
        // save the last req selected
        int selectedRequestID = RequestsTableView.getSelectionModel().getSelectedIndex();
        savedRequestsDTO = requestsDTO;
        ObservableList<RequestDTO> requests = FXCollections.observableArrayList();
        requests.addAll(requestsDTO.getRequests());
        RequestsTableView.setItems(requests);
        // select the last req selected
        RequestsTableView.getSelectionModel().select(selectedRequestID);
    }

    public void showAlert(StatusDTO statusDTO) {
        appController.showAlert(statusDTO);
    }
}
