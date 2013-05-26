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
import fr.webmarket.backend.model.ResponseWrapper;
import fr.webmarket.backend.model.User;
import fr.webmarket.backend.model.UserRole;

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
    public ResponseWrapper addUser(@QueryParam("sessionID") String sessionID,
                                   User user) {

        if (!ClientSessionManager.getInstance().checkSessionAndRights(AuthUtils.parseSessionID(sessionID),
                UserRole.ADMIN)) {
            return new ResponseWrapper().setStatus(false);
        }
        return new ResponseWrapper().setStatus(DataSourcesBundle.getDefaultDataSource().addUser(user));
    }

    @PUT
    public ResponseWrapper updateUser(@QueryParam("sessionID") String sessionID,
                                      User user) {

        if (!ClientSessionManager.getInstance().checkSessionAndRights(AuthUtils.parseSessionID(sessionID),
                UserRole.ADMIN)) {
            return new ResponseWrapper().setStatus(false);
        }
        return new ResponseWrapper().setStatus(DataSourcesBundle.getDefaultDataSource().
                updateUser(user.getUsername(), user));
    }

    @DELETE
    public ResponseWrapper deleteUser(@QueryParam("sessionID") String sessionID,
                                      String username) {

        if (!ClientSessionManager.getInstance().checkSessionAndRights(AuthUtils.parseSessionID(sessionID),
                UserRole.ADMIN)) {
            return new ResponseWrapper().setStatus(false);
        }
        return new ResponseWrapper().setStatus(DataSourcesBundle.getDefaultDataSource().removeUser(username));

    }

}
