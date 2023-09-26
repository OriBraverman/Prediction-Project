package world.factors.action.impl;

import context.Context;
import world.factors.action.api.AbstractAction;
import world.factors.action.api.ActionType;
import world.factors.action.api.SecondaryEntity;
import world.factors.condition.Condition;
import world.factors.entity.definition.EntityDefinition;
import world.factors.environment.definition.impl.EnvVariableManagerImpl;

import java.util.List;

public class ConditionAction extends AbstractAction {
    private Condition condition;
    private List<AbstractAction> thenActions;
    private List<AbstractAction> elseActions;

    public ConditionAction(EntityDefinition entityDefinition, Condition condition, List<AbstractAction> thenActions, List<AbstractAction> elseActions) {
        super(ActionType.CONDITION, entityDefinition);
        this.condition = condition;
        this.thenActions = thenActions;
        this.elseActions = elseActions;
    }

    public ConditionAction(EntityDefinition primaryEntityDefinition, SecondaryEntity secondaryEntity, Condition condition, List<AbstractAction> thenActions, List<AbstractAction> elseActions) {
        super(ActionType.CONDITION, primaryEntityDefinition, secondaryEntity);
        this.condition = condition;
        this.thenActions = thenActions;
        this.elseActions = elseActions;
    }

    @Override
    public void invoke(Context context) {
        if (condition.assertCondition(context)) {
            for (AbstractAction thenAction : thenActions) {
                thenAction.invoke(context);
            }
        } else {
            if (elseActions == null) {
                return;
            }
            for (AbstractAction elseAction : elseActions) {
                elseAction.invoke(context);
            }
        }
    }

    @Override
    public boolean isPropertyExistInEntity() {
        if (!condition.isPropertyExistInEntity()) //always true
            return false;
        for (AbstractAction thenAction : thenActions) {
            if (!thenAction.isPropertyExistInEntity())
                return false;
        }
        if (elseActions != null) {
            for (AbstractAction elseAction : elseActions) {
                if (!elseAction.isPropertyExistInEntity())
                    return false;
            }
        }
        return true;
    }
    public boolean isMathActionHasNumericArgs(List<EntityDefinition> entities, EnvVariableManagerImpl envVariableManager) {
        for (AbstractAction thenAction : thenActions) {
            if (thenAction instanceof CalculationAction) {
                if (!((CalculationAction) thenAction).isMathActionHasNumericArgs(entities, envVariableManager))
                    return false;
            }
            else if (thenAction instanceof IncreaseAction) {
                if (!((IncreaseAction) thenAction).isMathActionHasNumericArgs(entities, envVariableManager))
                    return false;
            }
            else if (thenAction instanceof DecreaseAction) {
                if (!((DecreaseAction) thenAction).isMathActionHasNumericArgs(entities, envVariableManager))
                    return false;
            }
            else if (thenAction instanceof ConditionAction) {
                if (!((ConditionAction) thenAction).isMathActionHasNumericArgs(entities, envVariableManager))
                    return false;
            }
        }
        if (elseActions != null) {
            for (AbstractAction elseAction : elseActions) {
                if (elseAction instanceof CalculationAction) {
                    if (!((CalculationAction) elseAction).isMathActionHasNumericArgs(entities, envVariableManager))
                        return false;
                }
                else if (elseAction instanceof IncreaseAction) {
                    if (!((IncreaseAction) elseAction).isMathActionHasNumericArgs(entities, envVariableManager))
                        return false;
                }
                else if (elseAction instanceof DecreaseAction) {
                    if (!((DecreaseAction) elseAction).isMathActionHasNumericArgs(entities, envVariableManager))
                        return false;
                }
                else if (elseAction instanceof ConditionAction) {
                    if (!((ConditionAction) elseAction).isMathActionHasNumericArgs(entities, envVariableManager))
                        return false;
                }
            }
        }
        return true;
    }

    public String getCondition() {
        return condition.toString();
    }

    public List<AbstractAction> getThenActions() {
        return thenActions;
    }

    public List<AbstractAction> getElseActions() {
        return elseActions;
    }
}
