package user.execution.envVarible;

import dto.EnvVariableValueDTO;
import dto.world.PropertyDefinitionDTO;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class EnvVariableTitledCell {
    private final TitledPane titledPane;
    private final Label nameValueLabel;
    private final Label typeValueLabel;
    private final Label fromValueLabel;
    private final Label toValueLabel;
    private final CheckBox addValueCheckBox;
    private final TextField textFieldValue;
    public EnvVariableTitledCell(PropertyDefinitionDTO propertyDefinitionDTO) {
        // Create a GridPane with 5 columns and 4 rows
        // make all columns to be aligned to the center of the GridPane
        GridPane gridPane = new GridPane();

        // Define column constraints.
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        ColumnConstraints col4 = new ColumnConstraints();
        ColumnConstraints col5 = new ColumnConstraints();
        col1.setHalignment(javafx.geometry.HPos.CENTER);
        col2.setHalignment(javafx.geometry.HPos.CENTER);
        col3.setHalignment(javafx.geometry.HPos.CENTER);
        col4.setHalignment(javafx.geometry.HPos.CENTER);
        col5.setHalignment(javafx.geometry.HPos.CENTER);
        gridPane.getColumnConstraints().addAll(col1, col2, col3, col4, col5);

        // Define row constraints.
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        RowConstraints row3 = new RowConstraints();
        RowConstraints row4 = new RowConstraints();
        row1.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
        row2.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
        row3.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
        row4.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
        gridPane.getRowConstraints().addAll(row1, row2, row3, row4);

        // Create Labels, CheckBox, and TextField
        Label nameLabel = new Label("Name:");
        nameValueLabel = new Label(propertyDefinitionDTO.getName());
        Label typeLabel = new Label("Type:");
        typeValueLabel = new Label(propertyDefinitionDTO.getType());
        Label rangeLabel = new Label("Range:");
        Label fromLabel = new Label("From:");
        Label toLabel = new Label("To:");
        if (propertyDefinitionDTO.getFromRange() != null) {
            fromValueLabel = new Label(propertyDefinitionDTO.getFromRange());
            toValueLabel = new Label(propertyDefinitionDTO.getToRange());
        } else {
            fromValueLabel = null;
            toValueLabel = null;
        }
        addValueCheckBox = new CheckBox("Want to add value?");
        textFieldValue = new TextField();

        // Set layout constraints for specific elements
        GridPane.setMargin(textFieldValue, new Insets(0, 0, 0, 10)); // Add right margin to the TextField

        // Add elements to the grid
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameValueLabel, 1, 0);
        gridPane.add(typeLabel, 0, 1);
        gridPane.add(typeValueLabel, 1, 1);
        if (propertyDefinitionDTO.getFromRange() != null) {
            gridPane.add(rangeLabel, 0, 2);
            gridPane.add(fromLabel, 1, 2);
            gridPane.add(fromValueLabel, 2, 2);
            gridPane.add(toLabel, 3, 2);
            gridPane.add(toValueLabel, 4, 2);
        }
        gridPane.add(addValueCheckBox, 0, 3, 2, 1);
        gridPane.add(textFieldValue, 2, 3, 3, 1);

        textFieldValue.disableProperty().bind(addValueCheckBox.selectedProperty().not());
        // Create a TitledPane
        titledPane = new TitledPane(propertyDefinitionDTO.getName(), gridPane);
    }

    public TitledPane getContainer() {
        return titledPane;
    }

    public EnvVariableValueDTO getEnvVariablesValuesDTO() {
        return new EnvVariableValueDTO(nameValueLabel.getText(), textFieldValue.getText(), addValueCheckBox.isSelected());
    }

    public void clear() {
        addValueCheckBox.setSelected(false);
        textFieldValue.setText("");
    }

    public void fill(EnvVariableValueDTO envVariableValueDTO) {
        addValueCheckBox.setSelected(envVariableValueDTO.hasValue());
        if (envVariableValueDTO.hasValue()) {
            textFieldValue.setText(envVariableValueDTO.getValue());
        }
    }
}
