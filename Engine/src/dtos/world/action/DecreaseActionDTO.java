package dtos.world.action;

import dtos.world.EntityDefinitionDTO;

public class DecreaseActionDTO extends AbstructActionDTO{
    private final String propName;
    private final String value;

    public DecreaseActionDTO(EntityDefinitionDTO primatyEntity, String propName, String value) {
        super("Decrease", primatyEntity);
        this.propName = propName;
        this.value = value;
    }

    public String getPropName() {
        return propName;
    }

    public String getValue() {
        return value;
    }
}