package world.factors.action.impl;

import world.factors.action.api.AbstractAction;
import world.factors.action.api.ActionType;

import context.Context;
import world.factors.entity.definition.EntityDefinition;

public class KillAction extends AbstractAction {

    public KillAction(EntityDefinition entityDefinition) {
        super(ActionType.KILL, entityDefinition);
    }

    @Override
    public void invoke(Context context) {
        context.removeEntity(context.getPrimaryEntityInstance());
    }

    @Override
    public boolean isPropertyExistInEntity() {
        return true;
    }
}
