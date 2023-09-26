package world.factors.action.api;


import world.factors.entity.definition.EntityDefinition;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractAction implements Action, Serializable {

    protected final ActionType actionType;
    protected final EntityDefinition primaryEntityDefinition;
    protected final SecondaryEntity secondaryEntity;

    public AbstractAction(ActionType actionType, EntityDefinition primaryEntityDefinition) {
        this.actionType = actionType;
        this.primaryEntityDefinition = primaryEntityDefinition;
        this.secondaryEntity = null;
    }

    protected AbstractAction(ActionType actionType, EntityDefinition primaryEntityDefinition, SecondaryEntity secondaryEntity) {
        this.actionType = actionType;
        this.primaryEntityDefinition = primaryEntityDefinition;
        this.secondaryEntity = secondaryEntity;
    }

    @Override
    public EntityDefinition getPrimaryEntityDefinition() {
        return primaryEntityDefinition;
    }

    @Override
    public ActionType getActionType() {
        return actionType;
    }


    @Override
    public boolean isEntityExistInWorld(List<EntityDefinition> entities) {
        if (entities.contains(primaryEntityDefinition)
            || (secondaryEntity != null && entities.contains(secondaryEntity.getSecondaryEntityDefinition()))) {
            return true;
        }
        return false;
    }

    public SecondaryEntity getSecondaryEntity() {
        return secondaryEntity;
    }

    @Override
    public boolean isLastAction() {
        return actionType.equals(ActionType.KILL)
                || actionType.equals(ActionType.REPLACE)
                || actionType.equals(ActionType.CONDITION) // might contain kill or replace in thenActions or elseActions
                || actionType.equals(ActionType.PROXIMITY); // might contain kill or replace in thenActions
    }

    @Override
    public boolean isFirstAction() {
        return !isLastAction();
    }
}
