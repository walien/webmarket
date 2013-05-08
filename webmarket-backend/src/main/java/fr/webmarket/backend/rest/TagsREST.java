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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/tags")
public class TagsREST {

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<ItemTag> getAllTags() throws IOException {
        return new ArrayList<ItemTag>(DataSourcesBundle.getDefaultDataSource().getItemTags().values());
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public ItemTag getTag(@PathParam("id") int id) throws IOException {
        return DataSourcesBundle.getDefaultDataSource().getItemTag(id);
    }
}
