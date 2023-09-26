package world.factors.condition;

import context.Context;

public interface Condition {
    boolean assertCondition(Context context);

    boolean isPropertyExistInEntity();
}