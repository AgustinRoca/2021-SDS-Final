package simulation.experiment.varyingTreeRatio;

import simulation.WildfireSimulation;
import simulation.models.Cell;
import simulation.models.TreeState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class AliveTreesAfterFire {
    private static final double DT = 60; // s
    private static final double TREE_RATIO_MIN = 0.1;
    private static final double TREE_RATIO_MAX = 0.9;
    private static final double STEPS = 4;
    private static final double ALPHA_MAX = 0.5;
    private static final double ALPHA_MIN = 0.15;
    private static final String OUTPUT_PATH = "./data/experiment/ratioAliveTrees.txt";
    private static final int MAX_ITERATIONS = 20;

    public static void main(String[] args) {
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.get(OUTPUT_PATH).toAbsolutePath().toString(), false));

            for (double treeRatio = TREE_RATIO_MIN; Double.compare(treeRatio, TREE_RATIO_MAX) <= 0; treeRatio += (TREE_RATIO_MAX - TREE_RATIO_MIN) / (STEPS - 1)) {
                writer.write("" + DT + " - " + treeRatio + "\n");
                int alive = 0;
                for(int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {
                    List<List<Cell>> lastMatrix = WildfireSimulation.initializeMatrix(treeRatio);
                    for (int round = 0; !WildfireSimulation.burntOut(lastMatrix); round++) {
                        if (round % 10 == 0)
                            System.out.println("Round " + round);
                        lastMatrix = WildfireSimulation.nextRound(lastMatrix, ALPHA_MAX, ALPHA_MIN, DT);
                    }

                    for (List<Cell> row : lastMatrix) {
                        for (Cell cell : row) {
                            if (cell.getTree() != null && cell.getTree().getState() == TreeState.HEALTHY) {
                                alive++;
                            }
                        }
                    }
                }
                double aliveAvg = (double) alive / MAX_ITERATIONS;
                writer.write("" + aliveAvg + "\n");
            }

            System.out.println("Termine");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
