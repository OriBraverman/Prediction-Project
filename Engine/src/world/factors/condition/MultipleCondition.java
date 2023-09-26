package world.factors.condition;

import context.Context;

import java.io.Serializable;
import java.util.List;

public class MultipleCondition implements Condition, Serializable {
    private final LogicalType logical;
    private final List<Condition> conditions;

    public MultipleCondition(LogicalType logical, List<Condition> conditions) {
        this.logical = logical;
        this.conditions = conditions;
    }
    @Override
    public boolean assertCondition(Context context) {
        switch (this.logical){
            case AND:
                for (Condition condition : conditions) {
                    if (!condition.assertCondition(context))
                        return false;
                }
                return true;
            case OR:
                for (Condition condition : conditions) {
                    if (condition.assertCondition(context))
                        return true;
                }
                return false;
        }
        return false;
    }

    @Override
    public boolean isPropertyExistInEntity() {
        return true;
    }

    @Override
    public String toString() {
        // Form: (condition1) logical (condition2) logical (condition3)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < conditions.size(); i++) {
            sb.append("(").append(conditions.get(i)).append(")");
            if (i != conditions.size() - 1)
                sb.append(" ").append(logical).append(" ");
        }
        return sb.toString();
    }
}
