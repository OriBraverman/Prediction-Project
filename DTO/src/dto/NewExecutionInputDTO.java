package dto;

import dto.world.EntityDefinitionDTO;
import dto.world.PropertyDefinitionDTO;

import java.util.List;

public class NewExecutionInputDTO {
    private RequestDTO requestDTO;
    private final List<PropertyDefinitionDTO> envVariables;
    private final List<EntityPopulationDTO> entityPopulations;

    public NewExecutionInputDTO(RequestDTO requestDTO, List<PropertyDefinitionDTO> envVariables, List<EntityPopulationDTO> entityPopulations) {
        this.requestDTO = requestDTO;
        this.envVariables = envVariables;
        this.entityPopulations = entityPopulations;
    }

    public RequestDTO getRequestDTO() {
        return requestDTO;
    }

    public List<PropertyDefinitionDTO> getEnvVariables() {
        return envVariables;
    }

    public List<EntityPopulationDTO> getEntityPopulations() {
        return entityPopulations;
    }
}
