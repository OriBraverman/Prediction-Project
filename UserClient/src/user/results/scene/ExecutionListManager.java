package user.results.scene;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class ExecutionListManager {
    private List<Integer> executionList;

    public ExecutionListManager() {
        executionList = new ArrayList<>();
    }

    /*
    This method is used to add all the executions to the execution list.
    The return value is the elements that were added to the execution list.
     */
    public ObservableList<Integer> addAll(List<Integer> executionList) {
        ObservableList<Integer> addedElements = FXCollections.observableArrayList();
        executionList.forEach(executionID -> {
            if (!this.executionList.contains(executionID)) {
                this.executionList.add(executionID);
                addedElements.add(executionID);
            }
        });
        return addedElements;
    }

    public int getMaxExecutionID() {
        return executionList.stream().max(Integer::compareTo).orElse(0);
    }

    public boolean isEmpty() {
        return executionList.isEmpty();
    }
}
