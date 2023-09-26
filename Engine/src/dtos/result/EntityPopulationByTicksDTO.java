package dtos.result;

import dtos.EntityPopulationDTO;
import world.factors.entityPopulation.EntityPopulation;

import java.util.List;
import java.util.Map;

public class EntityPopulationByTicksDTO {
    private final Map<Integer, List<EntityPopulationDTO>> entityPopulationByTicks;
    private final List<String> entityNames;

    public EntityPopulationByTicksDTO(Map<Integer, List<EntityPopulationDTO>> entityPopulationByTicks, List<String> entityNames) {
        this.entityPopulationByTicks = entityPopulationByTicks;
        this.entityNames = entityNames;
    }

    public Map<Integer, List<EntityPopulationDTO>> getEntityPopulationByTicks() {
        return entityPopulationByTicks;
    }

    public List<String> getEntityNames() {
        return entityNames;
    }
}
