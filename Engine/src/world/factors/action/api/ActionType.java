package world.factors.action.api;

import world.factors.function.api.FunctionType;

import java.util.HashMap;
import java.util.Map;

public enum ActionType {
    INCREASE, DECREASE, CALCULATION, CONDITION, SET, KILL, PROXIMITY, REPLACE;
    private final static Map<String, ActionType> actionTypeMap = new HashMap<>();

    static {
        actionTypeMap.put("increase", INCREASE);
        actionTypeMap.put("decrease", DECREASE);
        actionTypeMap.put("calculation", CALCULATION);
        actionTypeMap.put("condition", CONDITION);
        actionTypeMap.put("set", SET);
        actionTypeMap.put("kill", KILL);
        actionTypeMap.put("proximity", PROXIMITY);
        actionTypeMap.put("replace", REPLACE);
    }

    public static boolean isActionType(String functionType) {
        return actionTypeMap.containsKey(functionType);
    }
    public static ActionType getActionType(String actionType) {
        if (!isActionType(actionType)) {
            throw new IllegalArgumentException("action type " + actionType + " does not exist");
        }
        return actionTypeMap.get(actionType);
    }
}
