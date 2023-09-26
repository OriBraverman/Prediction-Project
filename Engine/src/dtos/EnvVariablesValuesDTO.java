package dtos;

public class EnvVariablesValuesDTO {
    EnvVariableValueDTO[] envVariablesValues;

    public EnvVariablesValuesDTO(EnvVariableValueDTO[] envVariablesValues) {
        this.envVariablesValues = envVariablesValues;
    }

    public EnvVariableValueDTO[] getEnvVariablesValues() {
        return envVariablesValues;
    }
}
