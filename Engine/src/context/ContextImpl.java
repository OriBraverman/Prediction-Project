package context;

import world.factors.action.impl.ReplaceType;
import world.factors.entity.definition.EntityDefinition;
import world.factors.entity.execution.EntityInstance;
import world.factors.entity.execution.manager.EntityInstanceManager;
import world.factors.environment.execution.api.ActiveEnvironment;
import world.factors.expression.api.AbstractExpression;
import world.factors.expression.api.Expression;
import world.factors.expression.api.ExpressionType;
import world.factors.function.api.Function;
import world.factors.function.api.FunctionType;
import world.factors.grid.execution.GridInstance;
import world.factors.property.definition.api.PropertyDefinition;
import world.factors.property.execution.PropertyInstance;

import static world.factors.expression.api.AbstractExpression.isFunctionExpression;

public class ContextImpl implements Context {

    private EntityInstance primaryEntityInstance;
    private EntityInstance secondaryEntityInstance;
    private EntityInstanceManager entityInstanceManager;
    private ActiveEnvironment activeEnvironment;
    private GridInstance grid;
    private int currentTick;

    public ContextImpl(EntityInstance primaryEntityInstance, EntityInstanceManager entityInstanceManager, ActiveEnvironment activeEnvironment, GridInstance grid, int currentTick) {
        this(primaryEntityInstance, null, entityInstanceManager, activeEnvironment, grid, currentTick);
    }

    public ContextImpl(EntityInstance primaryEntityInstance, EntityInstance secondaryEntityInstance, EntityInstanceManager entityInstanceManager, ActiveEnvironment activeEnvironment, GridInstance grid, int currentTick) {
        this.primaryEntityInstance = primaryEntityInstance;
        this.secondaryEntityInstance = secondaryEntityInstance;
        this.entityInstanceManager = entityInstanceManager;
        this.activeEnvironment = activeEnvironment;
        this.grid = grid;
        this.currentTick = currentTick;
    }

    @Override
    public EntityInstance getPrimaryEntityInstance() {
        return primaryEntityInstance;
    }

    @Override
    public EntityInstance getSecondaryEntityInstance() {
        return secondaryEntityInstance;
    }

    @Override
    public GridInstance getGrid() {
        return grid;
    }

    @Override
    public void removeEntity(EntityInstance entityInstance) {
        entityInstanceManager.killEntity(entityInstance.getId());
    }

    @Override
    public void replaceEntity(EntityDefinition createEntityDefinition, ReplaceType mode) {
        switch (mode) {
            case SCRATCH:
                entityInstanceManager.killEntity(primaryEntityInstance.getId());
                entityInstanceManager.create(createEntityDefinition, grid);
                break;
            case DERIVED:
                entityInstanceManager.replaceDerived(primaryEntityInstance, createEntityDefinition);
                break;
        }
    }

    @Override
    public ActiveEnvironment getEnvironment() {
        return activeEnvironment;
    }

    @Override
    public ExpressionType getExpressionType(String expression) {
        if (isFunctionExpression(expression)) {
            String functionName = expression.substring(0, expression.indexOf("("));
            if (FunctionType.isFunctionType(functionName)) {
                return ExpressionType.UTIL_FUNCTION;
            }
        }
        else if (isPropertyName(expression)) {
            return ExpressionType.PROPERTY_NAME;
        }
        return ExpressionType.FREE_VALUE;
    }

    private boolean isPropertyName(String expression) {
        try {
            primaryEntityInstance.getPropertyByName(expression);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public PropertyInstance getPropertyInstanceByPropertyDefinition(PropertyDefinition propertyDefinition) {
        return primaryEntityInstance.getPropertyByName(propertyDefinition.getName());
    }

    @Override
    public Object getValueByExpression(Expression expression) {
        switch (expression.getExpressionType()) {
            case UTIL_FUNCTION:
                return ((Function)expression.evaluate(this)).execute(this);
            case PROPERTY_NAME:
                PropertyInstance propertyInstance = primaryEntityInstance.getPropertyByName(expression.getStringExpression());
                return propertyInstance.getValue();
            case FREE_VALUE:
                return expression.evaluate(this);
            default:
                throw new IllegalArgumentException("expression type [" + expression.getExpressionType() + "] is not exist");
        }
    }

    @Override
    public void setPropertyValue(String name, String property, String value) {
        if (!name.equals(primaryEntityInstance.getEntityDefinition().getName())) {
            throw new IllegalArgumentException("entity [" + name + "] is not the primary entity");
        }
        EntityInstance entityInstance = primaryEntityInstance;
        Expression expression = AbstractExpression.getExpressionByString(value, primaryEntityInstance.getEntityDefinition());
        Object evaluateValue = getValueByExpression(expression);

        // check if entity instance property type is the same as evaluate type
        if (!entityInstance.getPropertyByName(property).getValue().getClass().equals(evaluateValue.getClass())) {
            throw new IllegalArgumentException("property [" + property + "] type is not the same as evaluate type");
        }
        entityInstance.getPropertyByName(property).updateValue(evaluateValue, currentTick);
    }

    @Override
    public Object getNumberOfTicksPropertyHasentChanged(String propertyName) {
        int lastUpdateTick = primaryEntityInstance.getPropertyByName(propertyName).getLastUpdatedTick();
        return this.currentTick - lastUpdateTick;
    }
    @Override
    public int getCurrentTick() {
        return currentTick;
    }
}