package org.accenture.demo.utilities;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerUtil {
    private static final Logger LOGGER = Logger.getLogger(LoggerUtil.class.getName());

    public static void logInfo(String message) {
        LOGGER.log(Level.INFO, message);
    }

    public static void logError(String message) {
        LOGGER.log(Level.SEVERE, message);
    }
}