package world.factors.environment.execution.api;

import world.factors.property.execution.PropertyInstance;

import java.util.List;

public interface ActiveEnvironment {
    PropertyInstance getProperty(String name);
    void addPropertyInstance(PropertyInstance propertyInstance);

    List<PropertyInstance> getEnvVariables();
}
