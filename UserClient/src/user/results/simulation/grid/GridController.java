package user.results.simulation.grid;

import dto.gridView.GridViewDTO;
import user.app.AppController;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Map;

public class GridController {
    @FXML private ScrollPane dynamicGrid;
    @FXML private ListView listView;

    private AppController appController;
    private DynamicGridView dynamicGridView;

    private SimpleIntegerProperty currentSimulationID;

    @FXML
    public void initialize() {
        currentSimulationID = new SimpleIntegerProperty();
        Platform.runLater(() -> {
            updateGrid();
        });
    }

    public void updateGrid() {
        // Create an instance of the DynamicGridView class to generate the dynamic grid
        dynamicGridView = new DynamicGridView();
        GridViewDTO gridViewDTO = appController.getGridViewDTO(currentSimulationID.get());
        ScrollPane scrollPane = dynamicGridView.createDynamicGrid(gridViewDTO);
        scrollPane.setPrefSize(dynamicGrid.getPrefWidth(), dynamicGrid.getPrefHeight());
        dynamicGrid.setContent(scrollPane);
        updateListViewOfEntities(dynamicGridView.getEntityNameToColorMap());
    }

    private void updateListViewOfEntities(Map<String, Color> entityNameToColorMap) {
        listView.getItems().clear();
        Label label = new Label("Entities:");
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-underline: true;");
        listView.getItems().add(label);
        for (String entityName : entityNameToColorMap.keySet()) {
            // each list item is in the format: Entity: <entity name> - <color>
            HBox itemContainer = new HBox(); // Container for CheckBox and TextField
            Label entityNameLabel = new Label(entityName);
            Label separator = new Label(" - ");
            Rectangle colorRectangle = new Rectangle(20, 20);
            colorRectangle.setFill(entityNameToColorMap.get(entityName));
            itemContainer.getChildren().addAll(entityNameLabel, separator, colorRectangle);
            listView.getItems().add(itemContainer);
        }
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void setCurrentSimulationID(int simulationID) {
        currentSimulationID.set(simulationID);
    }

    @FXML
    void BackwardsButtonAction(ActionEvent event) {
        this.appController.setPreviousTick(currentSimulationID.get());
        updateGrid();
    }

    @FXML
    void ForwardsButtonAction(ActionEvent event) {
        this.appController.getToNextTick(currentSimulationID.get());
        updateGrid();
    }
}
