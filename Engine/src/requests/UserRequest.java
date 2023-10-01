package requests;

import simulation.SimulationExecutionDetails;

public class UserRequest {
    private int id;
    private String simulationName;
    private SimulationExecutionDetails simulationExecutionDetails;  //todo: check if this is needed
    private int executionsCount;
    private boolean approved;
    private int runningExecutionsCount;
    private int completedExecutionsCount;

    public UserRequest(int id, String simulationName, int executionsCount, boolean approved) {
        this.id = id;
        this.simulationName = simulationName;
        this.executionsCount = executionsCount;
        this.approved = approved;
        this.runningExecutionsCount = 0;
        this.completedExecutionsCount = 0;
    }

    public int getId() {
        return id;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public int getExecutionsCount() {
        return executionsCount;
    }

    public boolean isApproved() {
        return approved;
    }

    public int getRunningExecutionsCount() {
        return runningExecutionsCount;
    }

    public int getCompletedExecutionsCount() {
        return completedExecutionsCount;
    }

    public void setSimulationName(String simulationName) {
        this.simulationName = simulationName;
    }

    public void setExecutionsCount(int executionsCount) {
        this.executionsCount = executionsCount;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void setRunningExecutionsCount(int runningExecutionsCount) {
        this.runningExecutionsCount = runningExecutionsCount;
    }

    public void setCompletedExecutionsCount(int completedExecutionsCount) {
        this.completedExecutionsCount = completedExecutionsCount;
    }
}
