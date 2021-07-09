package simulation.experiment.varyingAlpha;

import simulation.WildfireSimulation;
import simulation.models.Cell;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class TimeToExtinguish {
    private static final double DT = 60; // s
    private static final double TREE_RATIO = 0.3;
    private static final double STEPS = 4;
    private static final double ALPHA_RATIO = 3;
    private static final double ALPHA_MAX_MIN = 0.3;
    private static final double ALPHA_MAX_MAX = 1.3;
    private static final String OUTPUT_PATH = "./data/experiment/alphaTimeToExtinguish.txt";


    public static void main(String[] args) {
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.get(OUTPUT_PATH).toAbsolutePath().toString(), false));

            for (double alphaMax = ALPHA_MAX_MIN; alphaMax <= ALPHA_MAX_MAX; alphaMax += (ALPHA_MAX_MAX - ALPHA_MAX_MIN) / (STEPS - 1)) {
                double alphaMin = alphaMax / ALPHA_RATIO;
                writer.write("" + DT + " - " + alphaMax + " - " + alphaMin + "\n");
                List<List<Cell>> lastMatrix = WildfireSimulation.initializeMatrix(TREE_RATIO);
                int round = 0;
                for (; !WildfireSimulation.burntOut(lastMatrix); round++) {
                    if (round % 10 == 0)
                        System.out.println("Round " + round);
                    lastMatrix = WildfireSimulation.nextRound(lastMatrix, alphaMax, alphaMin, DT);
                }
                writer.write("" + (round * DT / 60) + "\n");
            }

            System.out.println("Termine");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
