package world.factors.property.definition.api;

public interface NumericPropertyDefinition extends PropertyDefinition{
    Range getRange();

    boolean isInRange(String value);
}
