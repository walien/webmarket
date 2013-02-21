package fr.webmarket.backend.rest.auth;

import java.util.UUID;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.webmarket.backend.datasource.DataSourcesBundle;
import fr.webmarket.backend.model.User;

@Path("/auth")
public class AuthREST {

	@POST
	@Path("login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("username") String username,
			@FormParam("pwd") String pwd) {

		// Retrieve the user
		User u = DataSourcesBundle.getDefaultDataSource().getUsers()
				.get(username);
		if (u == null) {
			return null;
		}

		// Check if the session doesn't exists yet
		UUID sessionID = ClientSessionManager.getInstance().getSessionID(u);
		if (sessionID != null) {
			return sessionID.toString();
		}

		// Check the password
		if (u.getPwd().equals(pwd) == false) {
			return null;
		}

		String newSessionID = ClientSessionManager.getInstance()
				.createSession(u).toString();

		return newSessionID;
	}

	@POST
	@Path("logout")
	public String logout(@FormParam("username") String username) {

		User u = DataSourcesBundle.getDefaultDataSource().getUsers()
				.get(username);
		if (u == null) {
			return new Boolean(false).toString();
		}

		return new Boolean(
				ClientSessionManager.getInstance().destroySession(u) != null)
				.toString();
	}
}
