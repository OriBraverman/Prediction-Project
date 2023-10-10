package user.execution.envVarible;

import dto.EnvVariableValueDTO;
import dto.EnvVariablesValuesDTO;
import dto.world.PropertyDefinitionDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TitledPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EnvVariableTitledManager {
    private Map<String, EnvVariableTitledCell> envVariableTitledCellMap;

    public EnvVariableTitledManager() {
        this.envVariableTitledCellMap = new HashMap<>();
    }

    public void addEnvVariableTitledCell(PropertyDefinitionDTO propertyDefinitionDTO) {
        envVariableTitledCellMap.put(propertyDefinitionDTO.getName(), new EnvVariableTitledCell(propertyDefinitionDTO));
    }

    public void removeAll() {
        envVariableTitledCellMap.clear();
    }


    public void updateEnvVariableTitledCells(List<PropertyDefinitionDTO> envVariables) {
        removeAll();
        envVariables.forEach(this::addEnvVariableTitledCell);
    }

    public ObservableList<TitledPane> getEnvVariableTitledPaneList() {
        return envVariableTitledCellMap.values().stream()
                .map(EnvVariableTitledCell::getContainer)
                .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
    }

    public List<EnvVariableValueDTO> getEnvVariablesDTOList() {
        return envVariableTitledCellMap.values().stream()
                .map(EnvVariableTitledCell::getEnvVariablesValuesDTO)
                .collect(Collectors.toList());
    }

    public void clearAll() {
        envVariableTitledCellMap.values().forEach(EnvVariableTitledCell::clear);
    }

    public void fillEnvVariablesInputVBox(EnvVariablesValuesDTO envVariablesValuesDTO) {
        envVariablesValuesDTO.getEnvVariablesValues().forEach(envVariableValueDTO -> {
            EnvVariableTitledCell envVariableTitledCell = envVariableTitledCellMap.get(envVariableValueDTO.getName());
            envVariableTitledCell.fill(envVariableValueDTO);
        });
    }
}
