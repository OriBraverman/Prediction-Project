package dtos;

public class EnvVariableValueDTO {
    private final String name;
    private final String value;
    private final boolean hasValue;


    public EnvVariableValueDTO(String name, String value, boolean hasValue) {
        this.name = name;
        this.value = value;
        this.hasValue = hasValue;
    }

    public boolean hasValue() {
        return hasValue;
    }

    public String getName() {
        return name;
    }

    public String getValue() { return value; }
}
