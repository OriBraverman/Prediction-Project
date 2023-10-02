package user.details.tree;

import dto.world.RuleDTO;
import javafx.scene.control.TreeItem;

public class RuleTreeItem extends TreeItem<String> {
    private RuleDTO rule;
    private TreeItem<String> actions;
    private TreeItem<String> activation;
    public RuleTreeItem(RuleDTO rule) {
        super("Rule name: " + rule.getName());
        this.rule = rule;
        actions = new ActionsTreeItem(rule.getActions());
        activation = new ActivationTreeItem(rule.getActivation());
        this.getChildren().addAll(actions, activation);
    }
}