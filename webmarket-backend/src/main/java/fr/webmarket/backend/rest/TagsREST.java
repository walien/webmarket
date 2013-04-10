package fr.webmarket.backend.rest;

import fr.webmarket.backend.datasource.DataSourcesBundle;
import fr.webmarket.backend.marshalling.JSONMarshaller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/tags")
public class TagsREST {

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getAllTags() throws IOException {
        return JSONMarshaller.getDefaultMapper().writeValueAsString(
                DataSourcesBundle.getDefaultDataSource().getTagsCatalog()
                        .getTags().values());
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getAllTags(@PathParam("id") int id) throws IOException {
        return JSONMarshaller.getDefaultMapper().writeValueAsString(
                DataSourcesBundle.getDefaultDataSource().getTagsCatalog()
                        .getTags().get(id));
    }
}
