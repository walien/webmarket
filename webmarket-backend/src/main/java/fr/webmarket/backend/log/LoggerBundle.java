package fr.webmarket.backend.log;

import java.util.logging.Logger;

public class LoggerBundle {

	private static final String WEBMARKET_BACKEND_LOGGER = "webmarket-backend";

	public static Logger getDefaultLogger() {
		return Logger.getLogger(WEBMARKET_BACKEND_LOGGER);
	}

}
