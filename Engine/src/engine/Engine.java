package engine;

import convertor.Convertor;
import dtos.*;
import dtos.gridView.EntityInstanceDTO;
import dtos.gridView.GridViewDTO;
import dtos.result.EntityPopulationByTicksDTO;
import dtos.result.HistogramDTO;
import dtos.result.PropertyAvaregeValueDTO;
import dtos.result.PropertyConstistencyDTO;
import dtos.world.*;
import dtos.world.action.*;
import resources.schema.generatedWorld.PRDWorld;

import static java.util.Arrays.stream;
import static simulation.SimulationMemorySaver.deleteDirectory;
import static simulation.SimulationMemorySaver.readSEDByIdAndTick;
import static validator.StringValidator.validateStringIsInteger;
import static validator.XMLValidator.*;

import simulation.SimulationExecutionDetails;
import simulation.SimulationExecutionManager;
import world.World;
import world.factors.action.api.AbstractAction;
import world.factors.action.api.Action;
import world.factors.action.impl.*;
import world.factors.entity.definition.EntityDefinition;
import world.factors.entity.execution.EntityInstance;
import world.factors.entity.execution.manager.EntityInstanceManager;
import world.factors.entity.execution.manager.EntityInstanceManagerImpl;
import world.factors.entityPopulation.EntityPopulation;
import world.factors.environment.definition.impl.EnvVariableManagerImpl;
import world.factors.environment.execution.api.ActiveEnvironment;
import world.factors.property.definition.api.NumericPropertyDefinition;
import world.factors.property.definition.api.PropertyDefinition;
import world.factors.property.definition.impl.BooleanPropertyDefinition;
import world.factors.property.definition.impl.FloatPropertyDefinition;
import world.factors.property.definition.impl.IntegerPropertyDefinition;
import world.factors.property.execution.PropertyInstance;
import world.factors.property.execution.PropertyInstanceImpl;
import world.factors.rule.Rule;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;


public class Engine implements Serializable {
    private World world;
    private SimulationExecutionManager simulationExecutionManager;
    private ActiveEnvironment activeEnvironment;
    private EntityInstanceManager entityInstanceManager;

    public Engine() {
        this.world = null;
        this.simulationExecutionManager = null;
        this.activeEnvironment = null;
        this.entityInstanceManager = null;
    }

    private static PRDWorld fromXmlFileToObject(Path path) {
        try {
            File file = new File(path.toString());
            JAXBContext jaxbContext = JAXBContext.newInstance(PRDWorld.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            PRDWorld generatedWorld = (PRDWorld) jaxbUnmarshaller.unmarshal(file);
            return generatedWorld;

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void loadXML(Path xmlPath) throws FileNotFoundException {
        validateFileExists(xmlPath);
        validateFileIsXML(xmlPath);
        PRDWorld generatedWorld = fromXmlFileToObject(xmlPath);
        validateXMLContent(generatedWorld);
        Convertor convertor = new Convertor();
        convertor.setGeneratedWorld(generatedWorld);
        World tempWorld = convertor.convertPRDWorldToWorld();
        validateGrid(tempWorld.getGridDefinition());
        validateAllActionsReferToExistingEntities(tempWorld);
        validateAllActionsReferToExistingEntityProperties(tempWorld);
        validateMathActionHasNumericArgs(tempWorld.getRules(), tempWorld.getEntities(), (EnvVariableManagerImpl) tempWorld.getEnvironment());
        // if loaded successfully, clear the old engine and set the new one
        this.world = tempWorld;
        this.simulationExecutionManager = new SimulationExecutionManager(this.world.getThreadCount());
        this.activeEnvironment = null;
    }

    public EnvVariablesValuesDTO updateActiveEnvironmentAndInformUser(EnvVariablesValuesDTO envVariablesValuesDTO) {
        ActiveEnvironment activeEnvironment = this.world.getEnvironment().createActiveEnvironment();
        for (int i = 0; i < envVariablesValuesDTO.getEnvVariablesValues().length; i++) {
            EnvVariableValueDTO envVariableValueDTO = envVariablesValuesDTO.getEnvVariablesValues()[i];
            String value = envVariableValueDTO.getValue();
            PropertyDefinition propertyDefinition = this.world.getEnvironment().getPropertyDefinitionByName(envVariableValueDTO.getName());
            PropertyInstance propertyInstance;
            if (value.equals("")) {
                Object generatedValue = this.world.getEnvironment().getPropertyDefinitionByName(envVariableValueDTO.getName()).generateValue();
                propertyInstance = new PropertyInstanceImpl(propertyDefinition, generatedValue);
            } else {
                propertyInstance = new PropertyInstanceImpl(propertyDefinition, getPropertyFromString(value, propertyDefinition));
            }
            activeEnvironment.addPropertyInstance(propertyInstance);
            envVariablesValuesDTO.getEnvVariablesValues()[i] = new EnvVariableValueDTO(envVariableValueDTO.getName(), value.toString(), true);
        }
        this.activeEnvironment = activeEnvironment;
        return envVariablesValuesDTO;
    }

    private Object getPropertyFromString(String value, PropertyDefinition propertyDefinition) {
        if (propertyDefinition instanceof IntegerPropertyDefinition) {
            return Integer.parseInt(value);
        } else if (propertyDefinition instanceof FloatPropertyDefinition) {
            return Float.parseFloat(value);
        } else if (propertyDefinition instanceof BooleanPropertyDefinition) {
            return Boolean.parseBoolean(value);
        } else {
            return value;
        }
    }

    public SimulationIDDTO activateSimulation(boolean isBonusActivated) {
        int simulationId = this.simulationExecutionManager.createSimulation(this.world, this.activeEnvironment, this.entityInstanceManager, isBonusActivated);
        this.simulationExecutionManager.runSimulation(simulationId);
        return new SimulationIDDTO(simulationId);
    }

    public WorldDTO getWorldDTO() {
        List<PropertyDefinitionDTO> environment = getEnvironmentDTO();
        List<EntityDefinitionDTO> entities = getEntitiesDTO();
        List<RuleDTO> rules = getRulesDTO();
        TerminationDTO termination = getTerminationDTO();
        int gridWidth = this.world.getGridDefinition().getWidth();
        int gridHeight = this.world.getGridDefinition().getHeight();
        int threadCount = this.world.getThreadCount();
        return new WorldDTO(environment, entities, rules, termination, gridWidth, gridHeight, threadCount);
    }

    private List<PropertyDefinitionDTO> getEnvironmentDTO() {
        Collection<PropertyDefinition> envVariables = this.world.getEnvironment().getEnvVariables();
        List<PropertyDefinitionDTO> propertyDefinitionDTOS = new ArrayList<>();
        envVariables.forEach(propertyDefinition -> propertyDefinitionDTOS.add(getPropertyDefinitionDTO(propertyDefinition)));
        return propertyDefinitionDTOS;
    }

    private TerminationDTO getTerminationDTO() {
        boolean isByUser = this.world.getTermination().isByUser();
        int secondsCount = this.world.getTermination().getSecondsCount();
        int ticksCount = this.world.getTermination().getTicksCount();
        return new TerminationDTO(isByUser, secondsCount, ticksCount);
    }

    private List<RuleDTO> getRulesDTO() {
        List<Rule> rules = this.world.getRules();
        List<RuleDTO> ruleDTOS = new ArrayList<>();
        rules.forEach(rule -> ruleDTOS.add(getRuleDTO(rule)));
        return ruleDTOS;
    }

    private RuleDTO getRuleDTO(Rule rule) {
        String name = rule.getName();
        ActivationDTO activationDTO = new ActivationDTO(rule.getActivation().getTicks(), rule.getActivation().getProbabilty());
        int numberOfActions = rule.getActionsToPerform().size();
        List<AbstructActionDTO> actions = new ArrayList<>();
        rule.getActionsToPerform().forEach(action -> actions.add(getAbstructActionDTO(action)));
        return new RuleDTO(name, activationDTO, numberOfActions, actions);
    }

    private AbstructActionDTO getAbstructActionDTO(Action action) {
        String propName, value;
        List<AbstructActionDTO> thenActions;
        switch (action.getActionType()) {
            case INCREASE:
                IncreaseAction increaseAction = (IncreaseAction) action;
                propName = increaseAction.getProperty().toString();
                value = increaseAction.getByExpression().toString();
                return new IncreaseActionDTO(getEntityDefinitionDTO(action), propName, value);
            case DECREASE:
                DecreaseAction decreaseAction = (DecreaseAction) action;
                propName = decreaseAction.getProperty().toString();
                value = decreaseAction.getByExpression().toString();
                return new DecreaseActionDTO(getEntityDefinitionDTO(action), propName, value);
            case CALCULATION:
                CalculationAction calculationAction = (CalculationAction) action;
                String resultProperty = calculationAction.getResultProperty().toString();
                String calculationExpression = calculationAction.getCalculationExpression();
                return new CalculationActionDTO(getEntityDefinitionDTO(action), resultProperty, calculationExpression);
            case CONDITION:
                ConditionAction conditionAction = (ConditionAction) action;
                String conditionExpression = conditionAction.getCondition();
                thenActions = getAbstructActionsDTO(conditionAction.getThenActions());
                if (conditionAction.getElseActions() != null) {
                    List<AbstructActionDTO> elseActions = getAbstructActionsDTO(conditionAction.getElseActions());
                    return new ConditionActionDTO(getEntityDefinitionDTO(action), conditionExpression, thenActions, elseActions);
                } else {
                    return new ConditionActionDTO(getEntityDefinitionDTO(action), conditionExpression, thenActions, null);
                }
            case KILL:
                return new KillActionDTO(getEntityDefinitionDTO(action));
            case REPLACE:
                ReplaceAction replaceAction = (ReplaceAction) action;
                EntityDefinitionDTO createEntityDefinitionDTO = getEntityDefinitionDTO(replaceAction.getCreateEntityDefinition());
                String mode = replaceAction.getMode().toString();
                return new ReplaceActionDTO(getEntityDefinitionDTO(action), createEntityDefinitionDTO, mode);
            case PROXIMITY:
                ProximityAction proximityAction = (ProximityAction) action;
                EntityDefinitionDTO targetEntityDefinitionDTO = getEntityDefinitionDTO(proximityAction.getSecondaryEntity().getSecondaryEntityDefinition());
                String ofValue = proximityAction.getStringOf();
                thenActions = getAbstructActionsDTO(proximityAction.getThenActions());
                return new ProximityActionDTO(getEntityDefinitionDTO(action), targetEntityDefinitionDTO, ofValue, thenActions);
            case SET:
                SetAction setAction = (SetAction) action;
                String propertyName = setAction.getProperty();
                value = setAction.getValue();
                return new SetActionDTO(getEntityDefinitionDTO(action), propertyName, value);
            default:
                return null;
        }
    }

    private List<AbstructActionDTO> getAbstructActionsDTO(List<AbstractAction> thenActions) {
        List<AbstructActionDTO> abstructActionDTOS = new ArrayList<>();
        thenActions.forEach(action -> abstructActionDTOS.add(getAbstructActionDTO(action)));
        return abstructActionDTOS;
    }

    private EntityDefinitionDTO getEntityDefinitionDTO(Action action) {
        EntityDefinition entityDefinition = action.getPrimaryEntityDefinition();
        return getEntityDefinitionDTO(entityDefinition);
    }

    private EntityDefinitionDTO getEntityDefinitionDTO(EntityDefinition entityDefinition) {
        String name = entityDefinition.getName();
        List<PropertyDefinitionDTO> entityPropertyDefinitionDTOS = new ArrayList<>();
        entityDefinition.getProps().forEach(propertyDefinition -> entityPropertyDefinitionDTOS.add(getPropertyDefinitionDTO(propertyDefinition)));
        return new EntityDefinitionDTO(name, entityPropertyDefinitionDTOS);
    }

    private PropertyDefinitionDTO getPropertyDefinitionDTO(PropertyDefinition propertyDefinition) {
        String name = propertyDefinition.getName();
        String type = propertyDefinition.getType().toString();
        boolean randomInit = propertyDefinition.isRandomInit();
        if (propertyDefinition instanceof NumericPropertyDefinition) {
            NumericPropertyDefinition property = (NumericPropertyDefinition) propertyDefinition;
            String fromRange = property.getRange().getFrom().toString();
            String toRange = property.getRange().getTo().toString();
            if (!randomInit)
                return new PropertyDefinitionDTO(name, type, fromRange, toRange, property.generateValue().toString(), randomInit);
            return new PropertyDefinitionDTO(name, type, fromRange, toRange, null, randomInit);
        }
        if (!randomInit)
            return new PropertyDefinitionDTO(name, type, null, null, propertyDefinition.generateValue().toString(), randomInit);
        return new PropertyDefinitionDTO(name, type, null, null, null, randomInit);
    }

    private List<EntityDefinitionDTO> getEntitiesDTO() {
        List<EntityDefinition> entities = this.world.getEntities();
        List<EntityDefinitionDTO> entityDefinitionDTOS = new ArrayList<>();
        entities.forEach(entityDefinition -> entityDefinitionDTOS.add(getEntityDTO(entityDefinition)));
        return entityDefinitionDTOS;
    }

    private EntityDefinitionDTO getEntityDTO(EntityDefinition entityDefinition) {
        String name = entityDefinition.getName();
        List<PropertyDefinitionDTO> entityPropertyDefinitionDTOS = getEntityPropertyDefinitionDTOS(entityDefinition.getProps());
        return new EntityDefinitionDTO(name, entityPropertyDefinitionDTOS);
    }

    private List<PropertyDefinitionDTO> getEntityPropertyDefinitionDTOS(List<PropertyDefinition> properties) {
        List<PropertyDefinitionDTO> entityPropertyDefinitionDTOS = new ArrayList<>();
        properties.forEach(propertyDefinition -> entityPropertyDefinitionDTOS.add(getPropertyDefinitionDTO(propertyDefinition)));
        return entityPropertyDefinitionDTOS;
    }

    public void validateEnvVariableValue(EnvVariableValueDTO envVariableValueDTO) {
        PropertyDefinition propertyDefinition = this.world.getEnvironment().getPropertyDefinitionByName(envVariableValueDTO.getName());
        if (propertyDefinition instanceof IntegerPropertyDefinition) {
            IntegerPropertyDefinition property = (IntegerPropertyDefinition) propertyDefinition;
            if(!property.isInRange(envVariableValueDTO.getValue())) {
                throw new IllegalArgumentException("Property " + envVariableValueDTO.getName() + " is not in range");
            }
        } else if (propertyDefinition instanceof FloatPropertyDefinition) {
            FloatPropertyDefinition property = (FloatPropertyDefinition) propertyDefinition;
            if(!property.isInRange(envVariableValueDTO.getValue())) {
                throw new IllegalArgumentException("Property " + envVariableValueDTO.getName() + " is not in range");
            }
        }
        if (!propertyDefinition.getType().isMyType(envVariableValueDTO.getValue())) {
            throw new IllegalArgumentException("Invalid value for env variable: " + envVariableValueDTO.getName());
        }
    }

    public SimulationIDListDTO getSimulationListDTO() {
        return this.simulationExecutionManager.getSimulationIDListDTO();
    }

    public boolean validateSimulationID(int userChoice) {
        return this.simulationExecutionManager.isSimulationIDExists(userChoice);
    }

    public SimulationResultByAmountDTO getSimulationResultByAmountDTO(int simulationID) {
        SimulationExecutionDetails simulationExecutionDetails = this.simulationExecutionManager.getSimulationDetailsByID(simulationID);
        return new SimulationResultByAmountDTO(simulationExecutionDetails.getId(), getEntityResultsDTO(simulationExecutionDetails));
    }

    private EntityResultDTO[] getEntityResultsDTO(SimulationExecutionDetails simulationExecutionDetails) {
        //use stream
        EntityResultDTO[] entityResultDTOS = stream(this.world.getEntities().toArray())
                .map(entityDefinition -> {
                    String name = ((EntityDefinition) entityDefinition).getName();
                    int startingPopulation  = this.entityInstanceManager.getPopulationByEntityDefinition((EntityDefinition) entityDefinition);
                    int endingPopulation = simulationExecutionDetails.getEntityInstanceManager().getEntityCountByName(name);
                    return new EntityResultDTO(name, startingPopulation, endingPopulation);
                })
                .toArray(EntityResultDTO[]::new);
        return entityResultDTOS;
    }


    public HistogramDTO getHistogramDTO(int simulationID, String entityName, String propertyName) {
        Map<Object, Integer> histogram = new HashMap<>();
        EntityDefinition entityDefinition = this.world.getEntityByName(entityName);
        PropertyDefinition propertyDefinition = entityDefinition.getPropertyDefinitionByName(propertyName);

        // Create a copy of the list to avoid ConcurrentModificationException
        List<EntityInstance> instancesCopy = new ArrayList<>(this.simulationExecutionManager.getSimulationDetailsByID(simulationID).getEntityInstanceManager().getInstances());

        for (EntityInstance entityInstance : instancesCopy) {
            if (entityInstance.getEntityDefinition().getName().equals(entityName)) {
                // we already know that the property is there
                PropertyInstance propertyInstance = entityInstance.getPropertyByName(propertyName);
                Object value = propertyInstance.getValue();
                if (histogram.containsKey(value)) {
                    histogram.put(value, histogram.get(value) + 1);
                } else {
                    histogram.put(value, 1);
                }
            }
        }
        return new HistogramDTO(histogram);
    }

    public boolean isXMLLoaded() {
        return this.world != null;
    }

    public NewExecutionInputDTO getNewExecutionInputDTO() {
        List<PropertyDefinitionDTO> envVariables = getEnvironmentDTO();
        List<EntityDefinitionDTO> entityDefinitionDTOS = getEntitiesDTO();
        return new NewExecutionInputDTO(envVariables, entityDefinitionDTOS);
    }

    public void updateActiveEntityPopulation(EntitiesPopulationDTO entitiesPopulationDTO) {
        EntityInstanceManager entityInstanceManager = new EntityInstanceManagerImpl();
        for (EntityDefinition entityDefinition : world.getEntities()) {
            EntityPopulationDTO entityPopulation = entitiesPopulationDTO.getEntityPopulationByName(entityDefinition.getName());
            String value;
            if (entityPopulation.hasValue()) {
                value = entityPopulation.getPopulation();
                entityInstanceManager.addEntityDefinitionPopulation(entityDefinition, Integer.parseInt(value));
            } else {
                entityInstanceManager.addEntityDefinitionPopulation(entityDefinition, 0);
            }
        }
        this.entityInstanceManager = entityInstanceManager;
    }

    public void validateEntitiesPopulation(EntitiesPopulationDTO entitiesPopulationDTO) {
        int MaxPopulation = this.world.getGridDefinition().getHeight() * this.world.getGridDefinition().getWidth();
        int totalPopulation = 0;
        for (EntityPopulationDTO entityPopulation : entitiesPopulationDTO.getEntitiesPopulation()) {
            EntityDefinition entityDefinition = this.world.getEntityByName(entityPopulation.getName());
            if (entityPopulation.hasValue()) {
                String value = entityPopulation.getPopulation();
                if (!validateStringIsInteger(value)) {
                    throw new IllegalArgumentException("Invalid value type for entity: " + entityPopulation.getName());
                }
                int population = Integer.parseInt(value);
                if (population < 0) {
                    throw new IllegalArgumentException("A negative value for entity: " + entityPopulation.getName());
                } else if (population > MaxPopulation) {
                    throw new IllegalArgumentException("A value bigger than the maximum population(" + MaxPopulation + ") for entity: " + entityPopulation.getName());
                }
                totalPopulation += Integer.parseInt(value);
            }
        }
        if (totalPopulation > MaxPopulation) {
            throw new IllegalArgumentException("Total population(" + totalPopulation + ") is bigger than the maximum population(" + MaxPopulation + ")");
        }
    }

    public SimulationExecutionDetailsDTO getSimulationExecutionDetailsDTO(int simulationID) {
        SimulationExecutionDetails simulationExecutionDetails = this.simulationExecutionManager.getSimulationDetailsByID(simulationID);
        boolean ticks = simulationExecutionDetails.isTerminatedByTicksCount();
        boolean seconds = simulationExecutionDetails.isTerminatedBySecondsCount();
        boolean isRunning = simulationExecutionDetails.isRunning();
        boolean isPaused = simulationExecutionDetails.isPaused();
        boolean isCompleted = simulationExecutionDetails.isCompleted();
        int entitiesCount = simulationExecutionDetails.getEntityInstanceManager().getAliveEntityCount();
        List<EntityPopulationDTO> entitiesPopulation = getEntityPopulationDTOList(simulationExecutionDetails);
        int currentTick = simulationExecutionDetails.getCurrentTick();
        long secondsPassed = simulationExecutionDetails.getSimulationSeconds();
        String status = simulationExecutionDetails.getStatus();
        String terminationReason = simulationExecutionDetails.getTerminationReason();
        return new SimulationExecutionDetailsDTO(simulationID, seconds, ticks, isRunning, isPaused, isCompleted, entitiesCount, entitiesPopulation, currentTick, secondsPassed, status, terminationReason);
    }

    public List<EntityPopulationDTO> getEntityPopulationDTOList(SimulationExecutionDetails simulationExecutionDetails) {
        List<EntityInstance> instancesCopy;
        synchronized (simulationExecutionDetails.getEntityInstanceManager()) {
            instancesCopy = new ArrayList<>(simulationExecutionDetails.getEntityInstanceManager().getInstances());
        }
        return instancesCopy.stream().filter(Objects::nonNull)
                .map(entityInstance -> entityInstance.getEntityDefinition().getName())
                .collect(Collectors.toMap(entityName -> entityName, entityName -> 1, Integer::sum))
                .entrySet().stream()
                .map(entry -> new EntityPopulationDTO(entry.getKey(), entry.getValue().toString(), true))
                .collect(Collectors.toList());
    }

    public void stopSimulation(int simulationID) {
        this.simulationExecutionManager.stopSimulation(simulationID);
    }

    public void pauseSimulation(int simulationID) {
        this.simulationExecutionManager.pauseSimulation(simulationID);
    }

    public void resumeSimulation(int simulationID) {
        this.simulationExecutionManager.resumeSimulation(simulationID);
    }

    public GridViewDTO getGridViewDTO(int simulationID) {
        SimulationExecutionDetails simulationExecutionDetails = this.simulationExecutionManager.getSimulationDetailsByID(simulationID);
        int gridWidth = simulationExecutionDetails.getWorld().getGridDefinition().getWidth();
        int gridHeight = simulationExecutionDetails.getWorld().getGridDefinition().getHeight();
        List<EntityInstanceDTO> entityInstanceDTOS = new ArrayList<>();
        simulationExecutionDetails.getEntityInstanceManager().getInstances()
                .forEach(entityInstance -> entityInstanceDTOS.add(new EntityInstanceDTO(entityInstance.getEntityDefinition().getName(), entityInstance.getCoordinate().getX(), entityInstance.getCoordinate().getY())));
        List<String> entityNames = simulationExecutionDetails.getWorld().getEntities()
                .stream().map(EntityDefinition::getName).collect(Collectors.toList());
        return new GridViewDTO(gridWidth, gridHeight, entityInstanceDTOS, entityNames);
    }

    public QueueManagementDTO getQueueManagementDTO() {
        if (this.simulationExecutionManager == null) {
            return new QueueManagementDTO(0, 0, 0);
        }
        int pendingSimulations = this.simulationExecutionManager.getPendingSimulationsCount();
        int activeSimulations = this.simulationExecutionManager.getActiveSimulationsCount();
        int completedSimulations = this.simulationExecutionManager.getCompletedSimulationsCount();
        return new QueueManagementDTO(pendingSimulations, activeSimulations, completedSimulations);
    }

    public PropertyAvaregeValueDTO getPropertyAvaregeValueDTO(int currentSimulationID, String entityName, String propertyName){
        List<EntityInstance> tempInstances;
        SimulationExecutionDetails simulationExecutionDetails = this.simulationExecutionManager.getSimulationDetailsByID(currentSimulationID);
        synchronized (simulationExecutionDetails.getEntityInstanceManager()) {
            tempInstances = new ArrayList<>(simulationExecutionDetails.getEntityInstanceManager()
                    .getEntityInstancesByName(entityName));
        }
        float sumValues = 0;
        for (EntityInstance entityInstance: tempInstances){
            PropertyInstance propertyInstance = entityInstance.getPropertyByName(propertyName);
            if(!(propertyInstance.getValue()  instanceof Number)){
                return new PropertyAvaregeValueDTO(currentSimulationID, entityName, propertyName, "Not a numeric value ");
            }
            sumValues += (float)(propertyInstance.getValue());
        }
        float avgValue = sumValues / tempInstances.size();
        return new PropertyAvaregeValueDTO(currentSimulationID, entityName, propertyName, String.valueOf(avgValue));
    }

    public boolean isSimulationCompleted(int simulationID) {
        return this.simulationExecutionManager.isSimulationCompleted(simulationID);
    }

    public PropertyConstistencyDTO getPropertyConsistencyDTO(int currentSimulationID, String entityName, String propertyName) {
        SimulationExecutionDetails simulationExecutionDetails = this.simulationExecutionManager.getSimulationDetailsByID(currentSimulationID);
        List<EntityInstance> instancesCopy;
        synchronized (simulationExecutionDetails.getEntityInstanceManager()) {
            instancesCopy = new ArrayList<>(simulationExecutionDetails.getEntityInstanceManager()
                    .getEntityInstancesByName(entityName));
        }
        int sumConsistency = 0;
        for (EntityInstance entityInstance : instancesCopy) {
            PropertyInstance propertyInstance = entityInstance.getPropertyByName(propertyName);
            if (propertyInstance == null) {
                throw new IllegalArgumentException("Property " + propertyName + " does not exist in entity " + entityName);
            }
            sumConsistency += propertyInstance.getConsistency(simulationExecutionDetails.getCurrentTick());

        }
        if (instancesCopy.isEmpty()) {
            return new PropertyConstistencyDTO(currentSimulationID, entityName, propertyName, "0");
        } else {
            return new PropertyConstistencyDTO(currentSimulationID, entityName, propertyName, String.valueOf(sumConsistency / instancesCopy.size()));
        }
    }

    private Map<Integer, List<EntityPopulationDTO>> getEntityPopulationByTicks(int simulationID)
    {
        Map<Integer, List<EntityPopulationDTO>> res = new HashMap<>();
        SimulationExecutionDetails simulationExecutionDetails = this.simulationExecutionManager.getSimulationDetailsByID(simulationID);
        Map<Integer, List<EntityPopulation>> entityPopulationByTicks = simulationExecutionDetails.getEntityPopulationByTicks();
        entityPopulationByTicks.forEach((tick, entityPopulationList) -> {
            List<EntityPopulationDTO> entityPopulationDTOS = new ArrayList<>();
            entityPopulationList.forEach(entityPopulation -> entityPopulationDTOS
                    .add(new EntityPopulationDTO(entityPopulation.getEntityName(), Integer.toString(entityPopulation.getPopulation()), true)));
            res.put(tick, entityPopulationDTOS);
        });
        return res;
    }
    public EntityPopulationByTicksDTO getEntityPopulationByTicksDTO(int simulationID) {
        SimulationExecutionDetails simulationExecutionDetails = this.simulationExecutionManager.getSimulationDetailsByID(simulationID);
        Map<Integer, List<EntityPopulationDTO>> entityPopulationByTicks = getEntityPopulationByTicks(simulationID);
        List<String> entityNames = simulationExecutionDetails.getWorld().getEntities().stream()
                .map(EntityDefinition::getName).collect(Collectors.toList());
        if (simulationExecutionDetails.getEntityPopulationByTicks().size() < 100) {

            return new EntityPopulationByTicksDTO(entityPopulationByTicks, entityNames);
        } else {
            // there are more than 100 ticks
            // take items every simulationExecutionDetails.getCurrentTick()/1000 ticks
            // this will get us 100 items for the graph
            Map<Integer, List<EntityPopulationDTO>> takenTicksEntityPopulation = entityPopulationByTicks
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getKey() % (simulationExecutionDetails.getCurrentTick() / 100) == 0)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            return new EntityPopulationByTicksDTO(takenTicksEntityPopulation, entityNames);
        }
    }

    public EnvVariablesValuesDTO getEnvVariablesValuesDTO(int simulationID) {
        SimulationExecutionDetails simulationExecutionDetails = this.simulationExecutionManager.getSimulationDetailsByID(simulationID);
        List<PropertyInstance> envVariables = simulationExecutionDetails.getActiveEnvironment().getEnvVariables();
        EnvVariableValueDTO[] envVariableValueDTOS = new EnvVariableValueDTO[envVariables.size()];
        for (int i = 0; i < envVariables.size(); i++) {
            PropertyInstance propertyInstance = envVariables.get(i);
            envVariableValueDTOS[i] = new EnvVariableValueDTO(propertyInstance.getPropertyDefinition().getName(), propertyInstance.getValue().toString(), true);
        }
        return new EnvVariablesValuesDTO(envVariableValueDTOS);
    }

    public EntitiesPopulationDTO getEntityPopulationDTO(int simulationID) {
        SimulationExecutionDetails simulationExecutionDetails = this.simulationExecutionManager.getSimulationDetailsByID(simulationID);
        Map<EntityDefinition, Integer> entityPopulationMap = simulationExecutionDetails.getEntityInstanceManager().getEntityPopulationMap();
        List<EntityPopulationDTO> entityPopulationDTOS = new ArrayList<>();
        entityPopulationMap
                .forEach((entityDefinition, population) -> entityPopulationDTOS.add(new EntityPopulationDTO(entityDefinition.getName(), population.toString(), true)));
        return new EntitiesPopulationDTO(entityPopulationDTOS);
    }

    public void setPreviousTick(int simulationID) {
        int currentTick = this.simulationExecutionManager.getSimulationDetailsByID(simulationID).getCurrentTick();
        if (currentTick > 0) {
            SimulationExecutionDetails simulationExecutionDetails = readSEDByIdAndTick(simulationID, currentTick - 1);
            this.simulationExecutionManager.setSEDById(simulationID, simulationExecutionDetails);
        }
    }

    public void getToNextTick(int simulationID) {
        this.simulationExecutionManager.getToNextTick(simulationID);
    }

    public void deleteInDepthMemoryFolder() {
        deleteDirectory();
    }

    public void stopThreadPool() {
        if (this.simulationExecutionManager != null) {
            this.simulationExecutionManager.stopThreadPool();
        }
    }
}


