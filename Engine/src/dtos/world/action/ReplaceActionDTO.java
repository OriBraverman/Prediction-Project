package dtos.world.action;

import dtos.world.EntityDefinitionDTO;

public class ReplaceActionDTO extends AbstructActionDTO {
    private final EntityDefinitionDTO createdEntity;
    private final String mode;

    public ReplaceActionDTO(EntityDefinitionDTO primatyEntity, EntityDefinitionDTO createdEntity, String mode) {
        super("Replace", primatyEntity);
        this.createdEntity = createdEntity;
        this.mode = mode;
    }

    public EntityDefinitionDTO getCreatedEntity() {
        return createdEntity;
    }

    public String getMode() {
        return mode;
    }
}