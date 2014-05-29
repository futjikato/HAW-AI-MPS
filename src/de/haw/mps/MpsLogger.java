package de.haw.mps;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public final class MpsLogger {

    private static MpsLogger instance;

    private Logger logger;

    private MpsLogger() {
        logger = Logger.getLogger("MPS");
        try {
            logger.addHandler(new FileHandler("mps_log.txt"));
        } catch (IOException e) {
            // well ... then log just on the cmd. No need to quit or do stupid things
            e.printStackTrace();
        }
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
}
