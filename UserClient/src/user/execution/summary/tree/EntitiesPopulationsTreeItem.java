package user.execution.summary.tree;

import dto.world.EntityDefinitionDTO;
import javafx.scene.control.TreeItem;

import java.util.List;
import java.util.Map;

public class EntitiesPopulationsTreeItem extends TreeItem<String>{
    private Map<String, Integer> entitiesPopulations;
    public EntitiesPopulationsTreeItem(Map<String, Integer> entitiesPopulations) {
        super("Entities Populations:");
        this.entitiesPopulations = entitiesPopulations;
        entitiesPopulations.forEach((entityName, population) -> {
            this.getChildren().add(new TreeItem<>("Entity: " + entityName + ", Population: " + population));
        });
    }

}

