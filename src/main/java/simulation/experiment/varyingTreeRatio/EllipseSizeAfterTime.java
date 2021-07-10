package simulation.experiment.varyingTreeRatio;

import simulation.WildfireSimulation;
import simulation.models.Cell;
import simulation.models.TreeState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EllipseSizeAfterTime {
    private static final double DT = 60; // s
    private static final double TREE_RATIO_MIN = 0.1;
    private static final double TREE_RATIO_MAX = 0.9;
    private static final double STEPS = 4;
    private static final double ALPHA_MAX = 0.5;
    private static final double ALPHA_MIN = 0.15;
    private static final String OUTPUT_PATH = "./data/experiment/ratioEllipse.txt";
    private static final double TIME_TO_MEASURE = 500*60;
    private static final int MAX_ITERATIONS = 20;

    public static void main(String[] args) {
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.get(OUTPUT_PATH).toAbsolutePath().toString(), false));

            for (double treeRatio = TREE_RATIO_MIN; Double.compare(treeRatio, TREE_RATIO_MAX) <= 0; treeRatio += (TREE_RATIO_MAX - TREE_RATIO_MIN) / (STEPS - 1)) {
                writer.write("" + DT + " - " + treeRatio + "\n");
                List<Double> lengthList = new ArrayList<>();
                List<Double> widthList = new ArrayList<>();
                for(int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {

                    List<List<Cell>> lastMatrix = WildfireSimulation.initializeMatrix(treeRatio);
                    for (int round = 0; !WildfireSimulation.burntOut(lastMatrix) && round * DT < TIME_TO_MEASURE; round++) {
                        if (round % 10 == 0)
                            System.out.println("Round " + round);
                        lastMatrix = WildfireSimulation.nextRound(lastMatrix, ALPHA_MAX, ALPHA_MIN, DT);
                    }
                    int centerRow = WildfireSimulation.getMatrixSideLength() / 2;
                    int centerColumn = WildfireSimulation.getMatrixSideLength() / 2;
                    int minRow = centerRow;
                    for (int row = centerRow - 1; row >= 0; row--) {
                        if (lastMatrix.get(row).get(centerColumn).getTree() != null &&
                                lastMatrix.get(row).get(centerColumn).getTree().getState() != TreeState.HEALTHY) {
                            minRow = row;
                        }
                    }
                    int maxRow = centerRow;
                    for (int row = centerRow + 1; row < WildfireSimulation.getMatrixSideLength(); row++) {
                        if (lastMatrix.get(row).get(centerColumn).getTree() != null &&
                                lastMatrix.get(row).get(centerColumn).getTree().getState() != TreeState.HEALTHY) {
                            maxRow = row;
                        }
                    }
                    int minCol = centerColumn;
                    for (int col = centerColumn - 1; col >= 0; col--) {
                        if (lastMatrix.get(centerRow).get(col).getTree() != null &&
                                lastMatrix.get(centerRow).get(col).getTree().getState() != TreeState.HEALTHY) {
                            minCol = col;
                        }
                    }
                    int maxCol = centerColumn;
                    for (int col = centerColumn + 1; col < WildfireSimulation.getCellSize(); col++) {
                        if (lastMatrix.get(centerRow).get(col).getTree() != null &&
                                lastMatrix.get(centerRow).get(col).getTree().getState() != TreeState.HEALTHY) {
                            maxCol = col;
                        }
                    }
                    double length = (maxCol - minCol) * WildfireSimulation.getCellSize();
                    double width = (maxRow - minRow) * WildfireSimulation.getCellSize();
                    lengthList.add(length);
                    widthList.add(width);
                }
                writer.write("" + calculateMean(lengthList) + "-" + calculateSD(lengthList) +
                        "-" + calculateMean(widthList) + "-" + calculateSD(widthList) + "\n\n");
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
