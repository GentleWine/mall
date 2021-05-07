package com.mng.util;

import com.mng.TaobaoApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.ThreadSafe;

/**
 * 单例模式的日志工具类，请避免使用System.out.println
 */
@ThreadSafe
@SuppressWarnings("unused")
public class Log {

    private static volatile Logger logger;

    public static Logger getLogger() {
        synchronized (Log.class) {
            if (logger == null) {
                synchronized (Log.class) {
                    logger = LoggerFactory.getLogger(TaobaoApplication.class);
                }
            }
        }
        return logger;
    }

    public static void i(String message) {
        getLogger().info(message);
    }

    public static void i(Object message) {
        getLogger().info(String.valueOf(message));
    }

    public static void d(String message) {
        getLogger().debug(message);
    }

    public static void d(Object message) {
        getLogger().debug(String.valueOf(message));
    }

    public static void w(String message) {
        getLogger().warn(message);
    }

    public static void w(Object message) {
        getLogger().warn(String.valueOf(message));
    }

    public static void e(String message) {
        getLogger().error(message);
    }

    public static void e(Object message) {
        getLogger().error(String.valueOf(message));
    }

    public static void e(String message, Throwable e) {
        getLogger().error(message, e);
    }
}
