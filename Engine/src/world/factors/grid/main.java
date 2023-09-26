package world.factors.grid;

import world.factors.grid.Coordinate;
import world.factors.grid.api.GridDefinition;
import world.factors.grid.api.GridDefinitionImpl;
import world.factors.grid.execution.GridInstance;
import world.factors.grid.execution.GridInstanceImpl;

import java.util.Collection;

public class main {
    public static void main(String[] args) {
        GridDefinition gridDefinition = new GridDefinitionImpl(8, 13);
        GridInstance grid = new GridInstanceImpl(gridDefinition);
        /*for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(grid.getCoordinate(i, j).getX() + " " + grid.getCoordinate(i, j).getY() + " | ");
            }
            System.out.println();
        }*/
        Collection<Coordinate> list = grid.findEnvironmentCells(grid.getCoordinate(6, 6), 2);
        for (int y = 0; y < gridDefinition.getHeight(); y++) {
            for (int x = 0; x < gridDefinition.getWidth(); x++) {
                if (list.contains(grid.getCoordinate(x, y))) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }
}
