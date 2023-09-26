package world.factors.rule;

import world.factors.action.api.Action;

import java.util.List;

public interface Rule {
    String getName();
    Activation getActivation();
    List<Action> getActionsToPerform();
    void addAction(Action action);
    boolean isRuleActive(int currentTick);
}
