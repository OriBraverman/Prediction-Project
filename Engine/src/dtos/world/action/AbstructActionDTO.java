package dtos.world.action;

import dtos.world.EntityDefinitionDTO;

public abstract class AbstructActionDTO {
    private final String type;
    private final EntityDefinitionDTO primatyEntity;
    public AbstructActionDTO(String type, EntityDefinitionDTO primatyEntity) {
        this.type = type;
        this.primatyEntity = primatyEntity;
    }

    public String getType() {
        return type;
    }

    public EntityDefinitionDTO getPrimatyEntity() {
        return primatyEntity;
    }
}
