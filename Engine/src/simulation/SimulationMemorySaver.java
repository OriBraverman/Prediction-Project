package simulation;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SimulationMemorySaver {
    private static final String path = "SimulationMemorySaver";

    public static SimulationExecutionDetails readSEDByIdAndTick(int id, int tick) {
        String filePath = getPathForTick(id, tick);
        try {
            return Serialization.readSystemFromFile(filePath);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null; // Handle the error appropriately
        }
    }

    public static void writeSEDByIdAndTick(int id, int tick, SimulationExecutionDetails sed) {
        String filePath = getPathForTick(id, tick);
        File directory = new File(path);

        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory and any missing parent directories
        }

        try {
            Serialization.writeSystemToFile(filePath, sed);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the error appropriately
        }
    }

    public static void deleteDirectory() {
        File directory = new File(path);
        if (directory.exists()) {
            File[] files = directory.listFiles();
            for (File file : files) {
                file.delete();
            }
            directory.delete();
        }
    }

    private static String getPathForTick(int id, int tick) {
        return path + "/simulation_" + id + "_tick_" + tick + ".sed";
    }
}
