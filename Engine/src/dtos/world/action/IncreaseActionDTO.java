package dtos.world.action;

import dtos.world.EntityDefinitionDTO;

public class IncreaseActionDTO extends AbstructActionDTO{
    private final String propName;
    private final String value;

    public IncreaseActionDTO(EntityDefinitionDTO primatyEntity, String propName, String value) {
        super("Increase", primatyEntity);
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