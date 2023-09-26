package engine;

import java.io.*;

public class Serialization {
    public static Engine readSystemFromFile(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream(path))) {
            Engine engine = (Engine) in.readObject();
            return engine;
        }
    }
    public static void writeSystemToFile(String path, Engine engine) throws IOException {
        try (ObjectOutputStream out =
                        new ObjectOutputStream(
                                new FileOutputStream(path))) {
                out.writeObject(engine);
                out.flush();
            }
    }
}
