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
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public AuthResult login(@FormParam("username") String username,
                            @FormParam("pwd") String pwd) {

        // Auth result
        AuthResult authResult = new AuthResult();

        // Retrieve the user
        User u = DataSourcesBundle.getDefaultDataSource().getUsers()
                .get(username);
        if (u == null) {
            return authResult.setAuth(false);
        }

        // Check the password (compare MD5)
        String md5Pwd = DigestUtils.computeMD5(pwd);
        if (!u.getPwd().equals(md5Pwd)) {
            return authResult.setAuth(false);
        }

        // Check if the session doesn't exists yet
        UUID sessionID = ClientSessionManager.getInstance().getSessionID(u);
        if (sessionID != null) {
            return authResult.setAuth(true).setSessionID(sessionID.toString());
        }

        return authResult.setSessionID(ClientSessionManager.getInstance()
                .createSession(u).toString());
    }

    @POST
    @Path("logout")
    public AuthResult logout(@FormParam("username") String username) {

        AuthResult authResult = new AuthResult();

        User u = DataSourcesBundle.getDefaultDataSource().getUsers()
                .get(username);
        if (u == null) {
            return authResult.setAuth(false);
        }

        ClientSessionManager.getInstance().destroySession(u);
        return authResult.setAuth(false);

    }
}
