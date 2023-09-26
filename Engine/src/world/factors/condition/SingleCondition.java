package world.factors.condition;

import context.Context;
import world.factors.entity.definition.EntityDefinition;
import world.factors.expression.api.AbstractExpression;
import world.factors.expression.api.Expression;
import world.factors.property.definition.api.PropertyDefinition;

import java.io.Serializable;

public class SingleCondition implements Condition, Serializable {
    private final EntityDefinition entityDefinition;
    private final String propertyExpression;
    private final OperatorType operator;
    private final String valueExpression;

    public SingleCondition(EntityDefinition entityDefinition, String propertyExpression, OperatorType operator, String valueExpression)
    {
        this.entityDefinition = entityDefinition;
        this.propertyExpression = propertyExpression;
        this.operator = operator;
        this.valueExpression = valueExpression;
    }

    public EntityDefinition getEntityDefinition() {
        return entityDefinition;
    }

    public String getPropertyExpression() {
        return propertyExpression;
    }

    public String getvalueExpression() {
        return valueExpression;
    }

    public OperatorType getOperator() {
        return operator;
    }

    @Override
    public boolean assertCondition(Context context) {
        Expression propertyExpression = AbstractExpression.getExpressionByString(this.propertyExpression, this.entityDefinition);
        Object propertyValue = context.getValueByExpression(propertyExpression);
        Expression byExpression = AbstractExpression.getExpressionByString(this.valueExpression, this.entityDefinition);
        Object byValue = context.getValueByExpression(byExpression);
        // check if the property value is the same type as the value
        if (propertyValue.getClass() != byValue.getClass())
            return false;
        switch (this.operator){
            case EQUALS:
                return propertyValue.equals(byValue);
            case NOT_EQUALS:
                return !propertyValue.equals(byValue);
            case BIGGER_THAN:
                if (propertyValue instanceof Number && byValue instanceof Number)
                    return ((Number) propertyValue).doubleValue() > ((Number) byValue).doubleValue();
                break;
            case LOWER_THAN:
                if (propertyValue instanceof Number && byValue instanceof Number)
                    return ((Number) propertyValue).doubleValue() < ((Number) byValue).doubleValue();
                break;
        }
        return false;
    }

    @Override
    public boolean isPropertyExistInEntity() {
        return true;
    }

    @Override
    public String toString() {
        return "SingleCondition: " +
                "Entity: " + entityDefinition.getName() + " " +
                "Property: " + propertyExpression + " " +
                "Operator: " + operator + " " +
                "Value: " + valueExpression;
    }
}
