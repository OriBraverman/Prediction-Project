package dtos;

public class EnvVariableDefinitionDTO {
    private String name;
    private String type;
    private String fromRange;
    private String toRange;

    public EnvVariableDefinitionDTO(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public EnvVariableDefinitionDTO(String name, String type, String fromRange, String toRange) {
        this.name = name;
        this.type = type;
        this.fromRange = fromRange;
        this.toRange = toRange;
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
}
