package world.definition.manager;

import world.definition.World;
import world.execution.WorldInstance;
import world.execution.WorldInstanceImpl;
import world.factors.entity.definition.EntityDefinition;
import world.factors.entity.execution.manager.EntityInstanceManager;
import world.factors.environment.definition.api.EnvVariablesManager;
import world.factors.environment.execution.api.ActiveEnvironment;
import world.factors.grid.api.GridDefinition;
import world.factors.rule.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldDefinitionManagerImpl implements WorldDefinitionManager{
    private Map<String, World> worldDefinitions;

    public WorldDefinitionManagerImpl(){
        this.worldDefinitions = new HashMap<>();
    }

    @Override
    public void addWorld(World world) {
        if (worldDefinitions.containsKey(world.getName())) {
            throw new IllegalArgumentException("World with name " + world.getName() + " already exists");
        }
        worldDefinitions.put(world.getName(), world);
    }

    @Override
    public World getWorldDefinitionByName(String name) {
        return worldDefinitions.get(name);
    }

    @Override
    public int getWorldDefinitionsCount(){
        return worldDefinitions.size();
    }

    @Override
    public List<World> getWorldDefinitions() {
        return new ArrayList<>(worldDefinitions.values());
    }
}
