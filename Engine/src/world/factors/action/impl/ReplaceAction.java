package world.factors.action.impl;

import context.Context;
import world.factors.action.api.AbstractAction;
import world.factors.action.api.ActionType;
import world.factors.action.api.SecondaryEntity;
import world.factors.entity.definition.EntityDefinition;

public class ReplaceAction extends AbstractAction {
    private EntityDefinition createEntityDefinition;
    private ReplaceType mode;

    public ReplaceAction(EntityDefinition killEntityDefinition, EntityDefinition createEntityDefinition, ReplaceType mode) {
        super(ActionType.REPLACE, killEntityDefinition);
        this.createEntityDefinition = createEntityDefinition;
        this.mode = mode;
    }

    public ReplaceAction(EntityDefinition killEntityDefinition, SecondaryEntity secondaryEntity, EntityDefinition createEntityDefinition, ReplaceType mode) {
        super(ActionType.REPLACE, killEntityDefinition, secondaryEntity);
        this.createEntityDefinition = createEntityDefinition;
        this.mode = mode;
    }

    @Override
    public void invoke(Context context) {
        context.replaceEntity(createEntityDefinition, mode);
    }

    @Override
    public boolean isPropertyExistInEntity() {
        return true;
    }

    public EntityDefinition getCreateEntityDefinition() {
        return createEntityDefinition;
    }

    public ReplaceType getMode() {
        return mode;
    }
}
