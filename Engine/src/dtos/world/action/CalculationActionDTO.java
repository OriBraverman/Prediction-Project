package dtos.world.action;

import dtos.world.EntityDefinitionDTO;
import dtos.world.action.AbstructActionDTO;

public class CalculationActionDTO extends AbstructActionDTO {
    private final String resultPropName;
    private final String calculationExpression;

    public CalculationActionDTO(EntityDefinitionDTO primatyEntity, String resultPropName, String calculationExpression) {
        super("Calculation", primatyEntity);
        this.resultPropName = resultPropName;
        this.calculationExpression = calculationExpression;
    }

    public String getResultPropName() {
        return resultPropName;
    }

    public String getCalculationExpression() {
        return calculationExpression;
    }
}