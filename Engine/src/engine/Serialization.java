package engine;

import java.io.*;

public class Serialization {
    public static EngineImpl readSystemFromFile(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream(path))) {
            EngineImpl engineImpl = (EngineImpl) in.readObject();
            return engineImpl;
        }
    }
    public static void writeSystemToFile(String path, EngineImpl engineImpl) throws IOException {
        try (ObjectOutputStream out =
                        new ObjectOutputStream(
                                new FileOutputStream(path))) {
                out.writeObject(engineImpl);
                out.flush();
            }
    }
}
