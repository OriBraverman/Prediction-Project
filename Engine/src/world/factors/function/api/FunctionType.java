package world.factors.function.api;

import java.util.HashMap;
import java.util.Map;

public enum FunctionType {
    ENVIRONMENT, RANDOM, EVALUATE, PERCENT, TICKS;

    //create a map of function types to string names

    private final static Map<String, FunctionType> functionTypeMap = new HashMap<>();

    static {
        functionTypeMap.put("environment", ENVIRONMENT);
        functionTypeMap.put("random", RANDOM);
        functionTypeMap.put("evaluate", EVALUATE);
        functionTypeMap.put("percent", PERCENT);
        functionTypeMap.put("ticks", TICKS);
    }

    public static boolean isFunctionType(String functionType) {
        return functionTypeMap.containsKey(functionType);
    }
    public static FunctionType getFunctionType(String functionType) {
        if (!isFunctionType(functionType)) {
            throw new IllegalArgumentException("function type " + functionType + " does not exist");
        }
        return functionTypeMap.get(functionType);
    }
}
