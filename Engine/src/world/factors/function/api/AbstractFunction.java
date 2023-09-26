package world.factors.function.api;

import world.factors.functionArgument.api.FunctionArgument;

import java.util.List;

public abstract class AbstractFunction implements Function {
    protected final FunctionType functionType;
    protected final List<FunctionArgument> functionArguments;
    protected final int numArgs;

    public AbstractFunction(FunctionType functionType, List<FunctionArgument> functionArguments, int numArgs) {
        this.functionType = functionType;
        this.functionArguments = functionArguments;
        this.numArgs = numArgs;
    }

    @Override
    public FunctionType getFunctionType() {
        return this.functionType;
    }

    @Override
    public int getNumArguments() {
        return this.numArgs;
    }
}
