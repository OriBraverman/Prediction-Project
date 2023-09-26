package dtos;

public class QueueManagementDTO {
    private int pending;
    private int active;
    private int completed;

    public QueueManagementDTO(int pending, int active, int completed) {
        this.pending = pending;
        this.active = active;
        this.completed = completed;
    }

    public int getPending() {
        return pending;
    }

    public int getActive() {
        return active;
    }

    public int getCompleted() {
        return completed;
    }
}
