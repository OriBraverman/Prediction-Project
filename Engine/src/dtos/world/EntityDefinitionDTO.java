package dtos.world;

import dtos.EntityPropertyDefinitionDTO;

import java.util.List;

public class EntityDefinitionDTO {
    private String name;
    private List<PropertyDefinitionDTO> properties;

    public EntityDefinitionDTO(String name, List<PropertyDefinitionDTO> properties) {
        this.name = name;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public List<PropertyDefinitionDTO> getProperties() {
        return properties;
    }
}
