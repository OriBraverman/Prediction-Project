package convertor;

import resources.schema.generatedWorld.*;
import value.generator.api.ValueGeneratorFactory;
import world.World;
import world.factors.action.api.AbstractAction;
import world.factors.action.api.Action;
import world.factors.action.api.ActionType;
import world.factors.action.api.SecondaryEntity;
import world.factors.action.impl.*;
import world.factors.condition.*;
import world.factors.entity.definition.EntityDefinition;
import world.factors.entity.definition.EntityDefinitionImpl;
import world.factors.environment.definition.api.EnvVariablesManager;
import world.factors.environment.definition.impl.EnvVariableManagerImpl;
import world.factors.expression.api.AbstractExpression;
import world.factors.grid.api.GridDefinition;
import world.factors.grid.api.GridDefinitionImpl;
import world.factors.property.definition.api.PropertyDefinition;
import world.factors.property.definition.api.Range;
import world.factors.property.definition.impl.BooleanPropertyDefinition;
import world.factors.property.definition.impl.FloatPropertyDefinition;
import world.factors.property.definition.impl.IntegerPropertyDefinition;
import world.factors.property.definition.impl.StringPropertyDefinition;
import world.factors.rule.Activation;
import world.factors.rule.Rule;
import world.factors.rule.RuleImpl;
import world.factors.termination.Termination;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Convertor implements Serializable {
    private PRDWorld generatedWorld;

    public World convertPRDWorldToWorld() {
        EnvVariablesManager environment = getEnvironment();
        List<EntityDefinition> entities = getEntities();
        List<Rule> rules = getRules(entities);
        Termination termination = getTermination();
        GridDefinition grid = new GridDefinitionImpl(generatedWorld.getPRDGrid().getRows(), generatedWorld.getPRDGrid().getColumns());
        int threadCount = generatedWorld.getPRDThreadCount();
        return new World(environment, entities, grid, rules, termination, threadCount);
    }

    private EnvVariablesManager getEnvironment() {
        EnvVariablesManager environment = new EnvVariableManagerImpl();
        for (PRDEnvProperty envProp : generatedWorld.getPRDEnvironment().getPRDEnvProperty()) {
            if (envProp.getType().equals("decimal")) {
                if (!isConvertableToInteger(envProp.getPRDRange().getFrom()) || !isConvertableToInteger(envProp.getPRDRange().getTo())) {
                    throw new RuntimeException("Range of decimal environment property must be integer");
                }
                Range<Integer> range = new Range((int)envProp.getPRDRange().getTo(), (int)envProp.getPRDRange().getFrom());
                environment.addEnvironmentVariable(new IntegerPropertyDefinition(envProp.getPRDName(), ValueGeneratorFactory.createRandomInteger(range), range));
            } else if (envProp.getType().equals("float")) {
                if (envProp.getPRDRange() == null) {
                    environment.addEnvironmentVariable(new FloatPropertyDefinition(envProp.getPRDName(), ValueGeneratorFactory.createRandomFloat(null)));
                } else {
                    Range<Float> range = new Range((float)envProp.getPRDRange().getTo(), (float)envProp.getPRDRange().getFrom());
                    environment.addEnvironmentVariable(new FloatPropertyDefinition(envProp.getPRDName(), ValueGeneratorFactory.createRandomFloat(range), range));
                }
            } else if (envProp.getType().equals("boolean")) {
                environment.addEnvironmentVariable(new BooleanPropertyDefinition(envProp.getPRDName(), ValueGeneratorFactory.createRandomBoolean()));
            } else if (envProp.getType().equals("string")) {
                environment.addEnvironmentVariable(new StringPropertyDefinition(envProp.getPRDName(), ValueGeneratorFactory.createRandomString()));
            } else {
                throw new RuntimeException("Unknown type: " + envProp.getType());
            }
        }
        return environment;
    }

    private boolean isConvertableToInteger(Double value) {
        return value == Math.floor(value);
    }

    private List<EntityDefinition> getEntities() {
        List<EntityDefinition> entities = new ArrayList<>();
        for (PRDEntity entity: generatedWorld.getPRDEntities().getPRDEntity()) {
            entities.add(getEntity(entity));
        }
        return entities;
    }

    private EntityDefinition getEntity(PRDEntity entity) {
        EntityDefinition entityDefinition = new EntityDefinitionImpl(entity.getName());
        for (PRDProperty property: entity.getPRDProperties().getPRDProperty()) {
            if(property.getPRDValue().isRandomInitialize()){
                entityDefinition.addProperty(getRandomProperty(property));
            }
            else{
                entityDefinition.addProperty(getFixedProperty(property));
            }
        }
        return entityDefinition;
    }

    private PropertyDefinition getRandomProperty(PRDProperty property){
        if(property.getType().equals("decimal")){
            Range<Integer> range = new Range((int)property.getPRDRange().getTo(), (int)property.getPRDRange().getFrom());
            return new IntegerPropertyDefinition(property.getPRDName(), ValueGeneratorFactory.createRandomInteger(range), range);
        }
        else if(property.getType().equals("float")){
            Range<Float> range = new Range((float)property.getPRDRange().getTo(), (float)property.getPRDRange().getFrom());
            return new FloatPropertyDefinition(property.getPRDName(), ValueGeneratorFactory.createRandomFloat(range), range);
        }
        else if(property.getType().equals("boolean")){
            return new BooleanPropertyDefinition(property.getPRDName(), ValueGeneratorFactory.createRandomBoolean());
        }
        else if(property.getType().equals("string")){
            return new StringPropertyDefinition(property.getPRDName(), ValueGeneratorFactory.createRandomString());
        }
        else{
            throw new RuntimeException("Unknown type: " + property.getType());
        }
    }

    private PropertyDefinition getFixedProperty(PRDProperty property) {
        if(property.getType().equals("decimal")){
            return new IntegerPropertyDefinition(property.getPRDName(), ValueGeneratorFactory.createFixed(Integer.valueOf(property.getPRDValue().getInit())), new Range<Integer>((int)property.getPRDRange().getTo(), (int)property.getPRDRange().getFrom()));
        }
        else if(property.getType().equals("float")){
            return new FloatPropertyDefinition(property.getPRDName(), ValueGeneratorFactory.createFixed(Float.valueOf(property.getPRDValue().getInit())), new Range<Float>((float)property.getPRDRange().getTo(), (float)property.getPRDRange().getFrom()));
        }
        else if(property.getType().equals("boolean")){
            return new BooleanPropertyDefinition(property.getPRDName(), ValueGeneratorFactory.createFixed(Boolean.valueOf(property.getPRDValue().getInit())));
        }
        else if(property.getType().equals("string")){
            return new StringPropertyDefinition(property.getPRDName(), ValueGeneratorFactory.createFixed(property.getPRDValue().getInit()));
        }
        else{
            throw new RuntimeException("Unknown type: " + property.getType());
        }
    }

    private List<Rule> getRules(List<EntityDefinition> entities) {
        List<Rule> rules = new ArrayList<>();
        for (PRDRule rule: generatedWorld.getPRDRules().getPRDRule()) {
            rules.add(getRule(rule, entities));
        }
        return rules;
    }

    private Rule getRule(PRDRule rule, List<EntityDefinition> entities) {
        String name = rule.getName();
        Activation activation = getActivation(rule);
        Rule ruleImpl = new RuleImpl(name, activation);
        for (PRDAction action: rule.getPRDActions().getPRDAction()) {
            ruleImpl.addAction(getAction(action, entities));
        }
        return ruleImpl;
    }

    private Activation getActivation(PRDRule rule) {
        //if both, one null, or both not null
        if (rule.getPRDActivation() == null) {
            return new Activation();
        } else if (rule.getPRDActivation().getTicks() != null && rule.getPRDActivation().getProbability() != null) {
            return new Activation(rule.getPRDActivation().getTicks(), rule.getPRDActivation().getProbability());
        } else if (rule.getPRDActivation().getTicks() != null) {
            return new Activation(rule.getPRDActivation().getTicks());
        } else if (rule.getPRDActivation().getProbability() != null) {
            return new Activation(rule.getPRDActivation().getProbability());
        } else {
            return new Activation();
        }
    }

    private Action getAction(PRDAction action, List<EntityDefinition> entities) {
        ActionType actionType = ActionType.getActionType(action.getType());
        SecondaryEntity secondaryEntity = null;
        if (action.getPRDSecondaryEntity() != null) {
            String secondaryEntityName = action.getPRDSecondaryEntity().getEntity();
            EntityDefinition secondaryEntityDefinition = getEntityDefinition(secondaryEntityName, entities);
            String selectionCount = action.getPRDSecondaryEntity().getPRDSelection().getCount();
            Condition selectionCondition = null;
            if (action.getPRDSecondaryEntity().getPRDSelection().getPRDCondition() != null) {
                selectionCondition = getCondition(action.getPRDSecondaryEntity().getPRDSelection().getPRDCondition(), entities);
            }
            secondaryEntity = new SecondaryEntity(secondaryEntityDefinition, selectionCount, selectionCondition);
        }
        switch (actionType) {
            case INCREASE:
                return getIncreaseAction(action, getEntityDefinition(action.getEntity(), entities), secondaryEntity);
            case DECREASE:
                return getDecreaseAction(action, getEntityDefinition(action.getEntity(), entities), secondaryEntity);
            case CALCULATION:
                return getCalculationAction(action, getEntityDefinition(action.getEntity(), entities), secondaryEntity);
            case CONDITION:
                return getConditionAction(action, getEntityDefinition(action.getEntity(), entities), entities, secondaryEntity);
            case SET:
                return getSetAction(action, getEntityDefinition(action.getEntity(), entities), secondaryEntity);
            case KILL:
                return getKillAction(action, getEntityDefinition(action.getEntity(), entities));
            case REPLACE:
                return getReplaceAction(action, getEntityDefinition(action.getKill(), entities), getEntityDefinition(action.getCreate(), entities), secondaryEntity);
            case PROXIMITY:
                PRDAction.PRDBetween between = action.getPRDBetween();
                return getProximityAction(action, getEntityDefinition(between.getSourceEntity(), entities), getEntityDefinition(between.getTargetEntity(), entities), entities, secondaryEntity);
            default:
                throw new RuntimeException("Unknown action type: " + actionType);
        }
    }

    private Action getProximityAction(PRDAction action, EntityDefinition primaryEntityDefinition, EntityDefinition targetEntityDefinition, List<EntityDefinition> entities, SecondaryEntity secondaryEntity) {
        List<AbstractAction> actions = null;
        if (action.getPRDActions() != null) {
            actions = getActions(action.getPRDActions().getPRDAction(), entities);
        }
        if (secondaryEntity == null) {
            secondaryEntity = new SecondaryEntity(targetEntityDefinition, "ALL", null);
        }
        return new ProximityAction(primaryEntityDefinition, secondaryEntity, AbstractExpression.getExpressionByString(action.getPRDEnvDepth().getOf(), null), actions);
    }

    private Action getReplaceAction(PRDAction action, EntityDefinition killEntityDefinition, EntityDefinition createEntityDefinition, SecondaryEntity secondaryEntity) {
        return new ReplaceAction(killEntityDefinition, secondaryEntity, createEntityDefinition, ReplaceType.getReplaceType(action.getMode()));
    }

    private Action getKillAction(PRDAction action, EntityDefinition entityDefinition) {
        return new KillAction(entityDefinition);
    }


    private Action getSetAction(PRDAction action, EntityDefinition entityDefinition, SecondaryEntity secondaryEntity) {
        String property = action.getProperty();
        String value = action.getValue();
        return new SetAction(entityDefinition, secondaryEntity, property, value);
    }

    private Action getConditionAction(PRDAction action, EntityDefinition entityDefinition, List<EntityDefinition> entities, SecondaryEntity secondaryEntity) {
        Condition condition = getCondition(action.getPRDCondition(), entities);
        List<AbstractAction> thenActions = getActions(action.getPRDThen().getPRDAction(), entities);
        List<AbstractAction> elseActions = null;
        if (action.getPRDElse() != null) {
            elseActions = getActions(action.getPRDElse().getPRDAction(), entities);
        }
        return new ConditionAction(entityDefinition, secondaryEntity, condition, thenActions, elseActions);
    }

    private List<AbstractAction> getActions(List<PRDAction> prdAction, List<EntityDefinition> entities) {
        List<AbstractAction> actions = new ArrayList<>();
        for (PRDAction action: prdAction) {
            actions.add((AbstractAction) getAction(action, entities));
        }
        return actions;
    }

    private Condition getCondition(PRDCondition prdCondition, List<EntityDefinition> entities) {
        String singularity = prdCondition.getSingularity();
        if (singularity.equals("single")) {
            EntityDefinition entityDefinition = getEntityDefinition(prdCondition.getEntity(), entities);
            String property = prdCondition.getProperty();
            OperatorType operatorType = OperatorType.getOperatorType(prdCondition.getOperator());
            String value = prdCondition.getValue();
            return new SingleCondition(entityDefinition, property, operatorType, value);
        } else if (singularity.equals("multiple")) {
            LogicalType logicalType = LogicalType.getLogicalType(prdCondition.getLogical());
            List<Condition> conditions = new ArrayList<>();
            for (PRDCondition condition: prdCondition.getPRDCondition()) {
                conditions.add(getCondition(condition, entities));
            }
            return new MultipleCondition(logicalType, conditions);
        } else {
            throw new RuntimeException("Unknown singularity: " + singularity);
        }
    }

    private PropertyDefinition getEntityPropertyDefinition(String property, List<PropertyDefinition> props) {
        List<PropertyDefinition> filteredProps = props
                .stream()
                .filter(entityPropertyDefinition -> entityPropertyDefinition.getName().equals(property))
                .collect(Collectors.toList());
        if (filteredProps.size() != 1) {
            throw new RuntimeException("5. Action " + property + " refers to non-existing entity property");
        }
        return filteredProps.get(0);
    }

    private Action getCalculationAction(PRDAction action, EntityDefinition entityDefinition, SecondaryEntity secondaryEntity) {
        String resultProperty = action.getResultProp();
        if (action.getPRDMultiply() != null) {
            String argument1 = action.getPRDMultiply().getArg1();
            String argument2 = action.getPRDMultiply().getArg2();
            return new CalculationAction(entityDefinition, secondaryEntity, resultProperty, argument1, argument2, CalculationAction.CalculationOperator.MULTIPLY);
        } else /*if (action.getPRDDivide() != null)*/ {
            String argument1 = action.getPRDDivide().getArg1();
            String argument2 = action.getPRDDivide().getArg2();
            return new CalculationAction(entityDefinition, secondaryEntity, resultProperty, argument1, argument2, CalculationAction.CalculationOperator.DIVIDE);
        }
    }

    private Action getDecreaseAction(PRDAction action, EntityDefinition entityDefinition, SecondaryEntity secondaryEntity) {
        String property = action.getProperty();
        String byExpression = action.getBy();
        return new DecreaseAction(entityDefinition, secondaryEntity, property, byExpression);
    }

    private EntityDefinition getEntityDefinition(String entity, List<EntityDefinition> entities) {
        List<EntityDefinition> filteredEntities = entities
                .stream()
                .filter(entityDefinition -> entityDefinition.getName().equals(entity))
                .collect(Collectors.toList());
        if (filteredEntities.size() != 1) {
            throw new RuntimeException("There is no entity named " + entity + " or there are more than one");
        }
        return filteredEntities.get(0);
    }

    private Action getIncreaseAction(PRDAction action, EntityDefinition entityDefinition, SecondaryEntity secondaryEntity) {
        String property = action.getProperty();
        String byExpression = action.getBy();
        return new IncreaseAction(entityDefinition, secondaryEntity, property, byExpression);
    }

    private Termination getTermination() {
        Termination termination = new Termination();
        List<Object> terminationList = generatedWorld.getPRDTermination().getPRDBySecondOrPRDByTicks();
        Object byUser = generatedWorld.getPRDTermination().getPRDByUser();
        if (byUser != null && terminationList.size() > 0) {
            throw new RuntimeException("ByUser and BySecond/ByTicks cannot be together");
        } else if (byUser != null) {
            termination.setByUser(true);
            return termination;
        } else if (terminationList.size() > 2 || terminationList.size() == 0) {
            throw new RuntimeException("Termination must have exactly 1 or 2 termination types");
        }
        for (Object terminationObject: terminationList) {
            if (terminationObject instanceof PRDByTicks) {
                termination.setTicksCount(((PRDByTicks) terminationObject).getCount());
            } else if (terminationObject instanceof PRDBySecond) {
                termination.setSecondsCount(((PRDBySecond) terminationObject).getCount());
            } else {
                throw new RuntimeException("Unknown termination type: " + terminationObject.getClass());
            }
        }
        return termination;
    }

    public void setGeneratedWorld(PRDWorld generatedWorld) {
        this.generatedWorld = generatedWorld;
    }

}
