package cristiancmello.javalogging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    public static void main(String[] args) throws IOException {
        final var myLogger = Logger.getLogger(Main.class.getName());

        var handler = new FileHandler("logs.txt");
        handler.setFormatter(new SimpleFormatter());

        myLogger.addHandler(handler);

        myLogger.setLevel(Level.FINER);

        myLogger.entering(Main.class.getName(), "main", "Java!");
    }

    public static void readFile() {
        System.out.println("Reading file...");
    }
}