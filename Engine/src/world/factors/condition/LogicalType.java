package world.factors.condition;

import java.util.HashMap;
import java.util.Map;

public enum LogicalType {
    AND, OR;
    private final static Map<String, LogicalType> logicalTypeMap = new HashMap<>();
    static {
        logicalTypeMap.put("and", AND);
        logicalTypeMap.put("or", OR);
    }
    public static boolean isLogicalType(String logicalType) {
        return logicalTypeMap.containsKey(logicalType);
    }
    public static LogicalType getLogicalType(String logicalType) {
        if (!isLogicalType(logicalType)) {
            throw new IllegalArgumentException("logical type " + logicalType + " does not exist");
        }
        return logicalTypeMap.get(logicalType);
    }
}
