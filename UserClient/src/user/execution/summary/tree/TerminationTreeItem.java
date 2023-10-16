package user.execution.summary.tree;

import dto.world.TerminationDTO;
import javafx.scene.control.TreeItem;

public class TerminationTreeItem extends TreeItem<String> {
    private TerminationDTO termination;
    public TerminationTreeItem(TerminationDTO termination) {
        super("Termination Conditions");
        this.termination = termination;
        if (termination.isByUser()) {
            this.getChildren().add(new TreeItem<>("Terminated By User"));
        } else {
            if (termination.getSecondsCount() != -1) {
                this.getChildren().add(new TreeItem<>("Terminated By Seconds Count: " + termination.getSecondsCount()));
            }
            if (termination.getTicksCount() != -1) {
                this.getChildren().add(new TreeItem<>("Terminated By Ticks Count: " + termination.getTicksCount()));
            }
        }
        this.setExpanded(true);
    }
}
