package world.execution.manager;

import requests.UserRequest;
import world.definition.World;
import world.execution.WorldInstance;
import world.factors.entity.execution.manager.EntityInstanceManager;
import world.factors.environment.execution.api.ActiveEnvironment;
import world.factors.termination.Termination;

public interface WorldInstanceManager {

    WorldInstance create(World world, ActiveEnvironment activeEnvironment, EntityInstanceManager entityInstanceManager, Termination termination, UserRequest userRequest);

    WorldInstance getWorldInstanceById(int id);
}
