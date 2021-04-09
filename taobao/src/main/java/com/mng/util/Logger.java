package com.mng.util;

import com.mng.TaobaoApplication;
import org.slf4j.LoggerFactory;

/**
 * 单例模式的日志工具类，请避免使用System.out.println
 */
public class Logger {

    private static org.slf4j.Logger logger;

    public static synchronized org.slf4j.Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.getLogger(TaobaoApplication.class);
        }
        return logger;
    }

    public static void info(String message) {
        getLogger().info(message);
    }

    public static void info(Object message) {
        getLogger().info(String.valueOf(message));
    }

    public static void debug(String message) {
        getLogger().debug(message);
    }

    public static void debug(Object message) {
        getLogger().debug(String.valueOf(message));
    }

    public static void warning(String message) {
        getLogger().warn(message);
    }

    public static void warning(Object message) {
        getLogger().warn(String.valueOf(message));
    }

    public static void error(String message) {
        getLogger().error(message);
    }

    public static void error(Object message) {
        getLogger().error(String.valueOf(message));
    }

    public static void error(String message, Throwable e) {
        getLogger().error(message, e);
    }
}
