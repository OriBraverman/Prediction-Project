package user.execution.summary.tree;


import dto.world.PropertyDefinitionDTO;
import javafx.scene.control.TreeItem;

import java.util.List;
import java.util.Map;

public class EnvVariablesTreeItem extends TreeItem<String> {
    private Map<PropertyDefinitionDTO, String> envVariables;
    public EnvVariablesTreeItem(Map<PropertyDefinitionDTO, String> envVariables) {
        super("Environment Properties");
        this.envVariables = envVariables;
        envVariables.forEach((propertyDefinitionDTO, value) -> this.getChildren().add(new EnvVariableTreeItem(propertyDefinitionDTO, value)));
    }
}
