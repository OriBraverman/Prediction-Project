package world.factors.property.execution;

import world.factors.property.definition.api.PropertyDefinition;
import world.factors.property.definition.api.PropertyType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PropertyInstanceImpl implements PropertyInstance, Serializable {

    private PropertyDefinition propertyDefinition;
    private Object value;
    private List<Integer> updatedDurations;
    private int lastUpdatedTick;

    public PropertyInstanceImpl(PropertyDefinition propertyDefinition, Object value) {
        this.propertyDefinition = propertyDefinition;
        this.value = value;
        this.updatedDurations = new ArrayList<>();
        this.lastUpdatedTick = 0;
    }

    @Override
    public PropertyDefinition getPropertyDefinition() {
        return propertyDefinition;
    }

    @Override
    public void updateValue(Object val, int currentTick) {
        this.updatedDurations.add(currentTick - lastUpdatedTick);
        this.lastUpdatedTick = currentTick;
        this.value = val;
    }

    @Override
    public PropertyType getType() {
        return propertyDefinition.getType();
    }

    public List<Integer> getUpdatedDurations() {
        return updatedDurations;
    }

    public int getLastUpdatedTick() {
        return lastUpdatedTick;
    }

    @Override
    public int getConsistency(int currentTick) {
        int sumConsistency = 0;
        for (int i = 0; i < updatedDurations.size(); i++) {
            sumConsistency += updatedDurations.get(i);
        }
        sumConsistency += currentTick - lastUpdatedTick;
        if (updatedDurations.size() == 0) {
            return sumConsistency;
        } else if (currentTick == lastUpdatedTick) {
            return sumConsistency / updatedDurations.size();
        }
        else {
            return sumConsistency / (updatedDurations.size() + 1);
        }
    }

    @Override
    public Object getValue() {
        return value;
    }
}
