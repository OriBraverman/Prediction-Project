package world.execution;

import requests.UserRequest;
import world.definition.World;
import world.factors.entity.execution.manager.EntityInstanceManager;
import world.factors.environment.execution.api.ActiveEnvironment;
import world.factors.grid.execution.GridInstance;
import world.factors.termination.Termination;

public interface WorldInstance {
    int getId();

    World getWorld();

    ActiveEnvironment getActiveEnvironment();

    EntityInstanceManager getEntityInstanceManager();

    GridInstance getGridInstance();

    Termination getTermination();

    UserRequest getUserRequest();
}
