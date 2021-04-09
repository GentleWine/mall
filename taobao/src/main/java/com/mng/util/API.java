package com.mng.util;

import com.mng.TaobaoApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class API {

    private static Logger logger;

    public static synchronized Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.getLogger(TaobaoApplication.class);
        }
        return logger;
    }

    public static void logInfo(String message) {
        getLogger().info(message);
    }

    public static void logDebug(String message) {
        getLogger().debug(message);
    }

    public static void logWarning(String message) {
        getLogger().warn(message);
    }

    public static void logError(String message) {
        getLogger().error(message);
    }

    public static void logError(String message, Throwable e) {
        getLogger().error(message, e);
    }
}
