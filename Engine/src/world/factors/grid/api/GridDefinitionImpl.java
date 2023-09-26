package world.factors.grid.api;

import java.io.Serializable;

public class GridDefinitionImpl implements GridDefinition, Serializable {

    private int horizontalDiameter;
    private int verticalDiameter;

    public GridDefinitionImpl(int horizontalDiameter, int verticalDiameter){
        this.horizontalDiameter = horizontalDiameter;
        this.verticalDiameter = verticalDiameter;
    }

    public int getWidth() {
        return horizontalDiameter;
    }

    public int getHeight() {
        return verticalDiameter;
    }
}
