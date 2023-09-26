package world.factors.action.impl;

import world.factors.action.api.ActionType;

import java.util.HashMap;
import java.util.Map;

public enum ReplaceType {
    SCRATCH, DERIVED;
    private final static Map<String, ReplaceType> replaceTypeMap = new HashMap<>();

    static {
        replaceTypeMap.put("scratch", SCRATCH);
        replaceTypeMap.put("derived", DERIVED);
    }

    public static boolean isReplaceType(String replaceType) {
        return replaceTypeMap.containsKey(replaceType);
    }

    public static ReplaceType getReplaceType(String replaceType) {
        if (!isReplaceType(replaceType)) {
            throw new IllegalArgumentException("replace type " + replaceType + " does not exist");
        }
        return replaceTypeMap.get(replaceType);
    }
}
