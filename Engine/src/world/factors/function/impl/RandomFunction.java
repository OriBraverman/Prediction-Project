package world.factors.function.impl;

import context.Context;
import world.factors.expression.api.Expression;
import world.factors.function.api.AbstractFunction;
import world.factors.function.api.FunctionType;
import world.factors.functionArgument.api.FunctionArgument;
import world.factors.functionArgument.impl.ExpressionFunctionArgument;

import java.util.List;

public class RandomFunction extends AbstractFunction {
    public RandomFunction(List<FunctionArgument> args) {
        super(FunctionType.RANDOM, args, 1);
    }

    @Override
    public Object execute(Context context) {
        //validate that the argument is an Integer
        //the function will return a random number between 0 and the argument (inclusive)
        Expression expression;
        if (this.functionArguments.get(0) instanceof ExpressionFunctionArgument) {
            expression = ((ExpressionFunctionArgument) this.functionArguments.get(0)).getExpression();
        } else {
            throw new IllegalArgumentException("RandomFunction argument must be an Expression");
        }
        if (!(expression.evaluate(context) instanceof Integer)) {
            throw new IllegalArgumentException("argument must be a Integer");
        }
        return (float) (Math.random() * ((Integer) expression.evaluate(context) + 1));
    }

    @Override
    public boolean isNumericFunction(Object object) {
        return true;
    }
}