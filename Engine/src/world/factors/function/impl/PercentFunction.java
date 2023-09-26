package world.factors.function.impl;

import context.Context;
import world.factors.expression.api.Expression;
import world.factors.function.api.AbstractFunction;
import world.factors.function.api.FunctionType;
import world.factors.functionArgument.api.FunctionArgument;
import world.factors.functionArgument.impl.ExpressionFunctionArgument;

import java.util.List;

public class PercentFunction extends AbstractFunction {
    public PercentFunction(List<FunctionArgument> functionArguments) {
        super(FunctionType.PERCENT, functionArguments, 2);
    }

    @Override
    public Object execute(Context context) {
        if (!(functionArguments.get(0) instanceof ExpressionFunctionArgument) || !(functionArguments.get(1) instanceof ExpressionFunctionArgument)) {
            throw new IllegalArgumentException("percent function: arguments must be expressions");
        }
        Expression expression1 = ((ExpressionFunctionArgument) functionArguments.get(0)).getExpression();
        Expression expression2 = ((ExpressionFunctionArgument) functionArguments.get(1)).getExpression();
        if (!(context.getValueByExpression(expression1) instanceof Float) || !(context.getValueByExpression(expression2) instanceof Float)) {
            throw new IllegalArgumentException("percent function: arguments must be numeric");
        }
        float num1 = (float) context.getValueByExpression(expression1);
        float num2 = (float) context.getValueByExpression(expression2);
        if (num2 == 0) {
            throw new IllegalArgumentException("percent function: second argument must be non-zero");
        }
        return num1 / num2;
    }

    @Override
    public boolean isNumericFunction(Object object) {
        return true;
    }
}
