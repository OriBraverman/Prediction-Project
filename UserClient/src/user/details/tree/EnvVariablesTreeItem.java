package user.details.tree;


import dto.world.PropertyDefinitionDTO;
import javafx.scene.control.TreeItem;

import java.util.List;

public class EnvVariablesTreeItem extends TreeItem<String> {
    private List<PropertyDefinitionDTO> envVariables;
    public EnvVariablesTreeItem(List<PropertyDefinitionDTO> envVariables) {
        super("Environment Properties");
        this.envVariables = envVariables;
        envVariables.forEach(envVariable -> this.getChildren().add(new PropertyItem(envVariable)));
    }
}
