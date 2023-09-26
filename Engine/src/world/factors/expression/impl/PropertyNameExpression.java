package world.factors.expression.impl;

import context.Context;
import world.factors.entity.definition.EntityDefinition;
import world.factors.entity.execution.EntityInstance;
import world.factors.environment.definition.impl.EnvVariableManagerImpl;
import world.factors.expression.api.AbstractExpression;
import world.factors.expression.api.ExpressionType;
import world.factors.property.definition.api.PropertyDefinition;
import world.factors.property.definition.api.PropertyType;

import java.io.Serializable;
import java.util.List;

public class PropertyNameExpression extends AbstractExpression {
    public PropertyNameExpression(String expression) {
        super(expression, ExpressionType.PROPERTY_NAME);
    }

    @Override
    public PropertyDefinition evaluate(Context context) {
        EntityInstance entityInstance = context.getPrimaryEntityInstance();
        if (entityInstance.getPropertyByName(expression) != null) {
            return entityInstance.getPropertyByName(expression).getPropertyDefinition();
        }
        throw new IllegalArgumentException("property [" + expression + "] is not exist");
    }

    @Override
    public boolean isNumericExpression(List<EntityDefinition> entityDefinitions, EnvVariableManagerImpl envVariableManagerImpl) {
        // check if the property type of the property of the entity is numeric
        for (EntityDefinition entityDefinition : entityDefinitions) {
            PropertyDefinition entityPropertyDefinition = entityDefinition.getPropertyDefinitionByName(expression);
            if (entityPropertyDefinition.getType() == PropertyType.FLOAT || entityPropertyDefinition.getType() == PropertyType.DECIMAL) {
                return true;
            }
        }
        return false;
    }
}