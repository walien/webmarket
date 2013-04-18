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
