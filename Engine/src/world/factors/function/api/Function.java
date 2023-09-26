package world.factors.function.api;

import context.Context;
import world.factors.environment.definition.api.EnvVariablesManager;

import java.util.ArrayList;

public interface Function {
    FunctionType getFunctionType();
    Object execute(Context context);
    int getNumArguments();
    boolean isNumericFunction(Object object);
}
