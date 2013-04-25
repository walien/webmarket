package fr.webmarket.backend.rest;

/**
 * User: walien
 * Date: 17/04/13
 * Time: 00:55
 */

import fr.webmarket.backend.datasource.DataSourcesBundle;
import fr.webmarket.backend.model.ResponseWrapper;
import fr.webmarket.backend.model.User;
import fr.webmarket.backend.rest.auth.AuthUtils;
import fr.webmarket.backend.rest.auth.ClientSessionManager;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/users")
public class UsersREST {

    @GET
    public List<User> getAllUsers() {
        return new ArrayList<User>(DataSourcesBundle.getDefaultDataSource().getUsers().values());
    }

    @POST
    public ResponseWrapper addUser(@QueryParam("sessionID") String sessionID, User user) {

        UUID id = AuthUtils.parseSessionID(sessionID);
        if (id == null
                || !ClientSessionManager.getInstance().checkSession(id)) {
            return new ResponseWrapper().setStatus(false);
        }

        return new ResponseWrapper().setStatus(DataSourcesBundle.getDefaultDataSource().addUser(user));
    }

    @PUT
    public ResponseWrapper updateUser(@QueryParam("sessionID") String sessionID, User user) {

        UUID id = AuthUtils.parseSessionID(sessionID);
        if (id == null
                || !ClientSessionManager.getInstance().checkSession(id)) {
            return new ResponseWrapper().setStatus(false);
        }

        return new ResponseWrapper().setStatus(DataSourcesBundle.getDefaultDataSource().
                updateUser(user.getUsername(), user));
    }

    @DELETE
    public ResponseWrapper deleteUser(@QueryParam("sessionID") String sessionID, String username) {

        UUID id = AuthUtils.parseSessionID(sessionID);
        if (id == null
                || !ClientSessionManager.getInstance().checkSession(id)) {
            return new ResponseWrapper().setStatus(false);
        }

        return new ResponseWrapper().setStatus(DataSourcesBundle.getDefaultDataSource().removeUser(username));

    }

}
