package world.factors.function.impl;

import context.Context;
import world.factors.function.api.AbstractFunction;
import world.factors.function.api.FunctionType;
import world.factors.functionArgument.api.FunctionArgument;
import world.factors.functionArgument.impl.EntityPropertyFunctionArgument;

import java.util.List;

public class TicksFunction extends AbstractFunction {
    public TicksFunction(List<FunctionArgument> functionArguments) {
        super(FunctionType.TICKS, functionArguments, 1);
    }

    @Override
    public Object execute(Context context) {
        try {
            EntityPropertyFunctionArgument entityPropertyFunctionArgument = (EntityPropertyFunctionArgument) this.functionArguments.get(0);
            return context.getNumberOfTicksPropertyHasentChanged(entityPropertyFunctionArgument.getPropertyName());
        } catch (Exception e) {
            throw new IllegalArgumentException("ticks function: " + e.getMessage());
        }
    }

    @Override
    public boolean isNumericFunction(Object object) {
        return false;
    }
}
