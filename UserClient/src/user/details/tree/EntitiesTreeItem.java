package user.details.tree;

import dto.world.EntityDefinitionDTO;
import javafx.scene.control.TreeItem;

import java.util.List;

public class EntitiesTreeItem extends TreeItem<String> {
    private List<EntityDefinitionDTO> entities;
    public EntitiesTreeItem(List<EntityDefinitionDTO> entities) {
        super("Entities");
        this.entities = entities;
        entities.forEach(entity -> this.getChildren().add(new EntityTreeItem(entity)));

    }
}
