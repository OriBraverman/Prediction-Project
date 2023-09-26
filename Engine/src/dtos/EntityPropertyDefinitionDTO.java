package dtos;

public class EntityPropertyDefinitionDTO {
    private String name;
    private String type;
    private String toRange;
    private String fromRange;
    private String valueGenerated;

    public EntityPropertyDefinitionDTO(String name, String type, String valueGenerated) {
        this.name = name;
        this.type = type;
        this.valueGenerated = valueGenerated;
    }

    public EntityPropertyDefinitionDTO(String name, String type, String toRange, String fromRange, String valueGenerated) {
        this.name = name;
        this.type = type;
        this.toRange = toRange;
        this.fromRange = fromRange;
        this.valueGenerated = valueGenerated;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getToRange() {
        return toRange;
    }

    public String getFromRange() {
        return fromRange;
    }

    public String getValueGenerated() {
        return valueGenerated;
    }
}
