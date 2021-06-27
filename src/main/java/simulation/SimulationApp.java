package simulation;

import simulation.models.Cell;
import simulation.models.TreeState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SimulationApp {
    private static final double DT = 1; // min
    private static final double TREE_RATIO = 0.6;
    private static final double ALPHA_MAX = 1.1;
    private static final double ALPHA_MIN = 0.9;
    private static final String OUTPUT_PATH = "./data/output.txt";


    public static void main(String[] args) {
        List<List<List<Cell>>> matrixes = new ArrayList<>();
        matrixes.add(WildfireSimulation.initializeMatrix(TREE_RATIO));

        for (int round = 0; !WildfireSimulation.burntOut(matrixes.get(matrixes.size() - 1)) && round < 900; round++) {
            if(round % 10 == 0)
                System.out.println("Round " + round);
            matrixes.add(WildfireSimulation.nextRound(matrixes.get(matrixes.size() - 1), ALPHA_MAX, ALPHA_MIN, DT));
        }

        System.out.println("Termine");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.get(OUTPUT_PATH).toAbsolutePath().toString(), false));
            writer.write("" + DT + " - " + ALPHA_MIN + " - " + ALPHA_MAX + "\n");
            int t=0;
            for (List<List<Cell>> matrix : matrixes){
                if(t % 3*DT == 0){
                    writer.write("" + t + "\n");
                    for(List<Cell> row : matrix){
                        for (Cell cell : row){
                            if(cell.getTree() != null){
                                if(cell.getTree().getState() == TreeState.DEAD){
                                    writer.write("D ");
                                } else {
                                    writer.write(cell.getTree().getTemperature() + " ");
                                }
                            } else {
                                writer.write("N ");
                            }
                        }
                        writer.write("\n");
                    }
                    writer.write("\n");
                }
                t+=DT;
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
