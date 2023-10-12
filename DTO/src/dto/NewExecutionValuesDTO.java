package dto;

public class NewExecutionValuesDTO {
    private EnvVariablesValuesDTO envVariablesValuesDTO;
    private EntitiesPopulationDTO entityPopulationDTO;

    public NewExecutionValuesDTO(EnvVariablesValuesDTO envVariablesValuesDTO, EntitiesPopulationDTO entityPopulationDTO) {
        this.envVariablesValuesDTO = envVariablesValuesDTO;
        this.entityPopulationDTO = entityPopulationDTO;
    }

    public EnvVariablesValuesDTO getEnvVariablesValuesDTO() {
        return envVariablesValuesDTO;
    }

    public EntitiesPopulationDTO getEntityPopulationDTO() {
        return entityPopulationDTO;
    }
}
