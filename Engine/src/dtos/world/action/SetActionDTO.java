package dtos.world.action;

import dtos.world.EntityDefinitionDTO;

public class SetActionDTO extends AbstructActionDTO {
    private final String propName;
    private final String value;

    public SetActionDTO(EntityDefinitionDTO primatyEntity, String propName, String value) {
        super("Set", primatyEntity);
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