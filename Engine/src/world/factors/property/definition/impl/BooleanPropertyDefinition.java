package world.factors.property.definition.impl;

import value.generator.api.ValueGenerator;
import world.factors.property.definition.api.AbstractPropertyDefinition;
import world.factors.property.definition.api.PropertyType;

public class BooleanPropertyDefinition extends AbstractPropertyDefinition<Boolean> {
    public BooleanPropertyDefinition(String name, ValueGenerator<Boolean> valueGenerator) {
        super(name, PropertyType.BOOLEAN, valueGenerator);
    }

    @Override
    public boolean isNumeric() {
        return false;
    }
}
