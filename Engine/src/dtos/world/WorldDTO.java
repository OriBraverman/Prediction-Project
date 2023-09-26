package dtos.world;

import java.util.List;

public class WorldDTO {
    private final List<PropertyDefinitionDTO> environment;
    private final List<EntityDefinitionDTO> entities;
    private final List<RuleDTO> rules;
    private final TerminationDTO termination;
    private final int gridWidth;
    private final int gridHeight;
    private final int threadCount;

    public WorldDTO(List<PropertyDefinitionDTO> environment, List<EntityDefinitionDTO> entities, List<RuleDTO> rules, TerminationDTO termination, int gridWidth, int gridHeight, int threadCount) {
        this.environment = environment;
        this.entities = entities;
        this.rules = rules;
        this.termination = termination;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.threadCount = threadCount;
    }

    public List<PropertyDefinitionDTO> getEnvironment() {
        return environment;
    }

    public List<EntityDefinitionDTO> getEntities() {
        return entities;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public List<RuleDTO> getRules() {
        return rules;
    }

    public TerminationDTO getTermination() {
        return termination;
    }

    public int getThreadCount() {
        return threadCount;
    }
}
