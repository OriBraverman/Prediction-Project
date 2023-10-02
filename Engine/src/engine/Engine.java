package engine;

import dto.*;
import dto.gridView.GridViewDTO;
import dto.result.EntityPopulationByTicksDTO;
import dto.result.HistogramDTO;
import dto.result.PropertyAvaregeValueDTO;
import dto.result.PropertyConstistencyDTO;
import dto.world.WorldDTO;
import simulation.SimulationExecutionDetails;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

public interface Engine {

    void loadXML(Path xmlPath) throws FileNotFoundException;

    SimulationIDDTO activateSimulation(boolean isBonusActivated, int worldID, EnvVariablesValuesDTO envVariablesValuesDTO, EntitiesPopulationDTO entitiesPopulationDTO);

    WorldDTO getWorldDTO(int simulationID);

    void validateEnvVariableValue(int worldID, EnvVariableValueDTO envVariableValueDTO);

    SimulationIDListDTO getSimulationListDTO();

    boolean validateSimulationID(int userChoice);

    SimulationResultByAmountDTO getSimulationResultByAmountDTO(int simulationID);

    HistogramDTO getHistogramDTO(int simulationID, String entityName, String propertyName);

    boolean isXMLLoaded();

    NewExecutionInputDTO getNewExecutionInputDTO(int worldID);

    void validateEntitiesPopulation(EntitiesPopulationDTO entitiesPopulationDTO, int id);

    SimulationExecutionDetailsDTO getSimulationExecutionDetailsDTO(int simulationID);

    List<EntityPopulationDTO> getEntityPopulationDTOList(SimulationExecutionDetails simulationExecutionDetails);

    void stopSimulation(int simulationID);

    void pauseSimulation(int simulationID);

    void resumeSimulation(int simulationID);

    GridViewDTO getGridViewDTO(int simulationID);

    QueueManagementDTO getQueueManagementDTO();

    PropertyAvaregeValueDTO getPropertyAvaregeValueDTO(int currentSimulationID, String entityName, String propertyName);

    boolean isSimulationCompleted(int simulationID);

    PropertyConstistencyDTO getPropertyConsistencyDTO(int currentSimulationID, String entityName, String propertyName);

    EntityPopulationByTicksDTO getEntityPopulationByTicksDTO(int simulationID);

    EnvVariablesValuesDTO getEnvVariablesValuesDTO(int simulationID);

    EntitiesPopulationDTO getEntityPopulationDTO(int simulationID);

    void setPreviousTick(int simulationID);

    void getToNextTick(int simulationID);

    void deleteInDepthMemoryFolder();

    void stopThreadPool();

    void setThreadsCount(String threadsCount) throws NumberFormatException;
}
