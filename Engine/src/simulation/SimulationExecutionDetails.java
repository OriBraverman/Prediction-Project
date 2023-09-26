package simulation;

import world.World;
import world.factors.entity.execution.manager.EntityInstanceManager;
import world.factors.entityPopulation.EntityPopulation;
import world.factors.environment.execution.api.ActiveEnvironment;
import world.factors.grid.execution.GridInstance;
import world.factors.grid.execution.GridInstanceImpl;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationExecutionDetails implements Serializable {
    private final int id;
    private final ActiveEnvironment activeEnvironment;
    private final GridInstance grid;
    private final World world;
    private final EntityInstanceManager entityInstanceManager;
    private boolean isTerminatedBySecondsCount = false;
    private boolean isTerminatedByTicksCount = false;
    private AtomicBoolean isPending;
    private AtomicBoolean isRunning;
    private AtomicBoolean isPaused;
    private Instant currStartTime;
    private List<Duration> durations;
    private String formattedStartTime;
    private AtomicInteger currentTick;
    private Map<Integer, List<EntityPopulation>> entityPopulationByTicks;
    private String status = "Pending";
    private String terminationReason = "";


    public SimulationExecutionDetails(int id, ActiveEnvironment activeEnvironment, EntityInstanceManager entityInstanceManager, World world) {
        this.id = id;
        this.activeEnvironment = activeEnvironment;
        this.grid = new GridInstanceImpl(world.getGridDefinition());
        this.world = world;
        this.entityInstanceManager = entityInstanceManager;
        this.currStartTime = Instant.now();
        this.durations = new ArrayList<>();
        this.isPending = new AtomicBoolean(true);
        this.isRunning = new AtomicBoolean(false);
        this.isPaused = new AtomicBoolean(false);
        this.entityPopulationByTicks = new HashMap<>();
        this.currentTick = new AtomicInteger(0);
    }

    public ActiveEnvironment getActiveEnvironment() { return activeEnvironment; }

    public World getWorld() { return world; }

    public int getId(){
        return this.id;
    }

    public String getFormattedStartTime(){
        return this.formattedStartTime;
    }

    public void setFormattedStartTime(String formattedStartTime){ this.formattedStartTime = formattedStartTime; }

    public boolean isTerminatedBySecondsCount() {
        return isTerminatedBySecondsCount;
    }

    public boolean isTerminatedByTicksCount() {
        return isTerminatedByTicksCount;
    }

    public void setIsTerminatedBySecondsCount(boolean isTerminatedBySecondsCount){ this.isTerminatedBySecondsCount = isTerminatedBySecondsCount; }

    public void setIsTerminatedByTicksCount(boolean isTerminatedByTicksCount){ this.isTerminatedByTicksCount = isTerminatedByTicksCount; }

    public EntityInstanceManager getEntityInstanceManager() { return this.entityInstanceManager; }

    public void setStatus(String status) {this.status = status; }

    public void setTerminationReason(String terminationReason) {this.terminationReason = terminationReason; }

    public int getCurrentTick() {
        return currentTick.get();
    }

    public void incrementCurrentTick() {
        this.entityPopulationByTicks.put(currentTick.get(), entityInstanceManager.getCurrEntityPopulationList());
        this.currentTick.incrementAndGet();
    }

    public void setCurrentTick(int currentTick) {
        this.currentTick.set(currentTick);
    }

    public Instant getCurrStartTime() {
        return currStartTime;
    }

    public void setCurrStartTime(Instant currStartTime) {
        this.currStartTime = currStartTime;
    }

    public long getSimulationSeconds() {
        if (isRunning.get() && !isPaused.get()) {
            Instant now = Instant.now();
            Duration duration = Duration.between(currStartTime, now);
            return duration.getSeconds() + durations.stream().mapToLong(Duration::getSeconds).sum();
        } else {
            return durations.stream().mapToLong(Duration::getSeconds).sum();
        }
    }

    public void addDuration(Duration duration) {
        durations.add(duration);
    }

    public boolean isRunning() {
        return isRunning.get();
    }

    public boolean isPaused() {
        return isPaused.get();
    }

    public void setTerminatedBySecondsCount(boolean terminatedBySecondsCount) {
        isTerminatedBySecondsCount = terminatedBySecondsCount;
    }

    public void setTerminatedByTicksCount(boolean terminatedByTicksCount) {
        isTerminatedByTicksCount = terminatedByTicksCount;
    }
    public boolean isPending() {
        return isPending.get();
    }
    public void setRunning(boolean running) {
        isRunning.set(running);
    }

    public void setPaused(boolean paused) {
        isPaused.set(paused);
    }

    public Map<Integer, List<EntityPopulation>> getEntityPopulationByTicks() {
        return entityPopulationByTicks;
    }

    public boolean isCompleted() {
        return !isRunning.get() && !isPending.get();
    }

    public GridInstance getGridInstance() {
        return grid;
    }

    public String getStatus(){return this.status;}

    public String getTerminationReason(){return this.terminationReason;}

    public void setPending(boolean isPending) {
        this.isPending.set(isPending);
    }

    public List<Duration> getDurations() {
        return durations;
    }

    public void setDurations(List<Duration> durations) {
        this.durations = durations;
    }
}
