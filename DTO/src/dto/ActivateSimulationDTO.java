package dto;


public class ActivateSimulationDTO {
    private final int requestID;
    private final EnvVariablesValuesDTO envVariablesValuesDTO;
    private final EntitiesPopulationDTO entityPopulationDTO;

    public ActivateSimulationDTO(int requestID, EnvVariablesValuesDTO envVariablesValuesDTO, EntitiesPopulationDTO entityPopulationDTO) {
        this.requestID = requestID;
        this.envVariablesValuesDTO = envVariablesValuesDTO;
        this.entityPopulationDTO = entityPopulationDTO;
    }

    public int getRequestID() {
        return requestID;
    }

    public EnvVariablesValuesDTO getEnvVariablesValuesDTO() {
        return envVariablesValuesDTO;
    }

    public EntitiesPopulationDTO getEntityPopulationDTO() {
        return entityPopulationDTO;
    }
}
