package requests;

import world.factors.termination.Termination;

public class UserRequest {
    private int id;
    private String username;
    private String worldName;
    private Termination termination;
    private int executionsCount;
    private RequestStatus requestStatus;
    private int pendingExecutionsCount;
    private int runningExecutionsCount;
    private int completedExecutionsCount;

    public UserRequest(int id,String username, String worldName, Termination termination, int executionsCount) {
        this.id = id;
        this.username = username;
        this.worldName = worldName;
        this.termination = termination;
        this.executionsCount = executionsCount;
        this.requestStatus = RequestStatus.PENDING;
        this.pendingExecutionsCount = 0;
        this.runningExecutionsCount = 0;
        this.completedExecutionsCount = 0;
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
        return pendingExecutionsCount;
    }

    public int getRunningExecutionsCount() {
        return runningExecutionsCount;
    }

    public int getCompletedExecutionsCount() {
        return completedExecutionsCount;
    }

    public void setSimulationName(String worldName) {
        this.worldName = worldName;
    }

    public void setExecutionsCount(int executionsCount) {
        this.executionsCount = executionsCount;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public void setRunningExecutionsCount(int runningExecutionsCount) {
        this.runningExecutionsCount = runningExecutionsCount;
    }

    public void setCompletedExecutionsCount(int completedExecutionsCount) {
        this.completedExecutionsCount = completedExecutionsCount;
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
}
