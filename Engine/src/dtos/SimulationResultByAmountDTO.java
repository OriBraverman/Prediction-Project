package dtos;

public class SimulationResultByAmountDTO {
    private final int simulationID;
    private final EntityResultDTO[] entityInstanceResults;

    public SimulationResultByAmountDTO(int simulationID, EntityResultDTO[] entityInstanceResults) {
        this.simulationID = simulationID;
        this.entityInstanceResults = entityInstanceResults;
    }

    public int getSimulationID() {
        return simulationID;
    }

    public EntityResultDTO[] getEntityInstanceResults() {
        return entityInstanceResults;
    }
}
