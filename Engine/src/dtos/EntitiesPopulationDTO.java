package dtos;

import java.util.List;

public class EntitiesPopulationDTO {
    private final List<EntityPopulationDTO> entitiesPopulation;

    public EntitiesPopulationDTO(List<EntityPopulationDTO> entitiesPopulation) {
        this.entitiesPopulation = entitiesPopulation;
    }

    public List<EntityPopulationDTO> getEntitiesPopulation() {
        return entitiesPopulation;
    }

    public EntityPopulationDTO getEntityPopulationByName(String name) {
        return entitiesPopulation.stream()
                .filter(entityPopulationDTO -> entityPopulationDTO.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
