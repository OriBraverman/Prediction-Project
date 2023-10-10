package dto;

import java.util.List;

public class EnvVariablesValuesDTO {
    List<EnvVariableValueDTO> envVariablesValues;

    public EnvVariablesValuesDTO(List<EnvVariableValueDTO> envVariablesValues) {
        this.envVariablesValues = envVariablesValues;
    }

    public List<EnvVariableValueDTO> getEnvVariablesValues() {
        return envVariablesValues;
    }

    public EnvVariableValueDTO getEnvVariableValueByName(String name) {
        return envVariablesValues.stream()
                .filter(envVariableValueDTO -> envVariableValueDTO.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
