package world;

import world.factors.entity.definition.EntityDefinition;
import world.factors.environment.definition.api.EnvVariablesManager;
import world.factors.grid.api.GridDefinition;
import world.factors.rule.Rule;
import world.factors.termination.Termination;

import java.io.Serializable;
import java.util.List;

public class World implements Serializable {
    private EnvVariablesManager environment;
    private List<EntityDefinition> entities;
    private GridDefinition grid;
    private List<Rule> rules;
    private Termination termination;
    private int threadCount;

    public World(EnvVariablesManager environment, List<EntityDefinition> entities, GridDefinition grid, List<Rule> rules, Termination termination, int threadCount) {
        this.environment = environment;
        this.entities = entities;
        this.grid = grid;
        this.rules = rules;
        this.termination = termination;
        this.threadCount = threadCount;
    }

    public EnvVariablesManager getEnvironment() {
        return environment;
    }

    public List<EntityDefinition> getEntities() {
        return entities;
    }

    public EntityDefinition getEntityByName(String name) {
        for (EntityDefinition entity : entities) {
            if (entity.getName().equals(name)) {
                return entity;
            }
        }
        return null;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public Termination getTermination() {
        return termination;
    }

    public GridDefinition getGridDefinition() {
        return grid;
    }

    public int getThreadCount() {
        return threadCount;
    }

}
