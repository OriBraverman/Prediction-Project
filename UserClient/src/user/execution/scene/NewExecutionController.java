package user.execution.scene;

import dto.*;
import dto.world.PropertyDefinitionDTO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import user.UserApplication;
import user.app.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import user.execution.entity.EntityPopulationListManager;
import user.execution.envVarible.EnvVariableTitledManager;
import user.execution.summary.ExecutionSummaryController;

import java.io.IOException;
import java.util.List;

public class NewExecutionController {
    @FXML private Accordion envVariablesAccordion;
    @FXML private ListView<HBox> entityPopulationListView;
    @FXML private Button clearSimulationButton;
    @FXML private Button startSimulationButton;
    @FXML private Label requestIDLabel;
    @FXML private Label worldNameLabel;
    @FXML private Label numberOfExecutionsLeftLabel;

    private AppController appController;

    private EntityPopulationListManager entityPopulationListManager;
    private EnvVariableTitledManager envVariableTitledManager;
    private SimpleIntegerProperty requestID;
    private SimpleStringProperty worldName;
    private SimpleIntegerProperty numberOfExecutionsLeft;

    public NewExecutionController() {
        entityPopulationListManager = new EntityPopulationListManager();
        envVariableTitledManager = new EnvVariableTitledManager();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML public void initialize(){
        requestID = new SimpleIntegerProperty();
        worldName = new SimpleStringProperty();
        numberOfExecutionsLeft = new SimpleIntegerProperty();
        entityPopulationListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        requestIDLabel.textProperty().bind(requestID.asString());
        worldNameLabel.textProperty().bind(worldName);
        numberOfExecutionsLeftLabel.textProperty().bind(numberOfExecutionsLeft.asString());
    }

    public void updateEnvVariablesInputVBox(List<PropertyDefinitionDTO> envVariables) {
        envVariableTitledManager.updateEnvVariableTitledCells(envVariables);
        ObservableList<TitledPane> envVariableTitledPaneList = envVariableTitledManager.getEnvVariableTitledPaneList();
        if (!envVariablesAccordion.getPanes().isEmpty()) {
            envVariablesAccordion.getPanes().clear();
        }
        envVariablesAccordion.getPanes().addAll(envVariableTitledPaneList);
    }

    public void updateEntityPopulationInputVBox(List<EntityPopulationDTO> entityPopulationDTOS) {
        entityPopulationListManager.updateEntityPopulationListCells(entityPopulationDTOS);
        ObservableList<HBox> entityPopulationListCellList = entityPopulationListManager.getEntityPopulationListCellList();
        entityPopulationListView.setItems(entityPopulationListCellList);
    }

    /*@FXML
    void startSimulationButtonAction(ActionEvent event) {
        if (this.numberOfExecutionsLeft.get() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No more executions left");
            alert.setContentText("You have no more executions left for this simulation");
            alert.showAndWait();
            return;
        }
        int requestID = this.requestID.get();
        EnvVariablesValuesDTO envVariablesValuesDTO = new EnvVariablesValuesDTO(envVariableTitledManager.getEnvVariablesDTOList());
        EntitiesPopulationDTO entityPopulationDTO = new EntitiesPopulationDTO(entityPopulationListManager.getEntityPopulationDTOList());
        ActivateSimulationDTO activateSimulationDTO = new ActivateSimulationDTO(requestID, envVariablesValuesDTO, entityPopulationDTO);
        // The validation of the input is done in activateSimulation function
        SimulationIDDTO simulationIDDTO = appController.getConnection().activateSimulation(activateSimulationDTO);
        appController.getResultsController().addAndSelectToSimulationList(simulationIDDTO.getSimulationId());
        this.numberOfExecutionsLeft.set(this.numberOfExecutionsLeft.get() - 1);
        appController.tabActivation(AppController.Tab.RESULTS, true);
        appController.selectTab(AppController.Tab.RESULTS);
    }*/

    //shachar
    @FXML
    void startSimulationButtonAction(ActionEvent event) {
        if (this.numberOfExecutionsLeft.get() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No more executions left");
            alert.setContentText("You have no more executions left for this simulation");
            alert.showAndWait();
            return;
        }
        int requestID = this.requestID.get();
        EnvVariablesValuesDTO envVariablesValuesDTO = new EnvVariablesValuesDTO(envVariableTitledManager.getEnvVariablesDTOList());
        EntitiesPopulationDTO entityPopulationDTO = new EntitiesPopulationDTO(entityPopulationListManager.getEntityPopulationDTOList());
        ActivateSimulationDTO activateSimulationDTO = new ActivateSimulationDTO(requestID, envVariablesValuesDTO, entityPopulationDTO);
        // The validation of the input is done in activateSimulation function
        //ExecutionSummaryDTO executionSummaryDTO = appController.getConnection().getExecutionSummaryDTO(activateSimulationDTO);
        executionSummaryComponentLuncher(executionSummaryDTO);
    }

    public void executionSummaryComponentLuncher(ExecutionSummaryDTO executionSummaryDTO){
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("summary/executionSummary.fxml"));
            Parent root = loader.load();

            // Get the controller instance from the loader
            ExecutionSummaryController executionSummaryController = loader.getController();

            // Set the appController
            executionSummaryController.setAppController(appController);

            // Set the current simulation ID
            executionSummary.setCurrentSimulationID(currentSimulationID.get());

            // Create a new scene using the loaded FXML
            Scene scene = new Scene(root);

            // Create a new stage (window) and set the scene
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Execution Summary View");
            stage.getIcons().add(new Image(NewExecutionController.class.getResourceAsStream("summary/prediction-icon-2.png")));

            // Make the stage modal (blocks interaction with the main window)
            stage.initModality(Modality.WINDOW_MODAL);

            // Set the main stage as the owner of the new stage (adjust the reference as needed)
            stage.initOwner(UserApplication.getStage());

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any potential exceptions that may occur during loading
        }
    }


    @FXML
    void clearSimulationButtonAction(ActionEvent event) {
        envVariableTitledManager.clearAll();
        entityPopulationListManager.clearAll();
    }

    public void fillEnvVariablesInputVBox(EnvVariablesValuesDTO envVariablesValuesDTO) {
        envVariableTitledManager.fillEnvVariablesInputVBox(envVariablesValuesDTO);
    }


    public void fillEntityPopulationInputVBox(EntitiesPopulationDTO entityPopulationDTO) {
        updateEntityPopulationInputVBox(entityPopulationDTO.getEntitiesPopulation());
    }

    public void updateExecution(NewExecutionInputDTO newExecutionInputDTO) {
        requestID.set(newExecutionInputDTO.getRequestDTO().getId());
        worldName.set(newExecutionInputDTO.getRequestDTO().getWorldName());
        numberOfExecutionsLeft.set(newExecutionInputDTO.getRequestDTO().getNumberOfExecutionsLeft());
        updateEnvVariablesInputVBox(newExecutionInputDTO.getEnvVariables());
        updateEntityPopulationInputVBox(newExecutionInputDTO.getEntityPopulations());
    }
}
