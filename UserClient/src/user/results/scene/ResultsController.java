package user.results.scene;

import dto.*;
import dto.world.EntityDefinitionDTO;
import dto.world.PropertyDefinitionDTO;
import dto.world.WorldDTO;
import user.app.AppController;
import user.results.simulation.SimulationController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import user.tasks.FetchRequestsTimer;
import user.tasks.FetchSimulationListTimer;

import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ResultsController {
    @FXML private AnchorPane resultsAnchorPane;
    @FXML private ListView<Integer> executionList;
    @FXML private SimulationController simulationComponentController;
    @FXML private AnchorPane simulationComponent;

    private AppController appController;
    private ExecutionListManager executionListManager;
    public final static int REFRESH_RATE = 1000;
    private Timer fetchSimulationListTimer;
    private FetchSimulationListTimer fetchSimulationListTimerTask;
    private SimulationIDListDTO savedSimulationIDListDTO;
    private boolean setActive = false;
    public void initialize(){
        executionListManager = new ExecutionListManager();
        executionList.setCellFactory(param -> new ExecutionListCell());
        ObservableList<Integer> items = FXCollections.observableArrayList();
        executionList.setItems(items);
        executionList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        Platform.runLater(() -> {
            fetchSimulationListTimer = new Timer();
            fetchSimulationListTimerTask = new FetchSimulationListTimer(appController.getConnection().getClient(), this);
            fetchSimulationListTimer.schedule(fetchSimulationListTimerTask, 0, REFRESH_RATE);
        });
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    public void onExecutionListClicked(MouseEvent event) {
        Integer selectedID = executionList.getSelectionModel().getSelectedItem();
        if (selectedID != null) {
            appController.getConnection().getSimulationExecutionDetails(selectedID);
        }

    }

    public void updateSimulationComponent(SimulationExecutionDetailsDTO sedDTO) {
        simulationComponentController.updateSimulationComponent(sedDTO);
        if (sedDTO.isCompleted()) {
            simulationComponentController.getInformationComponentController().updateInformationComponent(sedDTO.getId());
        }
    }
    public SimulationController getSimulationComponentController() {
        return simulationComponentController;
    }

    public int getMaxExecutionID() {
        return executionListManager.getMaxExecutionID();
    }

    public void showAlert(StatusDTO statusDTO) {
        appController.showAlert(statusDTO);
    }

    public void updateSimulationList(SimulationIDListDTO simulationIDListDTO) {
        ObservableList<Integer> items = executionListManager.addAll(simulationIDListDTO.getSimulationsID());
        executionList.getItems().addAll(items);
    }
}