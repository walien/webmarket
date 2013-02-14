package fr.webmarket.backend.rest.auth;

import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;

import fr.webmarket.backend.model.User;

public class ClientSessionManager {

	private static ClientSessionManager INSTANCE;

	private Map<User, UUID> sessions;

	private ClientSessionManager() {
		sessions = Maps.newHashMap();
	}

	public static ClientSessionManager getInstance() {
		if (null == INSTANCE) {
			INSTANCE = new ClientSessionManager();
		}
		return INSTANCE;
	}

	public UUID getSessionID(User u) {
		return sessions.get(u);
	}

	public UUID createSession(User u) {
		UUID newID = UUID.randomUUID();
		sessions.put(u, newID);
		return newID;
	}

	public boolean checkSession(User u, UUID id) {
		return sessions.get(u) == id;
	}

	public UUID destroySession(User u) {
		return sessions.remove(u);
	}

}
