package dtos;

import java.util.List;

public class SimulationIDListDTO {
    private final List<Integer> simulationsID;

    public SimulationIDListDTO(List<Integer> simulationsID) {
        this.simulationsID = simulationsID;
    }

    public List<Integer> getSimulationsID() {
        return simulationsID;
    }
}
