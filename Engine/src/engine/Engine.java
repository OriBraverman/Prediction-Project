package engine;

import dtos.*;
import dtos.gridView.GridViewDTO;
import dtos.result.EntityPopulationByTicksDTO;
import dtos.result.HistogramDTO;
import dtos.result.PropertyAvaregeValueDTO;
import dtos.result.PropertyConstistencyDTO;
import dtos.world.WorldDTO;
import simulation.SimulationExecutionDetails;
import world.definition.World;
import world.execution.WorldInstance;

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
