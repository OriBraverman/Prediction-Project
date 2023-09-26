package dtos.world;

public class TerminationDTO {
    private boolean isByUser = false;
    private int secondsCount = -1;
    private int ticksCount = -1;

    public TerminationDTO(boolean isByUser, int secondsCount, int ticksCount) {
        this.isByUser = isByUser;
        this.secondsCount = secondsCount;
        this.ticksCount = ticksCount;
    }

    public int getSecondsCount() {
        return secondsCount;
    }

    public int getTicksCount() {
        return ticksCount;
    }

    public boolean isByUser() {
        return isByUser;
    }
}
