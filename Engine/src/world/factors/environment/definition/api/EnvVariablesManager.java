package world.factors.environment.definition.api;

import world.factors.property.definition.api.PropertyDefinition;
import world.factors.environment.execution.api.ActiveEnvironment;

import java.util.Collection;

public interface EnvVariablesManager {
    void addEnvironmentVariable(PropertyDefinition propertyDefinition);
    ActiveEnvironment createActiveEnvironment();
    Collection<PropertyDefinition> getEnvVariables();
    boolean isNumericProperty(String propertyName);
    PropertyDefinition getPropertyDefinitionByName(String propertyName);
}
