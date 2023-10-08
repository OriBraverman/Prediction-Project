package dto;

public class QueueManagementDTO {
    private int pending;
    private int active;
    private int completed;
    private int capacity;

    public QueueManagementDTO(int capacity, int pending, int active, int completed) {
        this.pending = pending;
        this.active = active;
        this.completed = completed;
        this.capacity = capacity;
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

    public int getCapacity(){ return this.capacity; }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QueueManagementDTO) {
            QueueManagementDTO other = (QueueManagementDTO) obj;
            return this.pending == other.pending && this.active == other.active && this.completed == other.completed && this.capacity == other.capacity;
        }
        return false;
    }

}
