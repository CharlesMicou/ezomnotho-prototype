package logging;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MiniLogger {
    private FileWriter writer;

    private MiniLogger(String filePath) {
        try {
            Path logFilePath = Paths.get(filePath);
            Files.createFile(logFilePath);
            this.writer = new FileWriter(filePath, true);
        } catch (IOException e) {
            System.out.println("Blew up making the logger. " + e);
        }
    }

    public static MiniLogger create(String filePath) {
        return new MiniLogger(filePath);
    }

    void write(JSONObject json) {
        try {
            writer.write(json.toJSONString() + "\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Blew up writing to the logger. " + e);
        }
    }

    /**
     * Java has no destructors :(
     */
    void close() {
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("Blew up writing closing the logger. " + e);
        }
    }
}
