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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldDefinitionManagerImpl implements WorldDefinitionManager{
    private int nextId;
    private Map<Integer, World> worldDefinitions;

    public WorldDefinitionManagerImpl(){
        this.nextId = 0 ;
        this.worldDefinitions = new HashMap<>();
    }

    @Override
    public void addWorld(World world) {
        nextId++;
        world.setId(nextId);
        worldDefinitions.put(nextId, world);
    }

    @Override
    public World getWorldDefinitionById(int id){
        return worldDefinitions.get(id);
    }

    @Override
    public int getWorldDefinitionsCount(){
        return worldDefinitions.size();
    }

}
