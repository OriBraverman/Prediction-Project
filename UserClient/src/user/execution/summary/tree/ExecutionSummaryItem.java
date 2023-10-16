package user.execution.summary.tree;

import dto.ExecutionSummaryDTO;
import dto.world.WorldDTO;
import dto.world.WorldsDTO;
import javafx.scene.control.TreeItem;

public class ExecutionSummaryItem extends TreeItem<String> {
    private ExecutionSummaryDTO executionSummaryDTO;
    private TreeItem<String> worldName;
    private TreeItem<String> requestID;
    private TreeItem<String> envVariables;
    private TreeItem<String> entities;
    private TreeItem<String> termination;

    public ExecutionSummaryItem(ExecutionSummaryDTO executionSummaryDTO) {
        super("Execution Summery Details:");
        this.executionSummaryDTO = executionSummaryDTO;
        worldName = new TreeItem<>("World Name: " + executionSummaryDTO.getWorldName());
        requestID = new TreeItem<>("Request ID: " + executionSummaryDTO.getRequestID());
        envVariables = new EnvVariablesTreeItem(executionSummaryDTO.getEnvVariables());
        entities = new EntitiesPopulationsTreeItem(executionSummaryDTO.getEntitiesPopulation());
        termination = new TerminationTreeItem(executionSummaryDTO.getTermination());
        this.setExpanded(true);
    }
}
