package dtos.result;

public class PropertyAvaregeValueDTO {
    private int simulationID;
    private String entityName;
    private String propertyName;
    private String avarageValue;

    public PropertyAvaregeValueDTO(int simulationID, String entityName, String propertyName, String avarageValue) {
        this.simulationID = simulationID;
        this.entityName = entityName;
        this.propertyName = propertyName;
        this.avarageValue = avarageValue;
    }

    public int getSimulationID() {
        return simulationID;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getAvarageValue() {
        return avarageValue;
    }
}
