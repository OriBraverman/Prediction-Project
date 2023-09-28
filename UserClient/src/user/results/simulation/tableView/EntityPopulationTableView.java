package gui.components.main.results.simulation.tableView;

import dtos.EntityPopulationDTO;
import dtos.SimulationExecutionDetailsDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

public class EntityPopulationTableView {
    public List<TableView<String>> createEntityPopulationTableView(SimulationExecutionDetailsDTO simulationEDDTO) {
        List<EntityPopulationDTO> entityPopulationDTOS = simulationEDDTO.getEntitiesPopulation();
        List<TableView<String>> tableViews = entityPopulationDTOS.stream()
                .map(this::createTableView)
                .collect(Collectors.toList());
        return tableViews;
    }

    public TableView<String> createTableView(EntityPopulationDTO entityPopulationDTO) {
        TableView<String> tableView = new TableView<>();

        TableColumn<String, String> column = new TableColumn<>(entityPopulationDTO.getName());
        column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
        // Set the record alignment to the center
        column.setStyle("-fx-alignment: CENTER;");

        // Add the column to the table
        tableView.getColumns().add(column);

        // Add the record population to the table
        tableView.getItems().add(entityPopulationDTO.getPopulation());

        // Set the table width to the column width
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Set the table width to be max 100
        tableView.setPrefWidth(100);

        return tableView;
    }
}
