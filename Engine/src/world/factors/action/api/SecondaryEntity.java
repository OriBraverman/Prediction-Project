package world.factors.action.api;

import world.factors.condition.Condition;
import world.factors.entity.definition.EntityDefinition;
import world.factors.expression.api.Expression;

import java.io.Serializable;

public class SecondaryEntity implements Serializable {
    private final EntityDefinition secondaryEntityDefinition;
    private final String selectionCount;
    private final Condition selectionCondition;
    public SecondaryEntity(EntityDefinition secondaryEntityDefinition, String selectionCount, Condition selectionCondition) {
        this.secondaryEntityDefinition = secondaryEntityDefinition;
        this.selectionCount = selectionCount;
        this.selectionCondition = selectionCondition;
    }

    public EntityDefinition getSecondaryEntityDefinition() {
        return secondaryEntityDefinition;
    }

    public String getSelectionCount() {
        return selectionCount;
    }

    public Condition getSelectionCondition() {
        return selectionCondition;
    }
}
