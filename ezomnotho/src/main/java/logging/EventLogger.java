package logging;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EventLogger {
    @SuppressWarnings("unchecked")

    public EventLogger() {
    }

    public void test() throws IOException {
        JSONObject object = new JSONObject();
        object.put("something", "another thing");
        Files.createDirectories(Paths.get("logs"));
        Files.createFile(Paths.get("logs/test.txt"));

        try (FileWriter file = new FileWriter("logs/test.txt")) {
            file.write(object.toJSONString());
            file.close();
        }
    }


}
