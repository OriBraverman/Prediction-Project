package user.details.tree;

import dto.ActivationDTO;
import javafx.scene.control.TreeItem;

public class ActivationTreeItem extends TreeItem<String> implements OpenableItem{
    private ActivationDTO activationDTO;
    public ActivationTreeItem(ActivationDTO activation) {
        super("Activation");
        this.activationDTO = activation;
    }

    @Override
    public TreeItem<String> getFullView() {
        TreeItem<String> root = new TreeItem<>("Activation Details:");
        root.getChildren().add(new TreeItem<>("Ticks: " + activationDTO.getTicks()));
        root.getChildren().add(new TreeItem<>("Probability: " + activationDTO.getProbability()));
        root.setExpanded(true);
        return root;
    }
}
