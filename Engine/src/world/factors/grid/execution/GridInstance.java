package world.factors.grid.execution;

import world.factors.grid.Cell;
import world.factors.grid.Coordinate;
import world.factors.grid.Direction;

import java.util.Collection;

public interface GridInstance {
    Coordinate getCoordinate(int x, int y);
    boolean isCellFree(Coordinate coordinate);
    Cell moveEntity(Coordinate source, Direction direction);
    Collection<Coordinate> findEnvironmentCells(Coordinate source, int rank);
    Cell getRandomAvailableCell();
    int getWidth();
    int getHeight();
    int getNumberOfOccupiedCells();
}
