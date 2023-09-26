package simulation;

import context.ContextImpl;
import world.World;
import world.factors.action.api.Action;
import world.factors.action.api.ActionType;
import world.factors.entity.definition.EntityDefinition;
import world.factors.entity.execution.EntityInstance;
import world.factors.entity.execution.manager.EntityInstanceManager;
import world.factors.entity.execution.manager.EntityInstanceManagerImpl;
import world.factors.environment.execution.api.ActiveEnvironment;
import world.factors.grid.execution.GridInstance;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static simulation.SimulationMemorySaver.writeSEDByIdAndTick;

public class SimulationRunnerImpl implements Serializable, Runnable, SimulationRunner {
    private final int id;
    private final boolean isBonusActivated;
    private SimulationExecutionDetails simulationED;
    private AtomicBoolean isTravelForward = new AtomicBoolean(false);
    private final Semaphore travelForwardSemaphore = new Semaphore(1);
    private final Semaphore pauseSemaphore = new Semaphore(1);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | hh.mm.ss");
    public SimulationRunnerImpl(int id, SimulationExecutionDetails simulationExecutionDetails, boolean isBonusActivated) {
        this.id = id;
        this.simulationED = simulationExecutionDetails;
        this.isBonusActivated = isBonusActivated;
    }

    public AtomicBoolean getIsTravelForward() {
        return isTravelForward;
    }

    public void setIsTravelForward(boolean isTravelForward) {
        this.isTravelForward.set(isTravelForward);
    }

    public int getId() {
        return id;
    }

    public void setSimulationED(SimulationExecutionDetails simulationED) {
        this.simulationED = simulationED;
    }

    private void initEntityInstancesArray() {
        List<EntityDefinition> entityDefinitions = this.simulationED.getWorld().getEntities();
        for (EntityDefinition entityDefinition : entityDefinitions) {
            for (int i = 0; i < this.simulationED.getEntityInstanceManager().getPopulationByEntityDefinition(entityDefinition); i++) {
                this.simulationED.getEntityInstanceManager().create(entityDefinition, this.simulationED.getGridInstance());
            }
        }
    }

    public void pauseSimulation() {
        try {
            pauseSemaphore.acquire(); // Acquire the semaphore to pause the simulation
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public void resumeSimulation() {
        pauseSemaphore.release(); // Release the semaphore to resume the simulation
    }

    public void acquireTravelForwardSemaphore() {
        try {
            travelForwardSemaphore.acquire(); // Acquire the semaphore to travel forward
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void releaseTravelForwardSemaphore() {
        travelForwardSemaphore.release(); // Release the semaphore to travel forward
    }

    @Override
    public void run() {
        try {
            this.simulationED.setStatus("Running");
            runSimulation();
            this.simulationED.setStatus("Finished Successfully");
            if (simulationED.getTerminationReason() == null) {
                if (simulationED.isTerminatedBySecondsCount()) {
                    this.simulationED.setTerminationReason("Terminated by seconds count");
                } else if (simulationED.isTerminatedByTicksCount()) {
                    this.simulationED.setTerminationReason("Terminated by ticks count");
                }
            }
        } catch (Exception e) {
            this.simulationED.setStatus("Failed");
            this.simulationED.setTerminationReason(e.getMessage());
            this.simulationED.setRunning(false);
        }
    }

    private void runSimulation() {
        synchronized (simulationED) {
            simulationED.setPending(false);
            simulationED.setRunning(true);
        }
        Date date = new Date();
        this.simulationED.setFormattedStartTime(this.dateFormat.format(date));

        initEntityInstancesArray();
        while (!simulationED.getWorld().getTermination().isTerminated(simulationED.getCurrentTick(), simulationED.getSimulationSeconds()) && simulationED.isRunning()) {
            while (this.isTravelForward.get()) {
                try {
                    travelForwardSemaphore.acquire();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            travelForwardSemaphore.release();

            if (isBonusActivated) {
                writeSEDByIdAndTick(id, simulationED.getCurrentTick(), simulationED);
            }
            while (this.simulationED.isPaused()) {
                try {
                    pauseSemaphore.acquire();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            pauseSemaphore.release();

            simulationED.incrementCurrentTick();
            simulationED.getEntityInstanceManager().moveAllInstances(simulationED.getGridInstance());
            // get all runnable rules
            List<Action> actionableRules = simulationED.getWorld().getRules()
                    .stream()
                    .filter(rule -> rule.isRuleActive(simulationED.getCurrentTick()))
                    .flatMap(rule -> rule.getActionsToPerform().stream())
                    .collect(Collectors.toList());
            List<Action> firstActions = actionableRules.stream()
                    .filter(action -> action.isFirstAction())
                    .collect(Collectors.toList());
            List<Action> lastActions = actionableRules.stream()
                    .filter(action -> action.isLastAction())
                    .collect(Collectors.toList());
            for (EntityInstance entityInstance : simulationED.getEntityInstanceManager().getInstances()) {
                for (Action action : firstActions) {
                    if (action.getPrimaryEntityDefinition().getName().equals(entityInstance.getEntityDefinition().getName())) {
                        if (action.getSecondaryEntity() != null) {
                            List<EntityInstance> selectedSecondaryEntityInstances = simulationED.getEntityInstanceManager().getSelectedSeconderyEntites(action.getSecondaryEntity(),  simulationED.getActiveEnvironment(), simulationED.getGridInstance(), simulationED.getCurrentTick());
                            for (EntityInstance secondaryEntityInstance : selectedSecondaryEntityInstances) {
                                action.invoke(new ContextImpl(entityInstance, secondaryEntityInstance, simulationED.getEntityInstanceManager(), simulationED.getActiveEnvironment(), simulationED.getGridInstance(), simulationED.getCurrentTick()));
                            }
                        } else {
                            action.invoke(new ContextImpl(entityInstance, simulationED.getEntityInstanceManager(), simulationED.getActiveEnvironment(), simulationED.getGridInstance(), simulationED.getCurrentTick()));
                        }
                    }
                }
            }
            for (EntityInstance entityInstance : simulationED.getEntityInstanceManager().getInstances()) {
                for (Action action : lastActions) {
                    if (action.getPrimaryEntityDefinition().getName().equals(entityInstance.getEntityDefinition().getName())) {
                        if (action.getSecondaryEntity() != null) {
                            List<EntityInstance> selectedSecondaryEntityInstances = simulationED.getEntityInstanceManager().getSelectedSeconderyEntites(action.getSecondaryEntity(),  simulationED.getActiveEnvironment(), simulationED.getGridInstance(), simulationED.getCurrentTick());
                            for (EntityInstance secondaryEntityInstance : selectedSecondaryEntityInstances) {
                                if (simulationED.getEntityInstanceManager().isEntityAlive(entityInstance.getId()) && simulationED.getEntityInstanceManager().isEntityAlive(secondaryEntityInstance.getId())) {
                                    action.invoke(new ContextImpl(entityInstance, secondaryEntityInstance, simulationED.getEntityInstanceManager(), simulationED.getActiveEnvironment(), simulationED.getGridInstance(), simulationED.getCurrentTick()));
                                }
                            }
                        } else {
                            if (simulationED.getEntityInstanceManager().isEntityAlive(entityInstance.getId())) {
                                action.invoke(new ContextImpl(entityInstance, simulationED.getEntityInstanceManager(), simulationED.getActiveEnvironment(), simulationED.getGridInstance(), simulationED.getCurrentTick()));
                            }
                        }
                    }
                }
            }
        }
        simulationED.setIsTerminatedBySecondsCount(simulationED.getWorld().getTermination().isTerminatedBySecondsCount(simulationED.getSimulationSeconds()));
        simulationED.setIsTerminatedByTicksCount(simulationED.getWorld().getTermination().isTerminatedByTicksCount(simulationED.getCurrentTick()));
        simulationED.setRunning(false);
    }
    public void printDebug(List<EntityInstance> instances) {
        GridInstance gridInstance = simulationED.getGridInstance();
        for (int y = 0; y < gridInstance.getHeight(); y++) {
            for (int x = 0; x < gridInstance.getWidth(); x++) {
                if (!gridInstance.isCellFree(gridInstance.getCoordinate(x, y))) {
                    boolean found = false;
                    for (EntityInstance instance : instances) {
                        if (instance.getCoordinate().getX() == x && instance.getCoordinate().getY() == y) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        System.out.print("1 ");
                    } else {
                        System.out.print("2 ");
                    }
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }

    private void printReplacedEntities(List<EntityInstance> entityInstances) {
        for (EntityInstance entityInstance : simulationED.getEntityInstanceManager().getInstances()) {
            boolean Found = false;
            for (EntityInstance temp : entityInstances) {
                if (entityInstance.getId() == temp.getId()) {
                    Found = true;
                    break;
                }
            }
            if (!Found) {
                System.out.println("Entity " + entityInstance.getId() + " " + entityInstance.getCell().getCoordinate().getX() + " " + entityInstance.getCell().getCoordinate().getY() + " is replaced");
            }
        }
    }
}
