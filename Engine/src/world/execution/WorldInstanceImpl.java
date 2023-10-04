package world.execution;

import requests.UserRequest;
import world.definition.World;
import world.factors.entity.execution.manager.EntityInstanceManager;
import world.factors.environment.execution.api.ActiveEnvironment;
import world.factors.grid.execution.GridInstance;
import world.factors.grid.execution.GridInstanceImpl;
import world.factors.termination.Termination;

public class WorldInstanceImpl implements WorldInstance {
    private World world;
    private int id;
    private ActiveEnvironment activeEnvironment;
    private EntityInstanceManager entityInstanceManager;
    private GridInstance gridInstance;
    private Termination termination;
    private UserRequest userRequest;

    public WorldInstanceImpl(World world, int id, ActiveEnvironment activeEnvironment, EntityInstanceManager entityInstanceManager, Termination termination, UserRequest userRequest) {
        this.world = world;
        this.id = id;
        this.activeEnvironment = activeEnvironment;
        this.entityInstanceManager = entityInstanceManager;
        this.gridInstance = new GridInstanceImpl(world.getGridDefinition());
        this.termination = termination;
        this.userRequest = userRequest;
    }

    @Override
    public int getId(){
        return this.id;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public ActiveEnvironment getActiveEnvironment() {
        return activeEnvironment;
    }

    @Override
    public EntityInstanceManager getEntityInstanceManager() {
        return entityInstanceManager;
    }

    @Override
    public GridInstance getGridInstance() {
        return gridInstance;
    }

    @Override
    public Termination getTermination() {return this.termination; }

    @Override
    public UserRequest getUserRequest() {
        return userRequest;
    }
}
