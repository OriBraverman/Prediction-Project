package admin.executionHistory.simulation;

import dto.SimulationExecutionDetailsDTO;
import dto.SimulationStateDTO;
import http.url.Constants;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import admin.app.AppController;
import admin.executionHistory.simulation.information.InformationController;
import admin.executionHistory.simulation.tableView.EntityPopulationTableView;


public class SimulationController {
    @FXML private Label entitiesCountDisplay;
    @FXML private Label currentTickDisplay;
    @FXML private Label timeSinceSimulationStartedDisplay;
    @FXML private Button rerunSimulationButton;
    @FXML private Button pauseSimulationButton;
    @FXML private Button resumeSimulationButton;
    @FXML private Button stopSimulationButton;
    @FXML private ScrollPane entityPopulationScrollPane;
    @FXML private Label statusDisplay;
    @FXML private Button terminationReasonButton;


    @FXML private InformationController informationComponentController;
    @FXML private AnchorPane informationComponent;
    private AppController appController;

    private SimpleIntegerProperty currentSimulationID;
    private SimpleIntegerProperty entitiesCount;
    private SimpleIntegerProperty currentTick;
    private SimpleLongProperty timeSinceSimulationStarted;
    private SimpleBooleanProperty isCompleted; // is the simulation entered the completed simulation queue
    private SimpleBooleanProperty isRunning;
    private SimpleBooleanProperty isPaused;
    private SimpleStringProperty status;
    private SimpleStringProperty terminationReason;

    public void initialize() {
        if (informationComponentController != null) {
            informationComponentController.setSimulationComponentController(this);
        }
        currentSimulationID = new SimpleIntegerProperty();
        entitiesCount = new SimpleIntegerProperty();
        currentTick = new SimpleIntegerProperty();
        timeSinceSimulationStarted = new SimpleLongProperty();
        isCompleted = new SimpleBooleanProperty(false);
        isRunning = new SimpleBooleanProperty(false);
        isPaused = new SimpleBooleanProperty(false);
        status = new SimpleStringProperty();
        terminationReason = new SimpleStringProperty();
        entitiesCountDisplay.textProperty().bind(entitiesCount.asString());
        currentTickDisplay.textProperty().bind(currentTick.asString());
        timeSinceSimulationStartedDisplay.textProperty().bind(timeSinceSimulationStarted.asString());

        // Simulation control buttons
        rerunSimulationButton.visibleProperty().bind(isCompleted);
        // See pause when running and not paused --> Disable pause when not running or paused
        pauseSimulationButton.disableProperty().bind(isRunning.not().or(isPaused));
        // See resume when running and paused --> Disable resume when not running or not paused
        resumeSimulationButton.disableProperty().bind(isRunning.not().or(isPaused.not()));
        stopSimulationButton.disableProperty().bind(isRunning.not());
        informationComponent.visibleProperty().bind(isCompleted);
        // See resume when running and paused --> Disable resume when not running or not paused
        terminationReasonButton.disableProperty().bind(isCompleted.not());
        statusDisplay.textProperty().bind(status);
    }
    public void setAppController(AppController appController) {
        this.appController = appController;
    }
    public void setEntitiesCountDisplay(String entitiesCountDisplayText) {
        this.entitiesCountDisplay.setText(entitiesCountDisplayText);
    }

    public void updateSimulationComponent(SimulationExecutionDetailsDTO simulationEDDTO) {
        currentSimulationID.set(simulationEDDTO.getId());
        entitiesCount.set(simulationEDDTO.getNumberOfEntities());
        currentTick.set(simulationEDDTO.getCurrentTick());
        timeSinceSimulationStarted.set(simulationEDDTO.getDurationInSeconds());
        isRunning.set(simulationEDDTO.isRunning());
        isPaused.set(simulationEDDTO.isPaused());
        isCompleted.set(simulationEDDTO.isCompleted());
        status.set(simulationEDDTO.getStatus());
        terminationReason.set(simulationEDDTO.getTerminationReason());
        EntityPopulationTableView epTableView = new EntityPopulationTableView();
        HBox entityPopulationHBox = new HBox();
        entityPopulationScrollPane.setContent(entityPopulationHBox);
        entityPopulationHBox.getChildren().clear();
        entityPopulationHBox.getChildren().addAll(epTableView.createEntityPopulationTableView(simulationEDDTO));
        if (isRunning.get() && informationComponentController.getExecutionResult().getChildren() != null) {
            informationComponentController.getExecutionResult().getChildren().clear();
        }
    }
    @FXML
    void pauseSimulationButtonAction(ActionEvent event) {
        appController.getConnection().setSimulationState(new SimulationStateDTO(currentSimulationID.get(), Constants.PAUSE));
    }

    @FXML
    void rerunSimulationButtonAction(ActionEvent event) {
        appController.selectTab(AppController.Tab.EXECUTION);
        appController.updateNewExecutionByPrevSimulation(currentSimulationID.get());
    }

    @FXML
    void resumeSimulationButtonAction(ActionEvent event) {
        appController.getConnection().setSimulationState(new SimulationStateDTO(currentSimulationID.get(), Constants.RESUME));
    }

    @FXML
    void stopSimulationButtonAction(ActionEvent event) {
        appController.getConnection().setSimulationState(new SimulationStateDTO(currentSimulationID.get(), Constants.STOP));
    }

    @FXML
    void terminationReasonButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Termination Reason");
        alert.setHeaderText("Termination Reason");
        alert.setContentText(terminationReason.get());
        alert.showAndWait();
    }

    public int getCurrentSimulationID() {
        return currentSimulationID.get();
    }

    public InformationController getInformationComponentController() {
        return informationComponentController;
    }

    public boolean getIsRunning() {
        return isRunning.get();
    }

    public SimpleBooleanProperty isRunningProperty() {
        return isRunning;
    }
}
