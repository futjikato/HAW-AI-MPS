package de.haw.mps;

import sun.util.logging.resources.logging;

import java.util.logging.Logger;

public final class MpsLogger {

    private static MpsLogger instance;

    private Logger logger;

    private MpsLogger() {
        logger = Logger.getLogger("MPS");
    }

    private static MpsLogger getInstance() {
        if(instance == null) {
            instance = new MpsLogger();
        }

        return instance;
    }

    public static Logger getLogger() {
        return getInstance().logger;
    }

    public static void handleException(Exception e) {
        getLogger().severe(String.format("An Exception has been thrown in %s : %s", e.getClass(), e.getMessage()));
    }
}
