package user.execution.entity;

import dto.EntityPopulationDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityPopulationListManager {
    private Map<String, EntityPopulationListCell> entityPopulationListCellMap;

    public EntityPopulationListManager() {
        this.entityPopulationListCellMap = new HashMap<>();
    }

    public void addEntityPopulationListCell(EntityPopulationDTO entityPopulationDTO) {
        entityPopulationListCellMap.put(entityPopulationDTO.getName(), new EntityPopulationListCell(entityPopulationDTO));
    }

    public void updateEntityPopulationListCells(List<EntityPopulationDTO> entityPopulationDTOS) {
        removeAll();
        entityPopulationDTOS.forEach(this::addEntityPopulationListCell);
    }

    public void removeAll() {
        entityPopulationListCellMap.clear();
    }

    public void clearAll() {
        entityPopulationListCellMap.values().forEach(EntityPopulationListCell::clear);
    }

    public ObservableList<HBox> getEntityPopulationListCellList() {
        return entityPopulationListCellMap.values().stream()
                .map(EntityPopulationListCell::getContainer)
                .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
    }

    public List<EntityPopulationDTO> getEntityPopulationDTOList() {
        return entityPopulationListCellMap.values().stream()
                .map(EntityPopulationListCell::getEntityPopulationDTO)
                .collect(Collectors.toList());
    }


}
