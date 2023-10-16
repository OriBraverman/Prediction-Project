package user.execution.summary.tree;

import dto.world.PropertyDefinitionDTO;
import javafx.scene.control.TreeItem;
import user.details.tree.OpenableItem;

public class EnvVariableTreeItem extends TreeItem<String> {
    private PropertyDefinitionDTO propertyDefinitionDTO;
    public EnvVariableTreeItem(PropertyDefinitionDTO propertyDefinitionDTO, String value) {
        super(propertyDefinitionDTO.getName());
        this.propertyDefinitionDTO = propertyDefinitionDTO;
        this.getChildren().add(new TreeItem<>("Name: " + propertyDefinitionDTO.getName()));
        this.getChildren().add(new TreeItem<>("Type: " + propertyDefinitionDTO.getType()));
        if (propertyDefinitionDTO.getFromRange() != null) {
            this.getChildren().add(new TreeItem<>("From Range: " + propertyDefinitionDTO.getFromRange()));
            this.getChildren().add(new TreeItem<>("To Range: " + propertyDefinitionDTO.getToRange()));
        }
        this.getChildren().add(new TreeItem<>("Value: " + value));
        this.setExpanded(true);
    }
}
