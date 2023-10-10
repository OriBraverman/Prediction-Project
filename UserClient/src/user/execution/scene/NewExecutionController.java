package user.execution.scene;

import dto.*;
import dto.world.PropertyDefinitionDTO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import user.app.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import user.execution.entity.EntityPopulationListManager;
import user.execution.envVarible.EnvVariableTitledManager;

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
    private SimpleStringProperty numberOfExecutionsLeft;

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
        numberOfExecutionsLeft = new SimpleStringProperty();
        entityPopulationListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        requestIDLabel.textProperty().bind(requestID.asString());
        worldNameLabel.textProperty().bind(worldName);
        numberOfExecutionsLeftLabel.textProperty().bind(numberOfExecutionsLeft);
    }

    public void updateEnvVariablesInputVBox(List<PropertyDefinitionDTO> envVariables) {
        envVariableTitledManager.updateEnvVariableTitledCells(envVariables);
        ObservableList<TitledPane> envVariableTitledPaneList = envVariableTitledManager.getEnvVariableTitledPaneList();
        envVariablesAccordion.getPanes().addAll(envVariableTitledPaneList);
    }

    public void updateEntityPopulationInputVBox(List<EntityPopulationDTO> entityPopulationDTOS) {
        entityPopulationListManager.updateEntityPopulationListCells(entityPopulationDTOS);
        ObservableList<HBox> entityPopulationListCellList = entityPopulationListManager.getEntityPopulationListCellList();
        entityPopulationListView.setItems(entityPopulationListCellList);
    }

    @FXML
    void startSimulationButtonAction(ActionEvent event) {
        int requestID = this.requestID.get();
        String worldName = this.worldName.get();
        EnvVariablesValuesDTO envVariablesValuesDTO = getEnvVariablesDTOS(worldName);
        EntitiesPopulationDTO entityPopulationDTO = getEntityPopulationDTOS(worldName);
        if (envVariablesValuesDTO == null || entityPopulationDTO == null) {
            return;
        }
        ActivateSimulationDTO activateSimulationDTO = new ActivateSimulationDTO(requestID, envVariablesValuesDTO, entityPopulationDTO);
        appController.getConnection().activateSimulation(activateSimulationDTO);
        appController.tabActivation(AppController.Tab.RESULTS, true);
        appController.selectTab(AppController.Tab.RESULTS);
    }

    private EntitiesPopulationDTO getEntityPopulationDTOS(String worldName) {
        List<EntityPopulationDTO> entityPopulationDTOS = entityPopulationListManager.getEntityPopulationDTOList();
        EntitiesPopulationDTO entitiesPopulationDTO = new EntitiesPopulationDTO(entityPopulationDTOS);
        try {
            appController.validateEntitiesPopulation(worldName, entitiesPopulationDTO);
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid value");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return null;
        }
        return new EntitiesPopulationDTO(entityPopulationDTOS);
    }

    private EnvVariablesValuesDTO getEnvVariablesDTOS(String worldName) {
        List<EnvVariableValueDTO> envVariablesDTOS = envVariableTitledManager.getEnvVariablesDTOList();
        EnvVariablesValuesDTO envVariablesValuesDTO = new EnvVariablesValuesDTO(envVariablesDTOS);
        try {
            appController.validateEnvVariablesValues(worldName, envVariablesValuesDTO);
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid value");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return null;
        }
        return new EnvVariablesValuesDTO(envVariablesDTOS);
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
        numberOfExecutionsLeft.set(String.valueOf(newExecutionInputDTO.getRequestDTO().getNumberOfExecutionsLeft()));
        updateEnvVariablesInputVBox(newExecutionInputDTO.getEnvVariables());
        updateEntityPopulationInputVBox(newExecutionInputDTO.getEntityPopulations());
    }
}
