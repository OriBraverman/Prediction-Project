package world.factors.environment.definition.impl;

import world.factors.environment.execution.api.ActiveEnvironment;
import world.factors.environment.definition.api.EnvVariablesManager;
import world.factors.property.definition.api.PropertyDefinition;
import world.factors.environment.execution.impl.ActiveEnvironmentImpl;
import world.factors.property.definition.api.PropertyType;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EnvVariableManagerImpl implements EnvVariablesManager, Serializable {

    private final Map<String, PropertyDefinition> propNameToPropDefinition;

    public EnvVariableManagerImpl() {
        propNameToPropDefinition = new HashMap<>();
    }

    @Override
    public void addEnvironmentVariable(PropertyDefinition propertyDefinition) {
        propNameToPropDefinition.put(propertyDefinition.getName(), propertyDefinition);
    }

    @Override
    public ActiveEnvironment createActiveEnvironment() {
        return new ActiveEnvironmentImpl();
    }

    @Override
    public Collection<PropertyDefinition> getEnvVariables() {
        return propNameToPropDefinition.values();
    }

    @Override
    public boolean isNumericProperty(String propertyName) {
        return propNameToPropDefinition.get(propertyName).getType() == PropertyType.FLOAT ||
                propNameToPropDefinition.get(propertyName).getType() == PropertyType.DECIMAL;
    }

    @Override
    public PropertyDefinition getPropertyDefinitionByName(String propertyName) {
        return propNameToPropDefinition.get(propertyName);
    }
}
