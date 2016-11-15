package de.meonwax.unsigned.util;

public class Logger {

    public final static int LEVEL_DEBUG = 1;
    public final static int LEVEL_INFO = 2;
    public final static int LEVEL_WARN = 3;
    public final static int LEVEL_ERROR = 4;

    public static void debug(String msg) {
        log(LEVEL_DEBUG, msg);
    }

    public static void info(String msg) {
        log(LEVEL_INFO, msg);
    }

    public static void warn(String msg) {
        log(LEVEL_WARN, msg);
    }

    public static void error(String msg) {
        log(LEVEL_ERROR, msg);
    }

    public static void log(int level, String msg) {
        switch (level) {
            case LEVEL_DEBUG:
                System.out.println(msg);
                break;
            case LEVEL_INFO:
                System.out.println(msg);
                break;
            case LEVEL_WARN:
                System.out.println("WARN:\t" + msg);
                break;
            case LEVEL_ERROR:
                System.err.println("ERROR:\t" + msg);
                break;
        }
    }
}
