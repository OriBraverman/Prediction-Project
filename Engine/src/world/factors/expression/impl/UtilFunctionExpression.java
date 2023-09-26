package world.factors.expression.impl;

import context.Context;
import world.factors.entity.definition.EntityDefinition;
import world.factors.environment.definition.impl.EnvVariableManagerImpl;
import world.factors.expression.api.AbstractExpression;
import world.factors.expression.api.Expression;
import world.factors.expression.api.ExpressionType;
import world.factors.function.api.Function;
import world.factors.function.api.FunctionType;
import world.factors.function.impl.*;
import world.factors.functionArgument.api.FunctionArgument;
import world.factors.functionArgument.impl.EntityPropertyFunctionArgument;
import world.factors.functionArgument.impl.EnvPropertyFunctionArgument;
import world.factors.functionArgument.impl.ExpressionFunctionArgument;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilFunctionExpression extends AbstractExpression {
    public UtilFunctionExpression(String expression) {
        super(expression, ExpressionType.UTIL_FUNCTION);
    }

    @Override
    public Function evaluate(Context context) {
        return getFunctionByExpression(expression, context.getPrimaryEntityInstance().getEntityDefinition());
    }

    public Function getFunctionByExpression(String expression, EntityDefinition entityDefinition) {
        // this function receives only function expression
        // the function expression structure is: functionName(arg1,arg2,arg3,...)
        // so we need to extract the function name and the arguments
        List<String> elements = splitExpressionString(this.expression);
        ArrayList<FunctionArgument> functionArguments = new ArrayList<>();
        List<Expression> args = new ArrayList<>();
        for (int i = 1; i < elements.size(); i++) {
            args.add(getExpressionByString(elements.get(i), entityDefinition));
        }
        switch(FunctionType.getFunctionType(elements.get(0))) {
            case ENVIRONMENT:
                if (elements.size() != 2) {
                    throw new IllegalArgumentException("environment function must have only one argument");
                }
                functionArguments.add(new EnvPropertyFunctionArgument(args.get(0).getStringExpression()));
                return new EnvironmentFunction(functionArguments);
            case RANDOM:
                if (elements.size() != 2) {
                    throw new IllegalArgumentException("random function must have only one argument");
                }
                functionArguments.add(new ExpressionFunctionArgument(args.get(0).getStringExpression(), args.get(0)));
                return new RandomFunction(functionArguments);
            case TICKS:
                if (elements.size() != 2) {
                    throw new IllegalArgumentException("ticks function must have only one argument");
                }
                if (elements.get(1).contains(".")) {
                    String entityName = elements.get(1).substring(0, elements.get(1).indexOf("."));
                    String propertyName = elements.get(1).substring(elements.get(1).indexOf(".") + 1);
                    functionArguments.add(new EntityPropertyFunctionArgument(expression, entityName, propertyName));
                    return new TicksFunction(functionArguments);
                } else {
                    throw new IllegalArgumentException("ticks function must have entity.property argument");
                }
            case EVALUATE:
                if (elements.size() != 2) {
                    throw new IllegalArgumentException("ticks function must have only one argument");
                }
                if (elements.get(1).contains(".")) {
                    String entityName = elements.get(1).substring(0, elements.get(1).indexOf("."));
                    String propertyName = elements.get(1).substring(elements.get(1).indexOf(".") + 1);
                    functionArguments.add(new EntityPropertyFunctionArgument(expression, entityName, propertyName));
                    return new EvaluateFunction(functionArguments);
                } else {
                    throw new IllegalArgumentException("evaluate function must have entity.property argument");
                }
            case PERCENT:
                if (elements.size() != 3) {
                    throw new IllegalArgumentException("percent function must have only two arguments");
                }
                functionArguments.add(new ExpressionFunctionArgument(args.get(0).getStringExpression(), args.get(0)));
                functionArguments.add(new ExpressionFunctionArgument(args.get(1).getStringExpression(), args.get(1)));
                return new PercentFunction(functionArguments);
            default:
                throw new IllegalArgumentException("function [" + elements.get(0) + "] is not exist");
        }
    }

    @Override
    public boolean isNumericExpression(List<EntityDefinition> entityDefinitions, EnvVariableManagerImpl envVariableManagerImpl) {
        Function function = getFunctionByExpression(expression, entityDefinitions.get(0));
        return function.isNumericFunction(envVariableManagerImpl);
    }

    private static List<String> splitExpressionString(String expression) {
        // this function receives only function expression
        // the function expression structure is: functionName(arg1,arg2,arg3,...)
        // the return list will be: [functionName, arg1, arg2, arg3, ...]
        List<String> elements = new ArrayList<>();
        Pattern pattern = Pattern.compile("([a-zA-Z]+)\\((.*)\\)");
        Matcher matcher = pattern.matcher(expression);
        if (matcher.find()) {
            elements.add(matcher.group(1));
            String[] args = matcher.group(2).split(",");
            for (String arg : args) {
                elements.add(arg);
            }
        }
        return elements;
    }
}
