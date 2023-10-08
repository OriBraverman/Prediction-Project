package dto;

import dto.world.TerminationDTO;

public class RequestDTO {
    private final String status;
    private final int id;
    private final String userName;
    private final String worldName;
    private final int numberOfExecutions;
    private final TerminationDTO termination;
    private final int runningExecutions;
    private final int completedExecutions;
    public RequestDTO(String userName, String worldName, int numberOfExecutions, TerminationDTO termination) {
        this.status = "pending";
        this.id = -1;
        this.userName = userName;
        this.worldName = worldName;
        this.numberOfExecutions = numberOfExecutions;
        this.termination = termination;
        this.runningExecutions = 0;
        this.completedExecutions = 0;
    }

    public RequestDTO(String status, int id, String userName, String worldName, int numberOfExecutions, TerminationDTO termination, int runningExecutions, int completedExecutions) {
        this.status = status;
        this.id = id;
        this.userName = userName;
        this.worldName = worldName;
        this.numberOfExecutions = numberOfExecutions;
        this.termination = termination;
        this.runningExecutions = runningExecutions;
        this.completedExecutions = completedExecutions;
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getWorldName() {
        return worldName;
    }

    public int getNumberOfExecutions() {
        return numberOfExecutions;
    }

    public TerminationDTO getTermination() {
        return termination;
    }

    public int getRunningExecutions() {
        return runningExecutions;
    }

    public int getCompletedExecutions() {
        return completedExecutions;
    }
}
