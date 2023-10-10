package user.execution.entity;

import dto.EntityPopulationDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class EntityPopulationListCell {
    private final HBox container = new HBox();
    private final CheckBox checkBox = new CheckBox("");
    private final Label entityLabel = new Label("Entity: ");
    private final Label entityNameLabel = new Label();
    private final TextField textField = new TextField();

    private SimpleStringProperty entityName = new SimpleStringProperty();

    public EntityPopulationListCell(EntityPopulationDTO entityPopulationDTO) {
        //get the cell data
        entityNameLabel.textProperty().bind(entityName);
        entityNameLabel.setPadding(new javafx.geometry.Insets(0, 10, 0, 0));
        textField.disableProperty().bind(checkBox.selectedProperty().not());
        textField.visibleProperty().bind(checkBox.selectedProperty());
        container.getChildren().addAll(checkBox, entityLabel, entityNameLabel, textField);
        updateItem(entityPopulationDTO);
    }

    protected void updateItem(EntityPopulationDTO item) {
        entityName.set(item.getName());
        checkBox.setSelected(item.hasValue());
        if (item.hasValue()) {
            textField.setText(item.getPopulation());
        }
    }

    public void clear() {
        checkBox.setSelected(false);
        textField.setText("");
    }

    public HBox getContainer() {
        return container;
    }

    public EntityPopulationDTO getEntityPopulationDTO() {
        return new EntityPopulationDTO(entityName.get(), textField.getText(), checkBox.isSelected());
    }
}


