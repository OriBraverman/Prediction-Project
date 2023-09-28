package user.execution.scene;

import dtos.*;
import dtos.world.EntityDefinitionDTO;
import dtos.world.PropertyDefinitionDTO;
import user.app.AppController;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class NewExecutionController {
    @FXML private Accordion envVariablesAccordion;
    @FXML private ListView entityPopulationListView;
    @FXML private Button clearSimulationButton;
    @FXML private Button startSimulationButton;
    @FXML private CheckBox isBonusActivatedCheckBox;
    private AppController appController;

    private SimpleBooleanProperty isBonusActivated;
    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML public void initialize(){
        isBonusActivated = new SimpleBooleanProperty(false);
        entityPopulationListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        isBonusActivatedCheckBox.selectedProperty().bindBidirectional(isBonusActivated);
    }

    public void updateEnvVariablesInputVBox(NewExecutionInputDTO newExecutionInputDTO) {
        if (envVariablesAccordion.getPanes() != null) {
            envVariablesAccordion.getPanes().clear();
        }
        for (PropertyDefinitionDTO propertyDefinitionDTO : newExecutionInputDTO.getEnvVariables()) {
            envVariablesAccordion.getPanes().add(createEnvVarTitledPane(propertyDefinitionDTO));
        }
    }

    public void updateEntityPopulationInputVBox(NewExecutionInputDTO newExecutionInputDTO) {
        if (entityPopulationListView.getItems() != null) {
            entityPopulationListView.getItems().clear();
        }
        newExecutionInputDTO.getEntityDefinitions().forEach(entityDefinitionDTO -> {
            // each list item is in the format: Entity: <entity name> [checkbox] [text field](optional)
            HBox itemContainer = new HBox(); // Container for CheckBox and TextField

            CheckBox checkBox = new CheckBox("");
            itemContainer.getChildren().add(checkBox);

            Label entityLabel = new Label("Entity: ");
            itemContainer.getChildren().add(entityLabel);

            Label entityNameLabel = new Label(entityDefinitionDTO.getName());
            itemContainer.getChildren().add(entityNameLabel);

            Label spaceLabel = new Label(" ");
            itemContainer.getChildren().add(spaceLabel);

            // Create a TextField for each CheckBox
            TextField textField = new TextField();
            textField.setDisable(true); // Initially, the TextField is disabled
            textField.setVisible(false); // Initially, the TextField is not visible
            itemContainer.getChildren().add(textField);

            // Add listener to the CheckBox to enable and show/hide the TextField
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                textField.setDisable(!newValue); // Enable/disable TextField
                textField.setVisible(newValue);     // Show/hide TextField
            });

            // Add the itemContainer (HBox) to the ListView
            entityPopulationListView.getItems().add(itemContainer);
        });
    }

    public TitledPane createEnvVarTitledPane(PropertyDefinitionDTO propertyDefinitionDTO) {
        TitledPane titledPane = new TitledPane();
        titledPane.setText(propertyDefinitionDTO.getName());
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(401.0, 232.0);

        CheckBox checkBox = new CheckBox("Want to add value?");
        checkBox.setLayoutX(64.0);
        checkBox.setLayoutY(185.0);

        TextField textField = new TextField();
        textField.setLayoutX(201.0);
        textField.setLayoutY(181.0);

        // bind the text field to the checkbox
        textField.disableProperty().bind(checkBox.selectedProperty().not());

        Label prevName = new Label("Name:");
        prevName.setLayoutX(68.0);
        prevName.setLayoutY(52.0);

        Label propertyName = new Label(propertyDefinitionDTO.getName());
        propertyName.setLayoutX(137.0);
        propertyName.setLayoutY(52.0);

        Label typeLabel = new Label("Type:");
        typeLabel.setLayoutX(72.0);
        typeLabel.setLayoutY(85.0);

        Label typeValueLabel = new Label(propertyDefinitionDTO.getType().toString());
        typeValueLabel.setLayoutX(137.0);
        typeValueLabel.setLayoutY(85.0);

        if (propertyDefinitionDTO.getFromRange() != null) {
            Label rangeLabel = new Label("Range:");
            rangeLabel.setLayoutX(72.0);
            rangeLabel.setLayoutY(116.0);

            Label fromLabel = new Label("From");
            fromLabel.setLayoutX(137.0);
            fromLabel.setLayoutY(116.0);

            Label fromValueLabel = new Label(propertyDefinitionDTO.getFromRange().toString());
            fromValueLabel.setLayoutX(187.0);
            fromValueLabel.setLayoutY(116.0);

            Label toLabel = new Label("To");
            toLabel.setLayoutX(240.0);
            toLabel.setLayoutY(116.0);

            Label toValueLabel = new Label(propertyDefinitionDTO.getToRange().toString());
            toValueLabel.setLayoutX(286.0);
            toValueLabel.setLayoutY(116.0);

            anchorPane.getChildren().addAll(
                    propertyName, checkBox, textField, prevName, typeLabel, rangeLabel,
                    fromLabel, fromValueLabel, toLabel, toValueLabel,
                    typeValueLabel
            );
        } else {
            anchorPane.getChildren().addAll(
                    propertyName, checkBox, textField, prevName, typeLabel,
                    typeValueLabel
            );
        }

        titledPane.setContent(anchorPane);
        return titledPane;
    }

    @FXML
    void startSimulationAction(ActionEvent event) {
        int worldID = getWorldID();
        EnvVariablesValuesDTO envVariablesValuesDTO = getEnvVariablesDTOS(worldID);
        EntitiesPopulationDTO entityPopulationDTO = getEntityPopulationDTOS(worldID);
        if (envVariablesValuesDTO == null || entityPopulationDTO == null) {
            return;
        }
        appController.activateSimulation(worldID, envVariablesValuesDTO, entityPopulationDTO, isBonusActivated.get());
        appController.selectTab(AppController.Tab.RESULTS);
    }

    private int getWorldID() {
        return 0;//todo: get world id from somewhere
    }

    private EntitiesPopulationDTO getEntityPopulationDTOS(int worldID) {
        List<EntityPopulationDTO> entityPopulationDTOS = new ArrayList<>();
        for (Object item : entityPopulationListView.getItems()) {
            HBox itemContainer = (HBox) item;
            String entityName = ((Label) itemContainer.getChildren().get(2)).getText();
            CheckBox checkBox = (CheckBox) itemContainer.getChildren().get(0);
            if (checkBox.isSelected()) {
                TextField textField = (TextField) itemContainer.getChildren().get(4);
                entityPopulationDTOS.add(new EntityPopulationDTO(entityName, textField.getText(), true));
            } else {
                entityPopulationDTOS.add(new EntityPopulationDTO(checkBox.getText(), "", false));
            }
        }
        EntitiesPopulationDTO entitiesPopulationDTO = new EntitiesPopulationDTO(entityPopulationDTOS);
        try {
            appController.validateEntitiesPopulation(worldID, entitiesPopulationDTO);
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

    private EnvVariablesValuesDTO getEnvVariablesDTOS(int worldID) {
        EnvVariableValueDTO[] envVariablesDTOS = new EnvVariableValueDTO[envVariablesAccordion.getPanes().size()];
        for (int i = 0; i < envVariablesAccordion.getPanes().size(); i++) {
            TitledPane titledPane = envVariablesAccordion.getPanes().get(i);
            AnchorPane anchorPane = (AnchorPane) titledPane.getContent();
            CheckBox checkBox = (CheckBox) anchorPane.getChildren().get(1);
            EnvVariableValueDTO envVariableValueDTO;
            if (checkBox.isSelected()) {
                TextField textField = (TextField) anchorPane.getChildren().get(2);
                envVariableValueDTO = new EnvVariableValueDTO(titledPane.getText(), textField.getText(), true);
                try {
                    appController.validateEnvVariableValue(worldID, envVariableValueDTO);
                } catch (IllegalArgumentException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid value");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                    return null;
                }
            } else {
                envVariableValueDTO = new EnvVariableValueDTO(titledPane.getText(), "", false);
            }
            envVariablesDTOS[i] = envVariableValueDTO;
        }
        return new EnvVariablesValuesDTO(envVariablesDTOS);
    }

    @FXML
    void clearSimulationButtonAction(ActionEvent event) {
        for (TitledPane titledPane : envVariablesAccordion.getPanes()) {
            AnchorPane anchorPane = (AnchorPane) titledPane.getContent();
            CheckBox checkBox = (CheckBox) anchorPane.getChildren().get(0);
            checkBox.setSelected(false);
            TextField textField = (TextField) anchorPane.getChildren().get(2);
            textField.setText("");
        }
        for (Object item : entityPopulationListView.getItems()) {
            HBox itemContainer = (HBox) item;
            CheckBox checkBox = (CheckBox) itemContainer.getChildren().get(0);
            checkBox.setSelected(false);
            TextField textField = (TextField) itemContainer.getChildren().get(1);
            textField.setText("");
        }
    }

    public void fillEnvVariablesInputVBox(EnvVariablesValuesDTO envVariablesValuesDTO) {
        for (TitledPane titledPane : envVariablesAccordion.getPanes()) {
            AnchorPane anchorPane = (AnchorPane) titledPane.getContent();
            String envVariableName = ((Label)anchorPane.getChildren().get(0)).getText();
            CheckBox checkBox = (CheckBox) anchorPane.getChildren().get(1);
            TextField textField = (TextField) anchorPane.getChildren().get(2);
            for (EnvVariableValueDTO envVariableValueDTO : envVariablesValuesDTO.getEnvVariablesValues()) {
                if (envVariableName.equals(envVariableValueDTO.getName())) {
                    checkBox.setSelected(envVariableValueDTO.hasValue());
                    textField.setText(envVariableValueDTO.getValue());
                }
            }
        }
    }


    public void fillEntityPopulationInputVBox(EntitiesPopulationDTO entityPopulationDTO) {
        for (Object item : entityPopulationListView.getItems()) {
            HBox itemContainer = (HBox) item;
            String entityName = ((Label) itemContainer.getChildren().get(2)).getText();
            CheckBox checkBox = (CheckBox) itemContainer.getChildren().get(0);
            TextField textField = (TextField) itemContainer.getChildren().get(4);
            for (EntityPopulationDTO entityPopulation : entityPopulationDTO.getEntitiesPopulation()) {
                if (entityName.equals(entityPopulation.getName())) {
                    checkBox.setSelected(entityPopulation.hasValue());
                    textField.setText(entityPopulation.getPopulation());
                }
            }
        }
    }
}
