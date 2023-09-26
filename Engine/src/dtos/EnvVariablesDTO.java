package dtos;

public class EnvVariablesDTO {
    EnvVariableDefinitionDTO[] envVariables;

    public EnvVariablesDTO(EnvVariableDefinitionDTO[] envVariables) {
        this.envVariables = envVariables;
    }

    public EnvVariableDefinitionDTO[] getEnvVariables() {
        return envVariables;
    }
}
