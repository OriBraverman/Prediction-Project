package user.details.tree;

import dto.world.TerminationDTO;
import javafx.scene.control.TreeItem;

public class TerminationTreeItem extends TreeItem<String> implements OpenableItem {
    private TerminationDTO termination;
    public TerminationTreeItem(TerminationDTO termination) {
        super("Termination Conditions");
        this.termination = termination;
    }

    @Override
    public TreeItem<String> getFullView() {
        TreeItem<String> root = new TreeItem<>("Termination Details:");
        if (termination.isByUser()) {
            root.getChildren().add(new TreeItem<>("Terminated By User"));
        } else {
            if (termination.getSecondsCount() != -1) {
                root.getChildren().add(new TreeItem<>("Terminated By Seconds Count: " + termination.getSecondsCount()));
            }
            if (termination.getTicksCount() != -1) {
                root.getChildren().add(new TreeItem<>("Terminated By Ticks Count: " + termination.getTicksCount()));
            }
        }
        root.setExpanded(true);
        return root;
    }
}
