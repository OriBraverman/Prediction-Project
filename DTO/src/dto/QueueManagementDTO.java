package dto;

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QueueManagementDTO) {
            QueueManagementDTO other = (QueueManagementDTO) obj;
            return this.pending == other.pending && this.active == other.active && this.completed == other.completed;
        }
        return false;
    }
}
