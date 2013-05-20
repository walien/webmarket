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
import fr.webmarket.backend.model.ItemTag;
import fr.webmarket.backend.model.ResponseWrapper;
import fr.webmarket.backend.rest.auth.AuthUtils;
import fr.webmarket.backend.rest.auth.ClientSessionManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/tags")
public class TagsREST {

    @GET
    public List<ItemTag> getAllTags() throws IOException {
        return new ArrayList<ItemTag>(DataSourcesBundle.getDefaultDataSource().getItemTags().values());
    }

    @GET
    @Path("{id}")
    public ItemTag getTag(@PathParam("id") int id) throws IOException {
        return DataSourcesBundle.getDefaultDataSource().getItemTag(id);
    }

    @POST
    public ResponseWrapper addTag(@QueryParam("sessionID") String sessionID,
                                  ItemTag tag) throws IOException {

        UUID id = AuthUtils.parseSessionID(sessionID);
        if (id == null
                || !ClientSessionManager.getInstance().checkSession(id)) {
            return new ResponseWrapper().setStatus(false);
        }

        return new ResponseWrapper().setStatus(DataSourcesBundle.
                getDefaultDataSource().addItemTag(tag));
    }

    @POST
    @Path("{id}")
    public ResponseWrapper updateTag(@PathParam("id") int tagID,
                                     @QueryParam("sessionID") String sessionID,
                                     ItemTag tag) throws IOException {

        UUID id = AuthUtils.parseSessionID(sessionID);
        if (id == null
                || !ClientSessionManager.getInstance().checkSession(id)) {
            return new ResponseWrapper().setStatus(false);
        }

        return new ResponseWrapper().setStatus(DataSourcesBundle.
                getDefaultDataSource().updateItemTag(tagID, tag));
    }

    @DELETE
    @Path("{id}")
    public ResponseWrapper removeItemTag(@PathParam("id") int tagID,
                                         @QueryParam("sessionID") String sessionID) throws IOException {

        UUID id = AuthUtils.parseSessionID(sessionID);
        if (id == null
                || !ClientSessionManager.getInstance().checkSession(id)) {
            return new ResponseWrapper().setStatus(false);
        }

        return new ResponseWrapper().setStatus(DataSourcesBundle.
                getDefaultDataSource().removeItemTag(tagID));
    }
}
