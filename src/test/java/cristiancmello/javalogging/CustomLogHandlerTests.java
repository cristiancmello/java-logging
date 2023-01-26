package cristiancmello.javalogging;

import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.*;

public class CustomLogHandlerTests {
    @Test
    void shouldInMemoryHandlerStoreMyLoggerLogs() {
        final var myLogger = Logger.getLogger("cristiancmello.javalogging.inmemoryhandler");

        myLogger.setUseParentHandlers(false);
        myLogger.addHandler(new InMemoryHandler());

        myLogger.info("Log Info testing...");
        myLogger.severe("Log Severe testing...");
        myLogger.warning("Log Warning testing...");

        assertThat(InMemoryHandler.logRecords)
            .filteredOn(p -> p.getMessage().contains("Log Info testing..."))
            .extracting("level")
            .contains(Level.INFO)
            .hasSize(1);

        assertThat(InMemoryHandler.logRecords)
            .filteredOn(p -> p.getMessage().equalsIgnoreCase("log severe testing..."))
            .extracting("level")
            .contains(Level.SEVERE)
            .hasSize(1);

        assertThat(InMemoryHandler.logRecords)
            .filteredOn(p -> p.getMessage().contains("Log Warning testing..."))
            .extracting("level")
            .contains(Level.WARNING)
            .hasSize(1);
    }
}
