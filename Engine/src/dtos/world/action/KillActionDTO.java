package dtos.world.action;

import dtos.world.EntityDefinitionDTO;

public class KillActionDTO extends AbstructActionDTO{
    public KillActionDTO(EntityDefinitionDTO primatyEntity) {
        super("Kill", primatyEntity);
    }
}