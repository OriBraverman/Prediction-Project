package user.details.tree;

import dto.world.EntityDefinitionDTO;
import dto.world.PropertyDefinitionDTO;
import javafx.scene.control.TreeItem;

import java.util.List;

public class EntityTreeItem extends TreeItem<String> {
    private EntityDefinitionDTO entity;
    private List<PropertyDefinitionDTO> properties;
    public EntityTreeItem(EntityDefinitionDTO entity) {
        super(entity.getName());
        this.entity = entity;
        properties = entity.getProperties();
        properties.forEach(property -> this.getChildren().add(new PropertyItem(property)));
    }
}
