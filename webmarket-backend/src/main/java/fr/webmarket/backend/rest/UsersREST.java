package fr.webmarket.backend.rest;

/**
 * User: walien
 * Date: 17/04/13
 * Time: 00:55
 */

import fr.webmarket.backend.datasource.DataSourcesBundle;
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
    public boolean addUser(User user) {
        return DataSourcesBundle.getDefaultDataSource().addUser(user);
    }

    @PUT
    public boolean updateUser(User user) {
        return DataSourcesBundle.getDefaultDataSource().updateUser(user.getUsername(), user);
    }

    @DELETE
    public boolean deleteUser(String username) {
        return DataSourcesBundle.getDefaultDataSource().removeUser(username);

    }

}
