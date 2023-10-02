package dto.world.action;

import dto.world.EntityDefinitionDTO;

public class KillActionDTO extends AbstructActionDTO{
    public KillActionDTO(EntityDefinitionDTO primatyEntity) {
        super("Kill", primatyEntity);
    }
}