package fr.webmarket.backend.rest.auth;

import fr.webmarket.backend.datasource.DataSourcesBundle;
import fr.webmarket.backend.model.User;
import fr.webmarket.backend.utils.DigestUtils;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

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

        // Check the password (compare MD5)
        String md5Pwd = DigestUtils.computeMD5(pwd);
        if (!u.getPwd().equals(md5Pwd)) {
            return null;
        }

        return ClientSessionManager.getInstance()
                .createSession(u).toString();
    }

    @POST
    @Path("logout")
    public boolean logout(@FormParam("username") String username) {

        User u = DataSourcesBundle.getDefaultDataSource().getUsers()
                .get(username);
        if (u == null) {
            return false;
        }

        return ClientSessionManager.getInstance().destroySession(u) != null;
    }
}
