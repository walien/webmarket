package fr.webmarket.backend.rest.auth;

import java.util.UUID;

import fr.webmarket.backend.log.LoggerBundle;

public class AuthUtils {

	public static UUID parseSessionID(String sessionID) {

		if (sessionID == null) {
			return null;
		}

		try {
			return UUID.fromString(sessionID);
		} catch (Exception e) {
			LoggerBundle.getDefaultLogger().info(
					"Wrong uuid from REST request : " + sessionID);
		}
		return null;
	}

}
