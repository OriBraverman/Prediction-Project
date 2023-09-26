package world.factors.function.impl;

import context.Context;
import world.factors.environment.definition.api.EnvVariablesManager;
import world.factors.function.api.AbstractFunction;
import world.factors.functionArgument.api.FunctionArgument;
import world.factors.function.api.FunctionType;
import world.factors.functionArgument.impl.EnvPropertyFunctionArgument;

import java.util.List;

public class EnvironmentFunction extends AbstractFunction {
    public EnvironmentFunction(List<FunctionArgument> functionArguments) {
        super(FunctionType.ENVIRONMENT, functionArguments, 1);
    }

    @Override
    public Object execute(Context context) {
        EnvPropertyFunctionArgument envPropertyFunctionArgument = (EnvPropertyFunctionArgument) this.functionArguments.get(0);
        return envPropertyFunctionArgument.evaluate(context);
    }

    public boolean isNumericFunction(Object object) {
        EnvPropertyFunctionArgument envPropertyFunctionArgument = (EnvPropertyFunctionArgument) this.functionArguments.get(0);
        EnvVariablesManager envVariablesManager = (EnvVariablesManager) object;
        return envVariablesManager.isNumericProperty(envPropertyFunctionArgument.getEnvPropertyName());
    }
}
