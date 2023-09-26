package world.factors.expression.api;

import context.Context;
import world.factors.entity.definition.EntityDefinition;
import world.factors.environment.definition.impl.EnvVariableManagerImpl;
import world.factors.function.api.FunctionType;

import java.util.List;

public interface Expression {
    ExpressionType getExpressionType();
    String getStringExpression();
    Object evaluate(Context context);
    boolean isNumericExpression(List<EntityDefinition> entityDefinitions, EnvVariableManagerImpl envVariableManagerImpl);
    FunctionType getFunctionTypeByExpression(String expression);
}