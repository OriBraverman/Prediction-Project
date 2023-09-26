package simulation;

import java.io.*;

public class Serialization {
    public static SimulationExecutionDetails readSystemFromFile(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream(path))) {
            SimulationExecutionDetails simulationExecutionDetails = (SimulationExecutionDetails) in.readObject();
            return simulationExecutionDetails;
        }
    }
    public static void writeSystemToFile(String path, SimulationExecutionDetails simulationExecutionDetails) throws IOException {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream(path))) {
            out.writeObject(simulationExecutionDetails);
            out.flush();
        }
    }
}
