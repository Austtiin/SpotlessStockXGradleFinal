
// Purpose: Create a logger for the StockX application. This logger will log all events that occur in the application.

package logger;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class LoggerStockX {

    public static final Logger logger = Logger.getLogger("StockXLogger");
    private static FileHandler fh;

    static {
        try {
            // Create log file
            fh = new FileHandler("StockXLog.log");

            // Ensure thread safety during logger and file handler initialization
            synchronized (LoggerStockX.class) {
                logger.addHandler(fh);

                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);

                // Set default level
                logger.setLevel(Level.INFO);
            }
        } catch (Exception e) {
            // Log error if file handler fails to load
            logger.log(Level.SEVERE, "Failed to load Filehandler", e);
        }
    }

    public static void setLogLevel(Level level) {
        // Method to set the logging level dynamically
        logger.setLevel(level);
    }
}
