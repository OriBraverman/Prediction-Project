package dto;

public class SimulationStateDTO {
    private final int simulationID;
    private final String simulationState;

    public SimulationStateDTO(int simulationID, String simulationState) {
        this.simulationID = simulationID;
        this.simulationState = simulationState;
    }

    public int getSimulationID() {
        return simulationID;
    }

    public String getSimulationState() {
        return simulationState;
    }
}
