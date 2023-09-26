package world.factors.action.impl;

import context.Context;
import world.factors.action.api.AbstractAction;
import world.factors.action.api.Action;
import world.factors.action.api.ActionType;
import world.factors.action.api.SecondaryEntity;
import world.factors.entity.definition.EntityDefinition;
import world.factors.expression.api.Expression;
import world.factors.grid.Coordinate;

import java.util.Collection;
import java.util.List;

import static java.lang.Math.floor;

public class ProximityAction extends AbstractAction {
    private final Expression of;
    private List<AbstractAction> thenActions;

    public ProximityAction(EntityDefinition primaryEntityDefinition, Expression of, List<AbstractAction> thenActions){
        super(ActionType.PROXIMITY, primaryEntityDefinition);
        this.of = of;
        this.thenActions = thenActions;
    }

    public ProximityAction(EntityDefinition primaryEntityDefinition, SecondaryEntity secondaryEntity, Expression of, List<AbstractAction> thenActions) {
        super(ActionType.PROXIMITY, primaryEntityDefinition, secondaryEntity);
        this.of = of;
        this.thenActions = thenActions;
    }

    @Override
    public void invoke(Context context) {
        if (shouldActivateActions(context)) {
            for (AbstractAction thenAction : thenActions) {
                thenAction.invoke(context);
            }
        }
    }

    private boolean shouldActivateActions(Context context){
        boolean matchesDefintions = context.getPrimaryEntityInstance().getEntityDefinition().getName().equals(primaryEntityDefinition.getName())
                && context.getSecondaryEntityInstance().getEntityDefinition().getName().equals(secondaryEntity.getSecondaryEntityDefinition().getName());
        //context.getValueByExpression(this.of) is float like 1.65 so get the floor of it
        int rank;
        if (!(context.getValueByExpression(this.of) instanceof Number)) {
            throw new RuntimeException("ProximityAction: shouldActivateActions: of expression should be a number");
        } else if (context.getValueByExpression(this.of) instanceof Float) {
            rank = (int) floor((Float)context.getValueByExpression(this.of));
        } else {
            rank = (int) context.getValueByExpression(this.of);
        }
        Collection<Coordinate> envCells = context.getGrid().findEnvironmentCells(context.getPrimaryEntityInstance().getCoordinate(), rank);
        boolean areClose = envCells.contains(context.getSecondaryEntityInstance().getCoordinate());

        return matchesDefintions && areClose;
    }

    @Override
    public boolean isPropertyExistInEntity() {
        return true;
    }

    @Override
    public boolean isEntityExistInWorld(List<EntityDefinition> entities) {
        return super.isEntityExistInWorld(entities);
    }

    public String getStringOf() {
        return of.toString();
    }

    public List<AbstractAction> getThenActions() {
        return thenActions;
    }
}
