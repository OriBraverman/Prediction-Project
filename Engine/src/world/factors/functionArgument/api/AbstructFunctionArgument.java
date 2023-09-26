package world.factors.functionArgument.api;

public abstract class AbstructFunctionArgument implements FunctionArgument {
    private final FunctionArgumentType functionArgumentType;
    private final String functionArgument;

    public AbstructFunctionArgument(FunctionArgumentType functionArgumentType, String functionArgument) {
        this.functionArgumentType = functionArgumentType;
        this.functionArgument = functionArgument;
    }

    public String getFunctionArgument() {
        return functionArgument;
    }

    public FunctionArgumentType getFunctionArgumentType() {
        return functionArgumentType;
    }
}
