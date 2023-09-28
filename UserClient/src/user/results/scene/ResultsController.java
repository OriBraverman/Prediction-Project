package user.results.scene;

import dtos.*;
import dtos.world.EntityDefinitionDTO;
import dtos.world.PropertyDefinitionDTO;
import dtos.world.WorldDTO;
import user.app.AppController;
import user.results.simulation.SimulationController;
import user.upload.UploadController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import simulation.SimulationExecutionDetails;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ResultsController {
    @FXML private AnchorPane resultsAnchorPane;
    @FXML private ListView<String> executionList;
    @FXML private SimulationController simulationComponentController;
    @FXML private AnchorPane simulationComponent;
    private ScheduledExecutorService executorService;
    private AppController appController;
    private boolean setActive = false;
    public void initialize(){
        ObservableList<String> items = FXCollections.observableArrayList();
        executionList.setItems(items);
        executionList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Initialize and schedule the executor service
        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(this::updateUI, 0, 200, TimeUnit.MILLISECONDS);
    }
    public void stopExecutorService() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    private void updateUI() {
        if (!setActive) {
            return;
        }
        if (appController.getSimulationListDTO() == null || appController.getSimulationListDTO().getSimulationsID().isEmpty()) {
            return;
        }
        Platform.runLater(() -> {
            updateSimulationComponent(false);
        });
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void resetExecutionList() {
        if (executionList.getItems() != null) {
            executionList.getItems().clear();
        }
    }
    @FXML
    public void onExecutionListClicked(MouseEvent event) {
        updateSimulationComponent(true);
    }

    public void updateSimulationComponent(Boolean isClicked) {
        String selectedItem = executionList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String[] split = selectedItem.split(" ");
            int id = Integer.parseInt(split[1]);
            SimulationExecutionDetailsDTO simulationExecutionDetailsDTO = appController.getSimulationExecutionDetailsDTO(id);
            simulationComponentController.updateSimulationComponent(simulationExecutionDetailsDTO);
            if (appController.isSimulationCompleted(id) && isClicked) {
                simulationComponentController.getInformationComponentController().updateInformationComponent(id);
            }
        }
    }
    public SimulationController getSimulationComponentController() {
        return simulationComponentController;
    }

    public void addSimulationToExecutionList(SimulationIDDTO simulationIDDTO) {
        executionList.getItems().add("Simulation " + simulationIDDTO.getSimulationId());
        executionList.getSelectionModel().selectLast();
    }

    public void setIsActive(boolean setActive) {
        this.setActive = setActive;
    }
}