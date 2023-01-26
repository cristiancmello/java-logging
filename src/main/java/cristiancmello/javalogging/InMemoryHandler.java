package cristiancmello.javalogging;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class InMemoryHandler extends Handler {
    public static ArrayList<LogRecord> logRecords = new ArrayList<>();

    @Override
    public void publish(LogRecord record) {
        logRecords.add(record);
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {
        logRecords = null;
    }
}
