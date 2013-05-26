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

import fr.webmarket.backend.auth.AuthUtils;
import fr.webmarket.backend.auth.ClientSessionManager;
import fr.webmarket.backend.datasource.DataSourcesBundle;
import fr.webmarket.backend.model.Item;
import fr.webmarket.backend.model.ResponseWrapper;
import fr.webmarket.backend.model.UserRole;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/items")
public class ItemsREST {

    // ///////////////////////////////
    // //// GET METHODS
    // ///////////////////////////////

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Item> getAllItems() throws IOException {
        return new ArrayList<Item>(DataSourcesBundle.getDefaultDataSource().getItems().values());
    }

    @GET
    @Path("{item-id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Item getItem(@PathParam("item-id") int itemID) throws IOException {
        return DataSourcesBundle.getDefaultDataSource()
                .getItem(itemID);
    }

    // ///////////////////////////////////////////
    // //// POST METHODS (needs authentication)
    // ///////////////////////////////////////////

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public ResponseWrapper addItem(@QueryParam("sessionID") String sessionID,
                                   Item item) throws IOException {

        if (!ClientSessionManager.getInstance().checkSessionAndRights(AuthUtils.parseSessionID(sessionID),
                UserRole.ADMIN)) {
            return new ResponseWrapper().setStatus(false);
        }
        boolean result = DataSourcesBundle.getDefaultDataSource().addItem(item);

        return new ResponseWrapper().setStatus(result);
    }

    @POST
    @Path("{item-id}")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public ResponseWrapper updateItem(@PathParam("item-id") int itemID,
                                      @QueryParam("sessionID") String sessionID,
                                      Item item) throws IOException {

        if (!ClientSessionManager.getInstance().checkSessionAndRights(AuthUtils.parseSessionID(sessionID),
                UserRole.ADMIN)) {
            return new ResponseWrapper().setStatus(false);
        }
        boolean result = DataSourcesBundle.getDefaultDataSource().updateItem(itemID, item);

        return new ResponseWrapper().setStatus(result);
    }

    // /////////////////////////////////////////////
    // //// DELETE METHODS (needs authentication)
    // /////////////////////////////////////////////

    @DELETE
    @Path("{item-id}")
    public ResponseWrapper removeItem(@PathParam("item-id") int itemID,
                                      @QueryParam("sessionID") String sessionID) throws IOException {

        if (!ClientSessionManager.getInstance().checkSessionAndRights(AuthUtils.parseSessionID(sessionID),
                UserRole.ADMIN)) {
            return new ResponseWrapper().setStatus(false);
        }
        boolean result = DataSourcesBundle.getDefaultDataSource()
                .removeItem(itemID);

        return new ResponseWrapper().setStatus(result);
    }

}
