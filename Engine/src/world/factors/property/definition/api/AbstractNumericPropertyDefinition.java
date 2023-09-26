package world.factors.property.definition.api;

import value.generator.api.ValueGenerator;
import world.factors.property.definition.api.AbstractPropertyDefinition;
import world.factors.property.definition.api.NumericPropertyDefinition;
import world.factors.property.definition.api.PropertyType;
import world.factors.property.definition.api.Range;

public abstract class AbstractNumericPropertyDefinition<T> extends AbstractPropertyDefinition implements NumericPropertyDefinition {
    protected Range range;
    public AbstractNumericPropertyDefinition(String name, PropertyType propertyType, ValueGenerator valueGenerator) {
        super(name, propertyType, valueGenerator);
    }
    public AbstractNumericPropertyDefinition(String name, PropertyType propertyType, ValueGenerator valueGenerator, Range range) {
        super(name, propertyType, valueGenerator);
        this.range = range;
    }

    @Override
    public Range getRange() {
        return range;
    }
}
