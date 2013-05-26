/*
 * Copyright 2013 - Elian ORIOU
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.webmarket.backend.rest;

/**
 * User: walien
 * Date: 17/04/13
 * Time: 00:55
 */

import fr.webmarket.backend.auth.AuthUtils;
import fr.webmarket.backend.auth.ClientSessionManager;
import fr.webmarket.backend.datasource.DataSourcesBundle;
import fr.webmarket.backend.log.LoggerBundle;
import fr.webmarket.backend.model.ResponseWrapper;
import fr.webmarket.backend.model.User;
import fr.webmarket.backend.model.UserRole;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/users")
public class UsersREST {

    @GET
    public List<User> getAllUsers(@QueryParam("sessionID") String sessionID) {

        if (!ClientSessionManager.getInstance().checkSessionAndRights(AuthUtils.parseSessionID(sessionID),
                UserRole.ADMIN)) {
            return new ArrayList<User>();
        }

        return new ArrayList<User>(DataSourcesBundle.getDefaultDataSource().getUsers().values());
    }

    @POST
    public ResponseWrapper addUser(@QueryParam("sessionID") String sessionID,
                                   User user) {

        if (!ClientSessionManager.getInstance().checkSessionAndRights(AuthUtils.parseSessionID(sessionID),
                UserRole.ADMIN)) {
            return new ResponseWrapper().setStatus(false);
        }
        return new ResponseWrapper().setStatus(DataSourcesBundle.getDefaultDataSource().addUser(user));
    }

    @PUT
    @Path("{username}")
    public ResponseWrapper updateUser(@QueryParam("sessionID") String sessionID,
                                      @PathParam("username") String username,
                                      User user) {

        if (!ClientSessionManager.getInstance().checkSessionAndRights(AuthUtils.parseSessionID(sessionID),
                UserRole.ADMIN)) {
            return new ResponseWrapper().setStatus(false);
        }
        return new ResponseWrapper().setStatus(DataSourcesBundle.getDefaultDataSource().
                updateUser(username, user));
    }

    @DELETE
    @Path("{username}")
    public ResponseWrapper deleteUser(@QueryParam("sessionID") String sessionID,
                                      @PathParam("username") String username) {

        UUID id = AuthUtils.parseSessionID(sessionID);
        if (!ClientSessionManager.getInstance().checkSessionAndRights(id, UserRole.ADMIN)) {
            return new ResponseWrapper().setStatus(false);
        }
        // Check if the given user is not the currently logged in user
        User loggedUser = ClientSessionManager.getInstance().getUserFromSession(id);
        User potentiallyRemovedUser = DataSourcesBundle.getDefaultDataSource().getUser(username);
        if (loggedUser.equals(potentiallyRemovedUser)) {
            LoggerBundle.getDefaultLogger().warn("The given user is the currently logged in user ! " +
                    "Removing aborted...");
            return new ResponseWrapper().setStatus(false);
        }

        return new ResponseWrapper().setStatus(DataSourcesBundle.getDefaultDataSource().removeUser(username));

    }

}
