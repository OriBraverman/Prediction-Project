package world.factors.function.impl;

import context.Context;
import world.factors.function.api.AbstractFunction;
import world.factors.function.api.FunctionType;
import world.factors.functionArgument.api.FunctionArgument;
import world.factors.functionArgument.impl.EntityPropertyFunctionArgument;

import java.util.List;

public class EvaluateFunction extends AbstractFunction {
    public EvaluateFunction(List<FunctionArgument> functionArguments) {
        super(FunctionType.EVALUATE, functionArguments, 1);
    }

    @Override
    public Object execute(Context context) {
        try {
            EntityPropertyFunctionArgument entityPropertyFunctionArgument = (EntityPropertyFunctionArgument) this.functionArguments.get(0);
            return entityPropertyFunctionArgument.evaluate(context);
        } catch (Exception e) {
            throw new IllegalArgumentException("evaluate function: " + e.getMessage());
        }
    }

    @Override
    public boolean isNumericFunction(Object object) {
        return false;
    }


}
