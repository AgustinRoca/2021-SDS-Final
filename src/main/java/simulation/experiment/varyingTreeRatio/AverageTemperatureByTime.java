package simulation.experiment.varyingTreeRatio;

import simulation.WildfireSimulation;
import simulation.models.Cell;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AverageTemperatureByTime {
    private static final double DT = 60; // s
    private static final double TREE_RATIO_MIN = 0.1;
    private static final double TREE_RATIO_MAX = 0.9;
    private static final double STEPS = 4;
    private static final double ALPHA_MAX = 0.5;
    private static final double ALPHA_MIN = 0.15;
    private static final String OUTPUT_PATH = "./data/experiment/ratioAvgTemp.txt";
    private static final int MAX_ITERATIONS = 20;

    public static void main(String[] args) {
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.get(OUTPUT_PATH).toAbsolutePath().toString(), false));

            for (double treeRatio = TREE_RATIO_MIN; Double.compare(treeRatio, TREE_RATIO_MAX) <= 0; treeRatio += (TREE_RATIO_MAX - TREE_RATIO_MIN) / (STEPS - 1)) {
                writer.write("" + DT + " - " + treeRatio + "\n");
                Map<Double, Double> timeToTempMap = new HashMap<>();
                for(int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {
                    List<List<Cell>> lastMatrix = WildfireSimulation.initializeMatrix(treeRatio);
                    for (int round = 0; !WildfireSimulation.burntOut(lastMatrix); round++) {
                        if (round % 10 == 0)
                            System.out.println("Round " + round);
                        lastMatrix = WildfireSimulation.nextRound(lastMatrix, ALPHA_MAX, ALPHA_MIN, DT);

                        double t = DT * round;
                        if (t % 600 * DT == 0) {
                            int tempAccum = 0;
                            int trees = 0;
                            for (List<Cell> row : lastMatrix) {
                                for (Cell cell : row) {
                                    if (cell.getTree() != null) {
                                        trees++;
                                        tempAccum += cell.getTree().getTemperature();
                                    }
                                }
                            }
                            timeToTempMap.put(t/60, timeToTempMap.getOrDefault(t/60, 0.0) + tempAccum/trees);
                        }
                    }
                }
                for (Double time : timeToTempMap.keySet()){
                    writer.write("" + time + ":" + (timeToTempMap.get(time)/MAX_ITERATIONS) + "\n");
                }
                writer.write('\n');
            }

            System.out.println("Termine");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
