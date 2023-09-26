package world.factors.grid;

import java.io.Serializable;

public class Cell implements Serializable {
    private final Coordinate coordinate;
    private boolean isOccupied;
    //todo: entity

    public Cell(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.isOccupied = false;
    }

    public Cell(Coordinate coordinate, boolean isOccupied) {
        this.coordinate = coordinate;
        this.isOccupied = isOccupied;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public synchronized void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
}
