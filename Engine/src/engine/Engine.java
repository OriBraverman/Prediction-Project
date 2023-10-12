package engine;

import dto.*;
import dto.gridView.GridViewDTO;
import dto.result.*;
import dto.world.WorldDTO;
import dto.world.WorldsDTO;
import http.url.Client;
import requests.UserRequest;
import simulation.SimulationExecutionDetails;
import user.manager.UsersManager;
import world.definition.World;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

public interface Engine {

    void loadXML(String xmlPath) throws FileNotFoundException;

    SimulationIDDTO activateSimulation(ActivateSimulationDTO activateSimulationDTO);

    WorldDTO getWorldDTO(int simulationID);

    WorldDTO getWorldDTO(World world);

    void validateEnvVariablesValues(String worldName, EnvVariablesValuesDTO envVariablesValuesDTO);


    boolean validateSimulationID(int userChoice);

    SimulationResultByAmountDTO getSimulationResultByAmountDTO(int simulationID);

    HistogramDTO getHistogramDTO(int simulationID, String entityName, String propertyName);

    boolean isXMLLoaded();

    NewExecutionInputDTO getNewExecutionInputDTO(int requestID);

    void validateEntitiesPopulation(EntitiesPopulationDTO entitiesPopulationDTO, String worldName);

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

    WorldsDTO getWorldsDTO();

    RequestsDTO getRequestsDTO(String usernameFromSession, Client typeOfClient);

    RequestDTO getRequestDTO(UserRequest userRequest);

    void submitRequest(RequestDTO requestDTO);

    void updateRequestStatus(int requestIDInt, String status);

    SimulationIDListDTO getSimulationIDListDTO(Client typeOfClient, String usernameFromSession);

    PropertyStatisticsDTO getPropertyStatisticsDTO(int simulationID, String entityName, String propertyName);

    NewExecutionValuesDTO getNewExecutionValuesDTO(int simulationID);
}
