package world.factors.property.definition.api;

public interface PropertyDefinition {
    String getName();
    PropertyType getType();
    Object generateValue();
    boolean isNumeric();
    boolean isRandomInit();
}