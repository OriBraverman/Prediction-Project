package world.factors.termination;
import java.io.Serializable;
import java.sql.Time;

public class Termination implements Serializable {
    private boolean isByUser = false;
    private int secondsCount = -1;
    private int ticksCount = -1;
    private boolean terminatedByUser = false;


    public int getSecondsCount() {
        return secondsCount;
    }

    public void setSecondsCount(int secondsCount) {
        this.secondsCount = secondsCount;
    }

    public int getTicksCount() {
        return ticksCount;
    }

    public void setTicksCount(int ticksCount) {
        this.ticksCount = ticksCount;
    }

    public void setByUser(boolean byUser) {
        isByUser = byUser;
    }

    public void setTerminatedByUser(boolean terminatedByUser) {
        this.terminatedByUser = terminatedByUser;
    }

    public boolean isByUser() {
        return isByUser;
    }

    public boolean isTerminatedByUser() {
        return terminatedByUser;
    }

    public boolean isTerminated(int currentTick, long seconds) {
        if (isByUser) {
            return terminatedByUser;
        } else if (isTerminatedBySecondsCount(seconds) || isTerminatedByTicksCount(currentTick)) {
            return true;
        }
        return false;
    }

    public boolean isTerminatedBySecondsCount(long seconds) {
        if (this.secondsCount != -1) {
            if (seconds >= this.secondsCount) {
                return true;
            }
        }
        return false;
    }

    public boolean isTerminatedByTicksCount(int currentTick) {
        if (this.ticksCount != -1) {
            if (currentTick >= this.ticksCount) {
                return true;
            }
        }
        return false;
    }
}
