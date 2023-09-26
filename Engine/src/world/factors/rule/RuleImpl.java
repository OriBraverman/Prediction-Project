package world.factors.rule;

import world.factors.action.api.Action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RuleImpl implements Rule, Serializable {

    private final String name;
    private Activation activation;
    private final List<Action> actions;

    public RuleImpl(String name, Activation activation) {
        this.name = name;
        this.activation = activation;
        actions = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Activation getActivation() {
        return activation;
    }

    @Override
    public List<Action> getActionsToPerform() {
        return actions;
    }

    @Override
    public void addAction(Action action) {
        actions.add(action);
    }

    @Override
    public boolean isRuleActive(int currentTick) {
        float random = (float) Math.random();
        return currentTick % activation.getTicks() == 0 && random <= activation.getProbabilty();

    }
}
