package simulation.experiment.varyingAlpha;

import simulation.WildfireSimulation;
import simulation.models.Cell;
import simulation.models.TreeState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
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

            double alphaMin = 0.5;
            for (double alphaMax = ALPHA_MAX_MIN; alphaMax <= ALPHA_MAX_MAX; alphaMax += (ALPHA_MAX_MAX - ALPHA_MAX_MIN) / (STEPS - 1)) {
                alphaMin -= 0.1;
                writer.write("" + DT + " ; " + alphaMax + " ; " + alphaMin + "\n");
                Map<Double, List<Double>> timeToBurnTreesMap = new HashMap<>();
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
                            if (!timeToBurnTreesMap.containsKey(t/60)){
                                timeToBurnTreesMap.put(t/60, new ArrayList<>());
                            }
                            timeToBurnTreesMap.get(t / 60).add((double) burntTrees);
                        }
                    }
                    if(minTime == -1 || (round * DT/60) < minTime){
                        minTime = round * DT / 60;
                    }
                }
                for (Double time : timeToBurnTreesMap.keySet().stream().sorted().collect(Collectors.toList())){
                    if(time < minTime) { // El ultimo no cuenta
                        writer.write("" + time + ":" + calculateMean(timeToBurnTreesMap.get(time)) + ";" + calculateSD(timeToBurnTreesMap.get(time)) + "\n");
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

    public static double calculateSD(List<Double> numbers)
    {
        double standardDeviation = 0.0;

        double mean = calculateMean(numbers);

        for(double num: numbers) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation/numbers.size());
    }

    public static double calculateMean(List<Double> numbers) {
        double sum = 0.0;
        int length = numbers.size();

        for (double num : numbers) {
            sum += num;
        }

        return sum / length;
    }
}
