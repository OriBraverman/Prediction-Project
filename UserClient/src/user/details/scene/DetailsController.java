package user.details.scene;

import dto.world.WorldDTO;
import dto.world.WorldsDTO;
import user.app.AppController;
import user.details.tree.OpenableItem;
import user.details.tree.WorldsDetailsItem;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

public class DetailsController {
    @FXML private AnchorPane detailsComponent;
    @FXML private TreeView<String> detailsTreeView;
    @FXML private TreeView<String> fullInfoTree;

    private AppController appController;

    @FXML public void initialize(){
        if (detailsTreeView != null) {
            detailsTreeView.setRoot(null);
        }
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void updateDetailsTreeView(WorldsDTO worldsDTO) {
        if (detailsTreeView != null && detailsTreeView.getRoot() instanceof WorldsDetailsItem) {
            WorldsDetailsItem worldsDetailsItem = (WorldsDetailsItem) detailsTreeView.getRoot();
            if (sameWorlds(worldsDetailsItem.getWorldsDTO(), worldsDTO)) {
                return;
            }
        }
        TreeItem<String> root = new WorldsDetailsItem(worldsDTO);
        detailsTreeView.setRoot(root);
        detailsTreeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        detailsTreeView.setCellFactory(param -> new TreeCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(item);

                selectedProperty().addListener(e -> {
                    if (((ReadOnlyBooleanProperty)e).getValue()) {
                        if (getTreeItem() instanceof OpenableItem) {
                            OpenableItem openableItem = (OpenableItem) getTreeItem();
                            TreeItem<String> root = openableItem.getFullView();
                            fullInfoTree.setRoot(root);
                        }
                    }
                });
            }
        });
    }

    private boolean sameWorlds(WorldsDTO worldsDTO1, WorldsDTO worldsDTO2) {
        if (worldsDTO1.getWorlds().size() != worldsDTO2.getWorlds().size()) {
            return false;
        }
        if (worldsDTO1.getWorlds().size() == 0 && worldsDTO2.getWorlds().size() == 0) {
            return true;
        }
        for (WorldDTO worldDTO1 : worldsDTO1.getWorlds()) {
            boolean found = false;
            for (WorldDTO worldDTO2 : worldsDTO2.getWorlds()) {
                if (worldDTO1.getName().equals(worldDTO2.getName())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }
}
