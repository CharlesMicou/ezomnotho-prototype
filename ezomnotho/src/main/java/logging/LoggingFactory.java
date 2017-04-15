package logging;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LoggingFactory {
    private static final String LOG_FOLDER = "../logs/" + System.currentTimeMillis() + "/";

    public static MarketLogger createMarketLogger() {
        maybeInitDirectory();
        return MarketLogger.create(MiniLogger.create(LOG_FOLDER + "market.log"));
    }

    public static AgentLogger createAgentLogger(String agentName) {
        maybeInitDirectory();
        return AgentLogger.create(MiniLogger.create(LOG_FOLDER + agentName + ".log"));
    }

    private static void maybeInitDirectory() {
        try {
            Files.createDirectories(Paths.get(LOG_FOLDER));
        } catch (IOException e) {
            // fall through
        }
    }
}
