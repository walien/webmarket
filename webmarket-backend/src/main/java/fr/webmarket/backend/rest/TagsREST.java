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

import fr.webmarket.backend.auth.ClientSessionManager;
import fr.webmarket.backend.datasource.DataSourcesBundle;
import fr.webmarket.backend.model.ItemTag;
import fr.webmarket.backend.model.ResponseWrapper;
import fr.webmarket.backend.model.UserRole;
import fr.webmarket.backend.utils.DigestUtils;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/tags")
public class TagsREST {

    @GET
    public List<ItemTag> getAllTags() throws IOException {
        return new ArrayList<ItemTag>(DataSourcesBundle.getDataSource().getItemTags().values());
    }

    @GET
    @Path("{id}")
    public ItemTag getTag(@PathParam("id") String id) throws IOException {
        return DataSourcesBundle.getDataSource().getItemTag(id);
    }

    @POST
    public ResponseWrapper addTag(@QueryParam("sessionID") String sessionID,
                                  ItemTag tag) throws IOException {

        if (!ClientSessionManager.getInstance().checkSessionAndRights(DigestUtils.parseSessionID(sessionID),
                UserRole.ADMIN)) {
            return new ResponseWrapper().setStatus(false);
        }
        return new ResponseWrapper().setStatus(DataSourcesBundle.
                getDataSource().addItemTag(tag));
    }

    @POST
    @Path("{id}")
    public ResponseWrapper updateTag(@PathParam("id") String tagID,
                                     @QueryParam("sessionID") String sessionID,
                                     ItemTag tag) throws IOException {

        if (!ClientSessionManager.getInstance().checkSessionAndRights(DigestUtils.parseSessionID(sessionID),
                UserRole.ADMIN)) {
            return new ResponseWrapper().setStatus(false);
        }
        return new ResponseWrapper().setStatus(DataSourcesBundle.
                getDataSource().updateItemTag(tagID, tag));
    }

    @DELETE
    @Path("{id}")
    public ResponseWrapper removeItemTag(@PathParam("id") String tagID,
                                         @QueryParam("sessionID") String sessionID) throws IOException {

        if (!ClientSessionManager.getInstance().checkSessionAndRights(DigestUtils.parseSessionID(sessionID),
                UserRole.ADMIN)) {
            return new ResponseWrapper().setStatus(false);
        }
        return new ResponseWrapper().setStatus(DataSourcesBundle.
                getDataSource().removeItemTag(tagID));
    }
}
