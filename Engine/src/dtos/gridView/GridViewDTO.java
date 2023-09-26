package dtos.gridView;

import java.util.List;

public class GridViewDTO {
    private final int gridWidth;
    private final int gridHeight;
    private final List<String> entityDefinitionNames;
    private final List<EntityInstanceDTO> entityInstances;

    public GridViewDTO(int gridWidth, int gridHeight, List<EntityInstanceDTO> entityInstances, List<String> entityDefinitionNames) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.entityInstances = entityInstances;
        this.entityDefinitionNames = entityDefinitionNames;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public List<EntityInstanceDTO> getEntityInstances() {
        return entityInstances;
    }

    public List<String> getEntityDefinitionNames() {
        return entityDefinitionNames;
    }
}
