package dtos.result;

public class PropertyConstistencyDTO {
    private int simulationID;
    private String entityName;
    private String propertyName;
    private String consistency;

    public PropertyConstistencyDTO(int simulationID, String entityName, String propertyName, String consistency) {
        this.simulationID = simulationID;
        this.entityName = entityName;
        this.propertyName = propertyName;
        this.consistency = consistency;
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

    public String getConsistency() {
        return consistency;
    }
}
