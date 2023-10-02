package user.details.tree;

import dto.world.WorldDTO;
import javafx.scene.control.TreeItem;

public class WorldDetailsItem extends TreeItem<String> {
    private WorldDTO worldDTO;
    private TreeItem<String> envVariables;
    private TreeItem<String> entities;
    private TreeItem<String> rules;
    private TreeItem<String> grid;

    public WorldDetailsItem(WorldDTO worldDTO) {
        super("World name: " + worldDTO.getName());
        this.worldDTO = worldDTO;
        envVariables = new EnvVariablesTreeItem(worldDTO.getEnvironment());
        entities = new EntitiesTreeItem(worldDTO.getEntities());
        rules = new RulesTreeItem(worldDTO.getRules());
        grid = new TreeItem<>("Grid: Height: " + worldDTO.getGridHeight() + ", Width: " + worldDTO.getGridWidth());
        setExpanded(true);
        this.getChildren().addAll(envVariables, entities, rules, grid);
    }
}
