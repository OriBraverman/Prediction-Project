package world.factors.grid.execution;




import world.factors.grid.Cell;
import world.factors.grid.Coordinate;
import world.factors.grid.Direction;
import world.factors.grid.api.GridDefinition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class GridInstanceImpl implements GridInstance, Serializable {
    private GridDefinition gridDefinition;
    private Cell[][] grid;

    public GridInstanceImpl(GridDefinition gridDefinition) {
        this.gridDefinition = gridDefinition;
        int n = gridDefinition.getHeight();
        int m = gridDefinition.getWidth();
        grid = new Cell[n][m];
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < m; x++) {
                grid[y][x] = new Cell(new Coordinate(x, y));
            }
        }
    }

    @Override
    public Coordinate getCoordinate(int x, int y) {
        return grid[y][x].getCoordinate();
    }

    private Cell getCell(Coordinate coordinate) {
        return grid[coordinate.getY()][coordinate.getX()];
    }

    @Override
    public boolean isCellFree(Coordinate coordinate) {
        return !grid[coordinate.getY()][coordinate.getX()].isOccupied();
    }

    @Override
    public Cell moveEntity(Coordinate source, Direction direction) {
        Coordinate destination = getCoordinateInDirection(source, direction);
        if (isCellFree(destination)) {
            getCell(source).setOccupied(false);
            getCell(destination).setOccupied(true);
            return getCell(destination);
        }
        return null;
    }

    public int getWidth() {
        return this.gridDefinition.getWidth();
    }

    public int getHeight() {
        return this.gridDefinition.getHeight();
    }

    private Coordinate getCoordinateInDirection(Coordinate source, Direction direction) {
        switch (direction) {
            case UP:
                return getCoordinate(source.getX(), (source.getY() - 1 + getHeight()) % getHeight());
            case DOWN:
                return getCoordinate(source.getX(), (source.getY() + 1 + getHeight()) % getHeight());
            case LEFT:
                return getCoordinate((source.getX() - 1 + getWidth()) % getWidth(), source.getY());
            case RIGHT:
                return getCoordinate((source.getX() + 1 + getWidth()) % getWidth(), source.getY());
            default:
                return source;
        }
    }

    //write a function, that, given a source coordinate and a rank (integer), returns the (accumulative) list of cells (coordinates) that surrounds the given source coordinate.
    //Note: for Rank X, the list will include all the cells of Rank x-1 and x-2 until Rank = 1
    @Override
    public Collection<Coordinate> findEnvironmentCells(Coordinate source, int rank) {
        Collection<Coordinate> environmentCells = new ArrayList<>();
        for (int i = 1; i <= rank; i++) {
            environmentCells.addAll(getRankCells(source, i));
        }
        return environmentCells;
    }

    private Collection<Coordinate> getRankCells(Coordinate source, int rank) {
        Collection<Coordinate> rankCells = new HashSet<>();
        for (Direction d: Direction.values()){
            rankCells.addAll(getCellsInDirection(source, rank, d));
        }
        return rankCells;
    }

    private Collection<Coordinate> getCellsInDirection(Coordinate source, int rank, Direction direction) {
        Collection<Coordinate> directionCells = new HashSet<>();
        int rankDiameter = 2 * rank + 1;
        if (rankDiameter <= getHeight() && rankDiameter <= getWidth()) {
            directionCells.addAll(getCellsInDirectionHelper(source, rank, direction));
        } else if ((direction == Direction.UP || direction == Direction.DOWN) && rankDiameter > getWidth()) {
            directionCells.addAll(getLineInDirection(source, rank, direction));
        } else if ((direction == Direction.LEFT || direction == Direction.RIGHT) && rankDiameter > getHeight()) {
            directionCells.addAll(getLineInDirection(source, rank, direction));
        } else if (direction == Direction.LEFT && rankDiameter == getWidth() + 1 && rankDiameter <= getHeight()) {
            directionCells.addAll(getCellsInDirectionHelper(source, rank, Direction.LEFT));
        } else if (direction == Direction.UP && rankDiameter == getHeight() + 1 && rankDiameter <= getWidth()) {
            directionCells.addAll(getCellsInDirectionHelper(source, rank, Direction.UP));
        }
        return directionCells;
    }

    private Collection<Coordinate> getLineInDirection(Coordinate source, int rank, Direction direction) {
        Coordinate from, to;
        int hDiameter = getWidth(), vDiameter = getHeight();
        switch (direction) {
            case UP:
                from = getCoordinate(0, (source.getY() - rank + vDiameter) % vDiameter);
                to = getCoordinate(hDiameter - 1, (source.getY() - rank + vDiameter) % vDiameter);
                break;
            case DOWN:
                from = getCoordinate(0, (source.getY() + rank + vDiameter) % vDiameter);
                to = getCoordinate(hDiameter - 1, (source.getY() + rank + vDiameter) % vDiameter);
                break;
            case LEFT:
                from = getCoordinate((source.getX() - rank + hDiameter) % hDiameter, 0);
                to = getCoordinate((source.getX() - rank + hDiameter) % hDiameter, vDiameter - 1);
                break;
            case RIGHT:
                from = getCoordinate((source.getX() + rank + hDiameter) % hDiameter, 0);
                to = getCoordinate((source.getX() + rank + hDiameter) % hDiameter, vDiameter - 1);
                break;
            default:
                return new HashSet<>();
        }
        return getCellsInTheSameLine(from, to);
    }

    private Collection<Coordinate> getCellsInDirectionHelper(Coordinate source, int rank, Direction direction) {
        Coordinate from, to;
        int hDiameter = getWidth(), vDiameter = getHeight();
        switch (direction) {
            case LEFT:
                from = getCoordinate((source.getX() - rank + hDiameter) % hDiameter, (source.getY() - rank + vDiameter) % vDiameter);
                to = getCoordinate((source.getX() - rank + hDiameter) % hDiameter, (source.getY() + rank + vDiameter) % vDiameter);
                break;
            case RIGHT:
                from = getCoordinate((source.getX() + rank + hDiameter) % hDiameter, (source.getY() - rank + vDiameter) % vDiameter);
                to = getCoordinate((source.getX() + rank + hDiameter) % hDiameter, (source.getY() + rank + vDiameter) % vDiameter);
                break;
            case UP:
                from = getCoordinate((source.getX() - rank + hDiameter) % hDiameter, (source.getY() - rank + vDiameter) % vDiameter);
                to = getCoordinate((source.getX() + rank + hDiameter) % hDiameter, (source.getY() - rank + vDiameter) % vDiameter);
                break;
            case DOWN:
                from = getCoordinate((source.getX() - rank + hDiameter) % hDiameter, (source.getY() + rank + vDiameter) % vDiameter);
                to = getCoordinate((source.getX() + rank + hDiameter) % hDiameter, (source.getY() + rank + vDiameter) % vDiameter);
                break;
            default:
                return new HashSet<>();
        }
        return getCellsInTheSameLine(from, to);
    }

    private Collection<Coordinate> getCellsInTheSameLine(Coordinate from, Coordinate to) {
        int horizontalDiameter = getWidth();
        int verticalDiameter = getHeight();

        Collection<Coordinate> lineCells = new HashSet<>();
        if (from.getX() != to.getX() && from.getY() != to.getY()) {
            return lineCells;
        }
        else if (from.getX() == to.getX()) {
            int y = from.getY();
            int x = from.getX();
            while (y != to.getY()) {
                lineCells.add(getCoordinate(x, y));
                y = (y + 1) % verticalDiameter;
            }
            lineCells.add(getCoordinate(x, y));
            return lineCells;
        }
        else {
            int x = from.getX();
            int y = from.getY();
            while (x != to.getX()) {
                lineCells.add(getCoordinate(x, y));
                x = (x + 1) % horizontalDiameter;
            }
            lineCells.add(getCoordinate(x, y));
            return lineCells;
        }
    }

    @Override
    public Cell getRandomAvailableCell() {
        int horizontalDiameter = getWidth();
        int verticalDiameter = getHeight();
        List<Cell> availableCells = new ArrayList<>();
        for (int i = 0; i < verticalDiameter; i++) {
            for (int j = 0; j < horizontalDiameter; j++) {
                if (!grid[i][j].isOccupied()) {
                    availableCells.add(grid[i][j]);
                }
            }
        }
        if (availableCells.isEmpty()) {
            throw new IllegalStateException("Grid is full");
        }
        Cell cell = availableCells.get((int) (Math.random() * availableCells.size()));
        cell.setOccupied(true);
        return cell;
    }

    @Override
    public int getNumberOfOccupiedCells() {
        int count = 0;
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                if (cell.isOccupied()) {
                    count++;
                }
            }
        }
        return count;
    }
}
