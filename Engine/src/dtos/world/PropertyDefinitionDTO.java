package dtos.world;

public class PropertyDefinitionDTO {
    private final String name;
    private final String type;
    private final String fromRange;
    private final String toRange;
    private final String defaultVal;
    private final boolean randomInit;

    public PropertyDefinitionDTO(String name, String type, String fromRange, String toRange, String defaultVal, boolean randomInit) {
        this.name = name;
        this.type = type;
        this.fromRange = fromRange;
        this.toRange = toRange;
        this.defaultVal = defaultVal;
        this.randomInit = randomInit;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getFromRange() {
        return fromRange;
    }

    public String getToRange() {
        return toRange;
    }

    public String getDefaultVal() {
        return defaultVal;
    }

    public boolean isRandomInit() {
        return randomInit;
    }
}
