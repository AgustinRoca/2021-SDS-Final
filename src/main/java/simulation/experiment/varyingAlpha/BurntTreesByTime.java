package simulation.experiment.varyingAlpha;

import simulation.WildfireSimulation;
import simulation.models.Cell;
import simulation.models.TreeState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BurntTreesByTime {
    private static final double DT = 60; // s
    private static final double TREE_RATIO = 0.3;
    private static final double STEPS = 4;
    private static final double ALPHA_RATIO = 3;
    private static final double ALPHA_MAX_MIN = 0.3;
    private static final double ALPHA_MAX_MAX = 1.3;
    private static final String OUTPUT_PATH = "./data/experiment/alphaBurnTree.txt";
    private static final int MAX_ITERATIONS = 20;

    public static void main(String[] args) {
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.get(OUTPUT_PATH).toAbsolutePath().toString(), false));

            for(double alphaMax = ALPHA_MAX_MIN; alphaMax <=  ALPHA_MAX_MAX; alphaMax+=(ALPHA_MAX_MAX-ALPHA_MAX_MIN)/(STEPS-1)) {
                double alphaMin = alphaMax / ALPHA_RATIO;
                writer.write("" + DT + " - " + alphaMax + " - " + alphaMin + "\n");
                Map<Double, Long> timeToBurnTreesMap = new HashMap<>();
                double minTime = -1;
                for(int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {
                    System.out.println("Iteration: " + iteration);
                    List<List<Cell>> lastMatrix = WildfireSimulation.initializeMatrix(TREE_RATIO);
                    int round = 0;
                    for (; !WildfireSimulation.burntOut(lastMatrix); round++) {
                        if (round % 1000 == 0)
                            System.out.println("Round " + round);
                        lastMatrix = WildfireSimulation.nextRound(lastMatrix, alphaMax, alphaMin, DT);
                        double t = DT * round;
                        if (t % 600 * DT == 0) {
                            int burntTrees = 0;
                            for (List<Cell> row : lastMatrix) {
                                for (Cell cell : row) {
                                    if (cell.getTree() != null && cell.getTree().getState() == TreeState.DEAD) {
                                        burntTrees++;
                                    }
                                }
                            }
                            timeToBurnTreesMap.put(t/60, timeToBurnTreesMap.getOrDefault(t/60, 0L) + burntTrees);
                        }
                    }
                    if(minTime == -1 || (round * DT/60) < minTime){
                        minTime = round * DT / 60;
                    }
                }
                for (Double time : timeToBurnTreesMap.keySet().stream().sorted().collect(Collectors.toList())){
                    if(time < minTime) { // El ultimo no cuenta
                        writer.write("" + time + ":" + (timeToBurnTreesMap.get(time) / (double) MAX_ITERATIONS) + "\n");
                    }
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
