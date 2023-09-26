package world.factors.condition;

import world.factors.function.api.FunctionType;

import java.util.HashMap;
import java.util.Map;

public enum OperatorType {
    EQUALS, NOT_EQUALS, BIGGER_THAN, LOWER_THAN;
    private final static Map<String, OperatorType> operatorTypeMap = new HashMap<>();
    static {
        operatorTypeMap.put("=", EQUALS);
        operatorTypeMap.put("!=", NOT_EQUALS);
        operatorTypeMap.put("bt", BIGGER_THAN);
        operatorTypeMap.put("lt", LOWER_THAN);
    }
    public static boolean isOperatorType(String operatorType) {
        return operatorTypeMap.containsKey(operatorType);
    }
    public static OperatorType getOperatorType(String operatorType) {
        if (!isOperatorType(operatorType)) {
            throw new IllegalArgumentException("operator type " + operatorType + " does not exist");
        }
        return operatorTypeMap.get(operatorType);
    }
}
