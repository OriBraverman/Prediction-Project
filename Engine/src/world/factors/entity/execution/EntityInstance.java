package world.factors.entity.execution;

import world.factors.entity.definition.EntityDefinition;
import world.factors.grid.Cell;
import world.factors.grid.Coordinate;
import world.factors.property.definition.api.PropertyDefinition;
import world.factors.property.execution.PropertyInstance;

import java.util.List;

public interface EntityInstance {
    int getId();
    PropertyInstance getPropertyByName(String name);
    void addPropertyInstance(PropertyInstance propertyInstance);
    EntityDefinition getEntityDefinition();
    Coordinate getCoordinate();
    Cell getCell();
    void setCell(Cell cell);
}
