package world.factors.environment.execution.impl;

import world.factors.environment.execution.api.ActiveEnvironment;
import world.factors.property.execution.PropertyInstance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ActiveEnvironmentImpl implements ActiveEnvironment, Serializable {

    private final Map<String, PropertyInstance> envVariables;

    public ActiveEnvironmentImpl() {
        envVariables = new HashMap<>();
    }

    @Override
    public PropertyInstance getProperty(String name) {
        if (!envVariables.containsKey(name)) {
            throw new IllegalArgumentException("Can't find env variable with name " + name);
        }
        return envVariables.get(name);
    }

    @Override
    public void addPropertyInstance(PropertyInstance propertyInstance) {
        envVariables.put(propertyInstance.getPropertyDefinition().getName(), propertyInstance);
    }

    @Override
    public List<PropertyInstance> getEnvVariables() {
        return new ArrayList<>(envVariables.values());
    }
}
