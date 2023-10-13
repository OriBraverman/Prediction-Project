package requests;

import simulation.SimulationExecutionDetails;
import world.factors.termination.Termination;

import java.util.ArrayList;
import java.util.List;

public class UserRequest {
    private int id;
    private String username;
    private String worldName;
    private Termination termination;
    private int executionsCount;
    private RequestStatus requestStatus;
    private List<SimulationExecutionDetails> simulationExecutionDetailsList;

    public UserRequest(int id,String username, String worldName, Termination termination, int executionsCount) {
        this.id = id;
        this.username = username;
        this.worldName = worldName;
        this.termination = termination;
        this.executionsCount = executionsCount;
        this.requestStatus = RequestStatus.PENDING;
        simulationExecutionDetailsList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getSimulationName() {
        return worldName;
    }

    public int getExecutionsCount() {
        return executionsCount;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }
    public int getPendingExecutionsCount() {
        if (simulationExecutionDetailsList == null) {
            return 0;
        }
        return simulationExecutionDetailsList.stream()
                .mapToInt(simulationExecutionDetails -> simulationExecutionDetails.isPending() ? 1 : 0)
                .sum();
    }

    public int getRunningExecutionsCount() {
        if (simulationExecutionDetailsList == null) {
            return 0;
        }
        return simulationExecutionDetailsList.stream()
                .mapToInt(simulationExecutionDetails -> simulationExecutionDetails.isRunning() ? 1 : 0)
                .sum();
    }

    public int getCompletedExecutionsCount() {
        if (simulationExecutionDetailsList == null) {
            return 0;
        }
        return simulationExecutionDetailsList.stream()
                .mapToInt(simulationExecutionDetails -> simulationExecutionDetails.isCompleted() ? 1 : 0)
                .sum();
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public boolean isApproved() {
        return this.requestStatus == RequestStatus.ACCEPTED;
    }

    public String getWorldName() {
        return worldName;
    }

    public Termination getTermination() {
        return termination;
    }

    public void addSimulationExecutionDetails(SimulationExecutionDetails simulationExecutionDetails) {
        simulationExecutionDetailsList.add(simulationExecutionDetails);
    }
}
