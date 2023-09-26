package world.factors.functionArgument.impl;

import context.Context;
import world.factors.functionArgument.api.AbstructFunctionArgument;
import world.factors.functionArgument.api.FunctionArgumentType;

public class EnvPropertyFunctionArgument extends AbstructFunctionArgument {
    private final String envPropertyName;

    public EnvPropertyFunctionArgument(String functionArgument) {
        super(FunctionArgumentType.ENV_PROPERTY_NAME, functionArgument);
        this.envPropertyName = functionArgument;
    }

    public String getEnvPropertyName() {
        return envPropertyName;
    }

    @Override
    public Object evaluate(Context context) {
        return context.getEnvironment().getProperty(this.envPropertyName).getValue();
    }
}
