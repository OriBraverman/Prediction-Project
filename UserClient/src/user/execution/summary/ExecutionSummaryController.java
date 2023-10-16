package user.execution.summary;

import dto.ExecutionSummaryDTO;
import dto.world.PropertyDefinitionDTO;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import user.app.AppController;
import user.details.tree.OpenableItem;
import user.details.tree.WorldsDetailsItem;
import user.execution.summary.tree.ExecutionSummaryItem;

import javax.swing.tree.TreeNode;
import java.util.Map;

public class ExecutionSummaryController {
    @FXML private Label ExecutionSummaryTitle;
    @FXML private Button confirmAndRunButton;
    @FXML private Button cancelButton;
    @FXML private TreeView<String> summaryTreeView;

    private AppController appController;

    @FXML
    public void initialize() {
        if (summaryTreeView != null) {
            summaryTreeView.setRoot(null);
        }
    }

    public void updateSummaryTreeView(ExecutionSummaryDTO executionSummaryDTO) {
        TreeItem<String> root = new ExecutionSummaryItem(executionSummaryDTO);
        summaryTreeView.setRoot(root);
        summaryTreeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    void CancelButtonAction(ActionEvent event) {

    }

    @FXML
    void confirmAndRunButtonButtonAction(ActionEvent event) {

    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }


}
