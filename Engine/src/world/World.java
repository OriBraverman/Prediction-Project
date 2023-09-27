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

    public World(EnvVariablesManager environment, List<EntityDefinition> entities, GridDefinition grid, List<Rule> rules) {
        this.environment = environment;
        this.entities = entities;
        this.grid = grid;
        this.rules = rules;
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

    public GridDefinition getGridDefinition() {
        return grid;
    }

}
