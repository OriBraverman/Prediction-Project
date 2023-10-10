package admin.allocations;

import admin.app.AppController;
import admin.tasks.FetchRequestsTimer;
import dto.RequestDTO;
import dto.RequestsDTO;
import dto.StatusDTO;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

import java.util.Timer;

public class AllocationsController {
    @FXML private TableView<RequestDTO> AllocationsTableView;
    @FXML private TableColumn<RequestDTO, HBox> buttonsColumn;
    @FXML private TableColumn<RequestDTO, String> statusColumn;
    @FXML private TableColumn<RequestDTO, Integer> requestIdColumn;
    @FXML private TableColumn<RequestDTO, String> userNameColumn;
    @FXML private TableColumn<RequestDTO, String> simulationNameColumn;
    @FXML private TableColumn<RequestDTO, Integer> threadsRequestedColumn;
    @FXML private TableColumn<RequestDTO, String> terminationSecondsColumn;
    @FXML private TableColumn<RequestDTO, String> terminationTicksColumn;
    @FXML private TableColumn<RequestDTO, String> terminationUserColumn;
    @FXML private TableColumn<RequestDTO, Integer> runningColumn;
    @FXML private TableColumn<RequestDTO, Integer> completedColumn;

    private AppController appController;

    public final static int REFRESH_RATE = 1000;
    private Timer fetchRequestsTimer;
    private FetchRequestsTimer fetchRequestsTimerTask;
    private RequestsDTO savedRequestsDTO;

    @FXML
    public void initialize() {
        buttonsColumn.setCellValueFactory(cellData -> {
            HBox hBox = new HBox();
            Button approveButton = new Button("Approve");
            Button rejectButton = new Button("Reject");
            approveButton.setOnAction(event -> {
                int requestID = cellData.getValue().getId();
                appController.updateRequestStatus(requestID, "ACCEPTED");
            });
            rejectButton.setOnAction(event -> {
                int requestID = cellData.getValue().getId();
                appController.updateRequestStatus(requestID, "REJECTED");
            });
            approveButton.visibleProperty().bind(Bindings.createBooleanBinding(() -> {
                String status = cellData.getValue().getStatus();
                return status.equals("PENDING");
            }));
            rejectButton.visibleProperty().bind(Bindings.createBooleanBinding(() -> {
                String status = cellData.getValue().getStatus();
                return status.equals("PENDING");
            }));
            hBox.getChildren().addAll(approveButton, rejectButton);
            return Bindings.createObjectBinding(() -> hBox);
        });
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
        threadsRequestedColumn.setCellValueFactory(cellData -> {
            int threadsRequested = cellData.getValue().getNumberOfExecutions();
            return Bindings.createObjectBinding(() -> threadsRequested);
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
        Platform.runLater(() -> {
            fetchRequestsTimer = new Timer();
            fetchRequestsTimerTask = new FetchRequestsTimer(appController.getClient(), this);
            fetchRequestsTimer.scheduleAtFixedRate(fetchRequestsTimerTask, 0, REFRESH_RATE);
        });
    }


    public void updateRequests(RequestsDTO requestsDTO) {
        savedRequestsDTO = requestsDTO;
        ObservableList<RequestDTO> requests = FXCollections.observableArrayList();
        requests.addAll(requestsDTO.getRequests());
        AllocationsTableView.setItems(requests);
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void showAlert(StatusDTO statusDTO) {
        appController.showAlert(statusDTO);
    }
}
