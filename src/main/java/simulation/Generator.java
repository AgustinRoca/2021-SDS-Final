package simulation;

import simulation.models.Cell;
import simulation.models.Position;
import simulation.models.Tree;
import simulation.models.TreeState;

import java.util.ArrayList;
import java.util.List;

public class Generator {

    public static List<List<Cell>> generateMatrix(int rows, int columns, double initialTemperature, double cellSize, double treeRatio){
        List<List<Cell>> matrix = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            matrix.add(new ArrayList<>(columns));
            for (int j = 0; j < columns; j++) {
                if(Math.random() < treeRatio){
                    matrix.get(i).add(new Cell(
                            new Tree(new Position(j*cellSize, (rows - 1 - i)*cellSize), initialTemperature)));
                } else {
                    matrix.get(i).add(new Cell(null));
                }
            }
        }

        Tree burningTree = new Tree(
                new Position((columns/2)*cellSize, (rows - 1 - (rows/2))*cellSize), Tree.getStartingTemperature());
        burningTree.setState(TreeState.ON_FIRE);
        matrix.get(rows/2).set(columns/2, new Cell(burningTree));
        return matrix;
    }
}
