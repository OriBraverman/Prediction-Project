package validator;

import resources.schema.generatedWorld.*;
import world.World;
import world.factors.action.api.Action;
import world.factors.action.api.ActionType;
import world.factors.action.impl.CalculationAction;
import world.factors.action.impl.ConditionAction;
import world.factors.action.impl.DecreaseAction;
import world.factors.action.impl.IncreaseAction;
import world.factors.entity.definition.EntityDefinition;
import world.factors.environment.definition.impl.EnvVariableManagerImpl;
import world.factors.grid.api.GridDefinition;
import world.factors.rule.Rule;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

public class XMLValidator {
    public static void validateFileExists(Path file) throws FileNotFoundException {
        if(!file.toFile().exists()) {
            throw new FileNotFoundException("1."+ file.getFileName().toString() + " file not found.");
        }
    }

    public static void validateFileIsXML(Path file) {
        if(!file.getFileName().toString().endsWith(".xml")) {
            throw new IllegalArgumentException("File is not XML");
        }
    }

    public static void validateXMLContent(PRDWorld world) {
        validateEnvironmentPropertyUniqueness(world);
        validateAllEntitiesPropertyUniqueness(world);
    }

    //2
    public static void validateEnvironmentPropertyUniqueness(PRDWorld world) {
        PRDEnvironment environment = world.getPRDEnvironment();
        for(int i=0; i<environment.getPRDEnvProperty().size(); i++) {
            String currName = environment.getPRDEnvProperty().get(i).getPRDName();
            for(int j=i+1; j<environment.getPRDEnvProperty().size(); j++) {
                String otherName = environment.getPRDEnvProperty().get(j).getPRDName();
                if(currName.equals(otherName)) {
                    throw new IllegalArgumentException("2. Environment name is not unique");
                }
            }
        }
    }

    public static void validateEntityNameUniqueness(PRDWorld world) {
        PRDEntities entities = world.getPRDEntities();
        for(int i=0; i<entities.getPRDEntity().size(); i++) {
            String currName = entities.getPRDEntity().get(i).getName();
            for(int j=i+1; j<entities.getPRDEntity().size(); j++) {
                String otherName = entities.getPRDEntity().get(j).getName();
                if(currName.equals(otherName)) {
                    throw new IllegalArgumentException("Entity name is not unique");
                }
            }
        }
    }

    //3
    public static void validateAllEntitiesPropertyUniqueness(PRDWorld world) {
        PRDEntities entities = world.getPRDEntities();
        for(int i=0; i<entities.getPRDEntity().size(); i++) {
            validateEntityPropertyUniqueness(entities.getPRDEntity().get(i));
        }
    }

    private static void validateEntityPropertyUniqueness(PRDEntity entity) {
        PRDProperties properties = entity.getPRDProperties();
        for(int i=0; i<properties.getPRDProperty().size(); i++) {
            String currName = properties.getPRDProperty().get(i).getPRDName();
            for(int j=i+1; j<properties.getPRDProperty().size(); j++) {
                String otherName = properties.getPRDProperty().get(j).getPRDName();
                if(currName.equals(otherName)) {
                    throw new IllegalArgumentException("3. Entity property name is not unique");
                }
            }
        }
    }
    //4
    public static void validateAllActionsReferToExistingEntities(World world) {
        for (Rule rule : world.getRules()) {
            for (Action action : rule.getActionsToPerform()) {
                if (!action.isEntityExistInWorld(world.getEntities())) {
                    throw new IllegalArgumentException("4. Action refers to non-existing entity");
                }
            }
        }
    }

    //5
    public static void validateAllActionsReferToExistingEntityProperties(World world) {
        for(Rule rule: world.getRules()) {
            for(Action action: rule.getActionsToPerform()) {
                if(!action.isPropertyExistInEntity()) {
                    throw new IllegalArgumentException("5. Action refers to non-existing entity property");
                }
            }
        }
    }

    //6
    public static void validateMathActionHasNumericArgs(List<Rule> rules, List<EntityDefinition> entities, EnvVariableManagerImpl envVariableManagerImpl) {
        for (Rule rule : rules) {
            for (Action action : rule.getActionsToPerform()) {
                ActionType actionType = action.getActionType();
                switch (actionType) {
                    case INCREASE:
                        IncreaseAction increaseAction = (IncreaseAction) action;
                        if (!(increaseAction.isMathActionHasNumericArgs(entities, envVariableManagerImpl))) {
                            throw new IllegalArgumentException("6.Math action has non-numeric argument");
                        }
                        break;
                    case DECREASE:
                        DecreaseAction decreaseAction = (DecreaseAction) action;
                        if (!(decreaseAction.isMathActionHasNumericArgs(entities, envVariableManagerImpl))) {
                            throw new IllegalArgumentException("6. Math action action has non-numeric argument");
                        }
                        break;
                    case CALCULATION:
                        CalculationAction calculationAction = (CalculationAction) action;
                        if (!(calculationAction.isMathActionHasNumericArgs(entities, envVariableManagerImpl))) {
                            throw new IllegalArgumentException("6. Math action action has non-numeric argument");
                        }
                        break;
                    case CONDITION:
                        ConditionAction conditionAction = (ConditionAction) action;
                        if (!(conditionAction.isMathActionHasNumericArgs(entities, envVariableManagerImpl))) {
                            throw new IllegalArgumentException("6. Math action action has non-numeric argument");
                        }
                    default:
                        break;
                }
            }
        }
    }
    public static void validateGrid(GridDefinition grid) {
        int height = grid.getHeight();
        int width = grid.getWidth();
        // both between 10 and 100
        if(height < 10 || height > 100 || width < 10 || width > 100) {
            throw new IllegalArgumentException("Grid height and width must be between 10 and 100");
        }
    }

}
