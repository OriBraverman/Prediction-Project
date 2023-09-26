package world.factors.action.impl;

import context.Context;
import world.World;
import world.factors.action.api.AbstractAction;
import world.factors.action.api.ActionType;
import world.factors.action.api.SecondaryEntity;
import world.factors.entity.definition.EntityDefinition;
import world.factors.environment.definition.impl.EnvVariableManagerImpl;
import world.factors.expression.api.AbstractExpression;
import world.factors.expression.api.Expression;
import world.factors.expression.api.ExpressionType;
import world.factors.function.api.Function;
import world.factors.function.api.FunctionType;
import world.factors.property.definition.api.PropertyType;
import world.factors.property.execution.PropertyInstance;

import java.util.List;

public class CalculationAction extends AbstractAction {
    private final String resultProperty;
    private final String argument1;
    private final String argument2;
    private final CalculationOperator operator;
    public enum CalculationOperator {
        MULTIPLY, DIVIDE;
        @Override
        public String toString() {
            switch (this) {
                case MULTIPLY:
                    return "*";
                case DIVIDE:
                    return "/";
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    public CalculationAction(EntityDefinition entityDefinition, String resultProperty, String argument1, String argument2, CalculationOperator operator) {
        super(ActionType.CALCULATION, entityDefinition);
        this.resultProperty = resultProperty;
        this.argument1 = argument1;
        this.argument2 = argument2;
        this.operator = operator;
    }

    public CalculationAction(EntityDefinition primaryEntityDefinition, SecondaryEntity secondaryEntity, String resultProperty, String argument1, String argument2, CalculationOperator operator) {
        super(ActionType.CALCULATION, primaryEntityDefinition, secondaryEntity);
        this.resultProperty = resultProperty;
        this.argument1 = argument1;
        this.argument2 = argument2;
        this.operator = operator;
    }

    @Override
    public void invoke(Context context) {
        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(resultProperty);
        Expression expr1 = AbstractExpression.getExpressionByString(argument1, primaryEntityDefinition);
        Expression expr2 = AbstractExpression.getExpressionByString(argument2, primaryEntityDefinition);
        float exp1Value = PropertyType.FLOAT.convert(context.getValueByExpression(expr1));
        float exp2Value = PropertyType.FLOAT.convert(context.getValueByExpression(expr2));
        if (propertyInstance.getType() == PropertyType.DECIMAL) {
            /*forum question:
             * As part of calculation operations calculation \ increase \ decrease
             * what do we do in case the result of the operation is real and the PropertyType is an integer?
             * Answer:
             * In this case, of course, it is not possible to insert a real number into an integer, so it will be considered an error.
             * But if it was the other way around (for example an increase operation with a by of 3 for a property that is real)
             *  - of course it makes sense to allow since a real number can deal with an integer...*/
            Integer v;
            if (operator == CalculationOperator.DIVIDE) {
                v = PropertyType.DECIMAL.convert(Divide(exp1Value, exp2Value));
            }
            else /* if (operator == CalculationOperator.MULTIPLY)*/ {
                v = PropertyType.DECIMAL.convert(Multiply(exp1Value, exp2Value));
            }
            propertyInstance.updateValue(v, context.getCurrentTick());
        }

        else if (propertyInstance.getType() == PropertyType.FLOAT) {
            Float v;
            if (operator == CalculationOperator.DIVIDE) {
                v = PropertyType.FLOAT.convert(Divide(exp1Value, exp2Value));
            }
            else /* if (operator == CalculationOperator.MULTIPLY)*/ {
                v = PropertyType.FLOAT.convert(Multiply(exp1Value, exp2Value));
            }
            propertyInstance.updateValue(v, context.getCurrentTick());
        }

        else {
            throw new IllegalArgumentException("increase action can't operate on a none number property [" + resultProperty + "]");
        }
    }

    private float Divide(float exp1Value, float exp2Value) {
        if (exp2Value == 0) {
            throw new IllegalArgumentException("calculation action can't divide by zero");
        }
        return exp1Value / exp2Value;
    }

    private float Multiply(float exp1Value, float exp2Value) {
        return exp1Value * exp2Value;
    }

    @Override
    public boolean isPropertyExistInEntity() {
        return primaryEntityDefinition.getPropertyDefinitionByName(resultProperty) != null;
    }

    public boolean isMathActionHasNumericArgs(List<EntityDefinition> entities, EnvVariableManagerImpl envVariableManagerImpl) {
        Expression expr1 = AbstractExpression.getExpressionByString(argument1, primaryEntityDefinition);
        Expression expr2 = AbstractExpression.getExpressionByString(argument2, primaryEntityDefinition);
        if (!(expr1.isNumericExpression(entities, envVariableManagerImpl))) {
            return false;
        }
        if (!(expr2.isNumericExpression(entities, envVariableManagerImpl))) {
            return false;
        }
        return true;
    }

    public String getArgument1() {
        return argument1;
    }

    public String getArgument2() {
        return argument2;
    }

    public String getResultProperty() {
        return resultProperty;
    }

    public String getCalculationExpression() {
        return argument1 + " " + operator.toString() + " " + argument2;
    }
}
