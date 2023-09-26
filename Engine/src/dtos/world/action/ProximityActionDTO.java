package dtos.world.action;

import dtos.world.EntityDefinitionDTO;

import java.util.List;

public class ProximityActionDTO extends AbstructActionDTO {
    private final EntityDefinitionDTO targetEntity;
    private final String ofValue;
    private final List<AbstructActionDTO> thenActions;

    public ProximityActionDTO(EntityDefinitionDTO sourceEntity, EntityDefinitionDTO targetEntity, String ofValue, List<AbstructActionDTO> thenActions) {
        super("Proximity", sourceEntity);
        this.targetEntity = targetEntity;
        this.ofValue = ofValue;
        this.thenActions = thenActions;
    }

    public String getOfValue() {
        return ofValue;
    }

    public EntityDefinitionDTO getTargetEntity() {
        return targetEntity;
    }

    public List<AbstructActionDTO> getThenActions() {
        return thenActions;
    }
}