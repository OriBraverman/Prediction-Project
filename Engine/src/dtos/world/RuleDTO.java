package dtos.world;

import dtos.ActivationDTO;
import dtos.world.action.AbstructActionDTO;

import java.util.List;

public class RuleDTO {
    private String name;
    private ActivationDTO activation;
    private int numberOfActions;
    private List<AbstructActionDTO> actions;

    public RuleDTO(String name, ActivationDTO activation, int numberOfActions, List<AbstructActionDTO> actions) {
        this.name = name;
        this.activation = activation;
        this.numberOfActions = numberOfActions;
        this.actions = actions;
    }

    public String getName() {
        return name;
    }

    public ActivationDTO getActivation() {
        return activation;
    }

    public int getNumberOfActions() {
        return numberOfActions;
    }

    public List<AbstructActionDTO> getActions() {
        return actions;
    }
}
