package world.factors.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public static List<Direction> getDirections() {
        List<Direction> directions = new ArrayList<>();
        directions.add(Direction.UP);
        directions.add(Direction.DOWN);
        directions.add(Direction.LEFT);
        directions.add(Direction.RIGHT);
        return directions;
    }
}
