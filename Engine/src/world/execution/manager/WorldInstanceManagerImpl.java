package world.execution.manager;

import requests.UserRequest;
import world.definition.World;
import world.execution.WorldInstance;
import world.execution.WorldInstanceImpl;
import world.factors.entity.execution.manager.EntityInstanceManager;
import world.factors.environment.execution.api.ActiveEnvironment;

import java.util.HashMap;
import java.util.Map;

public class WorldInstanceManagerImpl implements WorldInstanceManager {
    private int nextId;
    private Map<Integer, WorldInstance> worldInstances;

    public WorldInstanceManagerImpl() {
        nextId = 0;
        worldInstances = new HashMap<>();
    }

    @Override
    public WorldInstance create(UserRequest userRequest, World world, ActiveEnvironment activeEnvironment, EntityInstanceManager entityInstanceManager) {
        nextId++;
        WorldInstance newWorldInstance = new WorldInstanceImpl(world, nextId, activeEnvironment, entityInstanceManager, userRequest.getTermination(), userRequest);
        worldInstances.put(nextId, newWorldInstance);
        return newWorldInstance;
    }

    @Override
    public WorldInstance getWorldInstanceById(int id) {
        return worldInstances.get(id);
    }

    @Override
    public UserRequest deleteWorldInstance(int simulationID) {
        UserRequest userRequest = worldInstances.get(simulationID).getUserRequest();
        worldInstances.remove(simulationID);
        return userRequest;
    }
}
