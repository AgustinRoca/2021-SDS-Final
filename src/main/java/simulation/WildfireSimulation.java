package simulation;

import simulation.models.Cell;
import simulation.models.Position;
import simulation.models.Tree;
import simulation.models.TreeState;

import java.util.ArrayList;
import java.util.List;

public class WildfireSimulation {
    private static final int MATRIX_SIDE_LENGTH = 100;
    private static final double CELL_SIZE = 5; // m
    private static final double ROOM_TEMPERATURE = 298; // Kelvin
    private static final double INTERACTION_DISTANCE = 4;
    private static final double STEFAN_BOLTZMANN_CONSTANT = 5.67 * Math.pow(10, -8); // W / (m^2 * K^4)

    public static List<List<Cell>> initializeMatrix(double treeRatio){
        return Generator.generateMatrix(MATRIX_SIDE_LENGTH, MATRIX_SIDE_LENGTH, ROOM_TEMPERATURE, CELL_SIZE, treeRatio);
    }

    public static List<List<Cell>> nextRound(List<List<Cell>> oldCellMatrix, double alphaMax, double alphaMin, double timeStep){
        List<List<Cell>> newCellMatrix = cloneMatrix(oldCellMatrix);

        for (List<Cell> row : oldCellMatrix){
            for (Cell cell : row){
                if(cell.getTree() != null){
                    if (cell.getTree().getState() == TreeState.ON_FIRE){
                        transmitHeat(cell.getTree(), getAffectingTrees(newCellMatrix, cell.getTree().getPosition()), alphaMax, alphaMin, timeStep);
                        getNewTree(cell.getTree(), newCellMatrix).evoluteTemperature(timeStep);
                    }
                }
            }
        }
        return newCellMatrix;
    }

    private static Tree getNewTree(Tree tree, List<List<Cell>> newCellMatrix) {

        return newCellMatrix.get((int) (MATRIX_SIDE_LENGTH - 1 - tree.getPosition().getY() / CELL_SIZE)).get((int) (tree.getPosition().getX() / CELL_SIZE)).getTree();
    }

    private static void transmitHeat(Tree treeOnFire, List<Tree> affectingHealthyTrees, double alphaMax, double alphaMin, double timeStep) {
        double maxEnergy = STEFAN_BOLTZMANN_CONSTANT * Math.pow(treeOnFire.getTemperature(), 4); // W/m^2 = J/(m^2 * s)
        maxEnergy *= Math.PI * treeOnFire.getRadius() * treeOnFire.getRadius(); // J / s
        maxEnergy *= timeStep; // J
        for (Tree tree : affectingHealthyTrees) {
            double alpha = alphaMin + (alphaMax - alphaMin) * (Math.cos(treeOnFire.getPosition().angleBetween(tree.getPosition())) + 1) / 2;
            double distance = treeOnFire.getPosition().distanceTo(tree.getPosition());
            double energyAbsorbed = maxEnergy * Math.exp(-alpha * distance);
            tree.setTemperature(tree.getTemperature() + energyAbsorbed / (tree.getHeatCapacity() * tree.getMass()));

            if(tree.getTemperature() >= tree.getIgnitionTemperature()){
                tree.setTemperature(tree.getIgnitionTemperature());
                tree.setState(TreeState.ON_FIRE);
            }
        }
    }

    private static List<List<Cell>> cloneMatrix(List<List<Cell>> oldCellMatrix) {
        List<List<Cell>> newMatrix = new ArrayList<>(MATRIX_SIDE_LENGTH);
        for (List<Cell> oldRow : oldCellMatrix){
            newMatrix.add(new ArrayList<>(MATRIX_SIDE_LENGTH));
            for (Cell oldCell : oldRow){
                newMatrix.get(newMatrix.size() - 1).add(new Cell((oldCell.getTree() != null)?new Tree(oldCell.getTree()):null));
            }
        }
        return newMatrix;
    }

    private static List<Tree> getAffectingTrees(List<List<Cell>> cellMatrix, Position position) {
        List<Tree> treeList = new ArrayList<>();
        for (int i = (int)Math.max(position.getX()/CELL_SIZE - INTERACTION_DISTANCE, 0); i < (int) Math.min(position.getX()/CELL_SIZE + INTERACTION_DISTANCE + 1, MATRIX_SIDE_LENGTH); i++) {
            for (int j = (int)Math.max(position.getY()/CELL_SIZE - INTERACTION_DISTANCE, 0); j < (int) Math.min(position.getY()/CELL_SIZE + INTERACTION_DISTANCE + 1, MATRIX_SIDE_LENGTH); j++) {
                if(cellMatrix.get(MATRIX_SIDE_LENGTH-j-1).get(i).getTree() != null && cellMatrix.get(MATRIX_SIDE_LENGTH-j-1).get(i).getTree().getState() == TreeState.HEALTHY){
                    treeList.add(cellMatrix.get(MATRIX_SIDE_LENGTH-j-1).get(i).getTree());
                }
            }
        }
        return treeList;
    }

    public static boolean burntOut(List<List<Cell>> cellMatrix) {
        for (List<Cell> row : cellMatrix){
            for (Cell cell : row){
                if(cell.getTree() != null && cell.getTree().getState() == TreeState.ON_FIRE){
                    return false;
                }
            }
        }
        return true;
    }
}
