package world.definition.manager;

import world.definition.World;
import world.factors.entity.definition.EntityDefinition;
import world.factors.environment.definition.api.EnvVariablesManager;
import world.factors.grid.api.GridDefinition;
import world.factors.rule.Rule;

import java.util.List;

public interface WorldDefinitionManager {
    void addWorld(World world);
    int getWorldDefinitionsCount();
    World getWorldDefinitionByName(String name);

    List<World> getWorldDefinitions();

    boolean isWorldExists(String worldName);
}
