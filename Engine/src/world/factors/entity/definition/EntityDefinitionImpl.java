package world.factors.entity.definition;

import world.factors.property.definition.api.PropertyDefinition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EntityDefinitionImpl implements EntityDefinition, Serializable {

    private final String name;
    private final List<PropertyDefinition> properties;

    public EntityDefinitionImpl(String name) {
        this.name = name;
        this. properties = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addProperty(PropertyDefinition propertyDefinition) {
        properties.add(propertyDefinition);
    }

    @Override
    public List<PropertyDefinition> getProps() {
        return properties;
    }

    @Override
    public PropertyDefinition getPropertyDefinitionByName(String name) {
        for (PropertyDefinition propertyDefinition : properties) {
            if (propertyDefinition.getName().equals(name)) {
                return propertyDefinition;
            }
        }
        return null;
    }
}
