package user.results.simulation.grid;

import dtos.gridView.EntityInstanceDTO;
import dtos.gridView.GridViewDTO;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class DynamicGridView {
    private Map<String, Color> entityNameToColorMap;
    private final List<Color> colors = new ArrayList<>(Arrays.asList(
            Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PURPLE,
            Color.ORANGE, Color.PINK, Color.BROWN, Color.MAGENTA, Color.CYAN,
            Color.DARKMAGENTA
    ));


    public ScrollPane createDynamicGrid(GridViewDTO gridViewDTO) {
        // Create a ScrollPane to contain the GridPane
        ScrollPane scrollPane = new ScrollPane();

        // Create a GridPane
        GridPane dynamicGrid = new GridPane();

        int gridWidth = gridViewDTO.getGridWidth();
        int gridHeight = gridViewDTO.getGridHeight();

        // Define column constraints for the specified width
        //<ColumnConstraints hgrow="NEVER" maxWidth="-Infinity" minWidth="50.0" prefWidth="80.0" />
        for (int i = 0; i < gridWidth; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.NEVER);
            colConstraints.setMaxWidth(Double.MAX_VALUE);
            colConstraints.setMinWidth(5.0);
            colConstraints.setPrefWidth(10.0);
            dynamicGrid.getColumnConstraints().add(colConstraints);
        }

        // Define row constraints for the specified height
        //<RowConstraints maxHeight="-Infinity" minHeight="30.0" prefHeight="50.0" vgrow="SOMETIMES" />
        for (int i = 0; i < gridHeight; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMaxHeight(Double.MAX_VALUE);
            rowConstraints.setMinHeight(5.0);
            rowConstraints.setPrefHeight(10.0);
            rowConstraints.setVgrow(Priority.SOMETIMES);
            dynamicGrid.getRowConstraints().add(rowConstraints);
        }

        // Add an empty label to each cell of the grid
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                dynamicGrid.add(createColoredRectangle(Color.WHITE), i, j);
            }
        }
        // update the entityNameToColorMap
        List<String> entityDefinitions = gridViewDTO.getEntityDefinitionNames();
        updateEntityNameToColorMap(entityDefinitions);
        // Create and add labels with different colors for each entity
        for (EntityInstanceDTO entityInstanceDTO : gridViewDTO.getEntityInstances()) {
            String entityName = entityInstanceDTO.getEntityName();
            int x = entityInstanceDTO.getX();
            int y = entityInstanceDTO.getY();
            Color entityColor = calculateColor(entityName);
            if (entityColor != null) {
                //paint the cell with the entity color
                dynamicGrid.add(createColoredRectangle(entityColor), x, y);
            }
        }

        // Set the dynamicGrid as the content of the ScrollPane
        scrollPane.setContent(dynamicGrid);

        return scrollPane;
    }

    private void updateEntityNameToColorMap(List<String> entityDefinitions) {
        entityNameToColorMap = new HashMap<>();
        for (int i = 0; i < entityDefinitions.size(); i++) {
            entityNameToColorMap.put(entityDefinitions.get(i), colors.get(i % colors.size()));
        }
    }

    private Color calculateColor(String entityName) {
        if (entityNameToColorMap == null) {
            throw new IllegalStateException("entityNameToColorMap is null");
        }
        if (!entityNameToColorMap.containsKey(entityName)) {
            throw new IllegalStateException("entityNameToColorMap does not contain the entityName: " + entityName);
        }

        return entityNameToColorMap.get(entityName);
    }

    private Rectangle createColoredRectangle(Color color) {
        Rectangle rectangle = new Rectangle(10, 10);
        rectangle.setFill(color);
        rectangle.setStroke(Color.BLACK);
        return rectangle;
    }

    public Map<String, Color> getEntityNameToColorMap() {
        return entityNameToColorMap;
    }
}
