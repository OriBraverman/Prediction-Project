package user.results.scene;

import javafx.scene.control.ListCell;

public class ExecutionListCell extends ListCell<Integer> {
    public ExecutionListCell() {
        super();
    }

    @Override
    protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null); // Clear the cell if it's empty or the item is null.
        } else {
            setText("Simulation " + item); // Set the cell text based on the integer item.
        }
    }
}
