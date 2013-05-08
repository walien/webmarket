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

import fr.webmarket.backend.datasource.DataSourcesBundle;
import fr.webmarket.backend.model.Item;
import fr.webmarket.backend.model.ResponseWrapper;
import fr.webmarket.backend.rest.auth.AuthUtils;
import fr.webmarket.backend.rest.auth.ClientSessionManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                .getItems().get(itemID);
    }

    // ///////////////////////////////////////////
    // //// POST METHODS (needs authentication)
    // ///////////////////////////////////////////

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public ResponseWrapper addItem(@QueryParam("sessionID") String sessionID,
                                   Item item) throws IOException {

        UUID id = AuthUtils.parseSessionID(sessionID);
        if (id == null
                || !ClientSessionManager.getInstance().checkSession(id)) {
            return new ResponseWrapper().setStatus(false);
        }

        // Add the item
        boolean result = DataSourcesBundle.getDefaultDataSource().addItem(item);

        return new ResponseWrapper().setStatus(result);
    }

    @POST
    @Path("{item-id}")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public ResponseWrapper updateItem(@QueryParam("sessionID") String sessionID,
                                      @PathParam("item-id") int itemID, Item item) throws IOException {

        UUID id = AuthUtils.parseSessionID(sessionID);
        if (id == null
                || !ClientSessionManager.getInstance().checkSession(id)) {
            return new ResponseWrapper().setStatus(false);
        }

        // Add the item
        boolean result = DataSourcesBundle.getDefaultDataSource().updateItem(itemID, item);

        return new ResponseWrapper().setStatus(result);
    }

    // /////////////////////////////////////////////
    // //// DELETE METHODS (needs authentication)
    // /////////////////////////////////////////////

    @DELETE
    @Path("{item-id}")
    public ResponseWrapper removeItem(String sessionID,
                                      @PathParam("item-id") int itemID) throws IOException {

        UUID id = AuthUtils.parseSessionID(sessionID);
        if (id == null
                || !ClientSessionManager.getInstance().checkSession(id)) {
            return new ResponseWrapper().setStatus(false);
        }

        // Remove the item
        boolean result = DataSourcesBundle.getDefaultDataSource()
                .removeItem(itemID);

        return new ResponseWrapper().setStatus(result);
    }

}
