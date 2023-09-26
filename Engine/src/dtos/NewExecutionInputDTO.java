package dtos;

import dtos.world.EntityDefinitionDTO;
import dtos.world.PropertyDefinitionDTO;

import java.util.List;

public class NewExecutionInputDTO {
    private final List<PropertyDefinitionDTO> envVariables;
    private final List<EntityDefinitionDTO> entityDefinitions;

    public NewExecutionInputDTO(List<PropertyDefinitionDTO> envVariables, List<EntityDefinitionDTO> entityDefinitions) {
        this.envVariables = envVariables;
        this.entityDefinitions = entityDefinitions;
    }

    public List<PropertyDefinitionDTO> getEnvVariables() {
        return envVariables;
    }

    public List<EntityDefinitionDTO> getEntityDefinitions() {
        return entityDefinitions;
    }
}
