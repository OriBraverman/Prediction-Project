package dto;

import dto.world.PropertyDefinitionDTO;
import dto.world.TerminationDTO;

import java.util.Map;

public class ExecutionSummaryDTO {
    private Integer simulationId;
    private String worldName;
    private Integer requestID;
    private TerminationDTO termination;
    private Map <String, Integer> entitiesPopulation;
    private Map<PropertyDefinitionDTO, String> envVariables;


    public ExecutionSummaryDTO(Integer simulationId, Map<String, Integer> entitiesPopulation, Map<PropertyDefinitionDTO, String> envVariables) {
        this.simulationId = simulationId;
        this.entitiesPopulation = entitiesPopulation;
        this.envVariables = envVariables;
    }

    public Integer getSimulationId() {
        return simulationId;
    }

    public Map<String, Integer> getEntitiesPopulation() {
        return entitiesPopulation;
    }

    public Map<PropertyDefinitionDTO, String> getEnvVariables() {
        return envVariables;
    }

    public String getWorldName() {
        return worldName;
    }

    public Integer getRequestID() {
        return requestID;
    }

    public TerminationDTO getTermination() {
        return termination;
    }
}
