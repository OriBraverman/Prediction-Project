package world.factors.functionArgument.impl;

import context.Context;
import world.factors.expression.api.Expression;
import world.factors.functionArgument.api.AbstructFunctionArgument;
import world.factors.functionArgument.api.FunctionArgumentType;

public class ExpressionFunctionArgument extends AbstructFunctionArgument {
    private final Expression expression;

    public ExpressionFunctionArgument(String functionArgument, Expression expression) {
        super(FunctionArgumentType.EXPRESSION, functionArgument);
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public Object evaluate(Context context) {
        return expression.evaluate(context);
    }
}
