package fr.webmarket.backend.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerBundle {

    private static final String WEBMARKET_BACKEND_LOGGER = "webmarket-backend";

    public static Logger getDefaultLogger() {
        return LoggerFactory.getLogger(WEBMARKET_BACKEND_LOGGER);
    }

}
