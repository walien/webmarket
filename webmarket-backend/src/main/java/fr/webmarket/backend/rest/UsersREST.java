package fr.webmarket.backend.rest;

/**
 * User: walien
 * Date: 17/04/13
 * Time: 00:55
 */

import fr.webmarket.backend.datasource.DataSourcesBundle;
import fr.webmarket.backend.model.ResponseWrapper;
import fr.webmarket.backend.model.User;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

@Path("/users")
public class UsersREST {

    @GET
    public List<User> getAllUsers() {
        return new ArrayList<User>(DataSourcesBundle.getDefaultDataSource().getUsers().values());
    }

    @POST
    public ResponseWrapper addUser(User user) {
        return new ResponseWrapper().setStatus(DataSourcesBundle.getDefaultDataSource().addUser(user));
    }

    @PUT
    public ResponseWrapper updateUser(User user) {
        return new ResponseWrapper().setStatus(DataSourcesBundle.getDefaultDataSource().
                updateUser(user.getUsername(), user));
    }

    @DELETE
    public ResponseWrapper deleteUser(String username) {
        return new ResponseWrapper().setStatus(DataSourcesBundle.getDefaultDataSource().removeUser(username));

    }

}
