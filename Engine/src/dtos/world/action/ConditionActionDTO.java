package dtos.world.action;

import dtos.world.EntityDefinitionDTO;

import java.util.List;

public class ConditionActionDTO extends AbstructActionDTO {
    private final String conditionExpression;
    private final List<AbstructActionDTO> thenActions;
    private final List<AbstructActionDTO> elseActions;

    public ConditionActionDTO(EntityDefinitionDTO primatyEntity, String conditionExpression, List<AbstructActionDTO> thenActions, List<AbstructActionDTO> elseActions) {
        super("Condition", primatyEntity);
        this.conditionExpression = conditionExpression;
        this.thenActions = thenActions;
        this.elseActions = elseActions;
    }

    public String getConditionExpression() {
        return conditionExpression;
    }

    public List<AbstructActionDTO> getThenActions() {
        return thenActions;
    }

    public List<AbstructActionDTO> getElseActions() {
        return elseActions;
    }
}