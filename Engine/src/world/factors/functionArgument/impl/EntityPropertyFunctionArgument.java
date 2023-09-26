package world.factors.functionArgument.impl;

import context.Context;
import world.factors.functionArgument.api.AbstructFunctionArgument;
import world.factors.functionArgument.api.FunctionArgumentType;

public class EntityPropertyFunctionArgument extends AbstructFunctionArgument {
    private final String entityName;
    private final String propertyName;

    public EntityPropertyFunctionArgument(String functionArgument, String entityName, String propertyName) {
        super(FunctionArgumentType.ENTITY_PROPERTY_NAME, functionArgument);
        this.entityName = entityName;
        this.propertyName = propertyName;
    }

    @Override
    public Object evaluate(Context context) {
        if ((context.getPrimaryEntityInstance().getEntityDefinition().getName().equals(this.entityName))) {
            return context.getPrimaryEntityInstance().getPropertyByName(this.propertyName).getValue();
        } else if (context.getSecondaryEntityInstance().getEntityDefinition().getName().equals(this.entityName)) {
            return context.getSecondaryEntityInstance().getPropertyByName(this.propertyName).getValue();
        } else {
            throw new RuntimeException("Entity " + this.entityName + " not found");
        }
    }

    public String getEntityName() {
        return entityName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
