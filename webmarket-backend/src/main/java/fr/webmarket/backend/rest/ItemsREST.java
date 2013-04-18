package fr.webmarket.backend.rest;

import fr.webmarket.backend.datasource.DataSourcesBundle;
import fr.webmarket.backend.marshalling.JSONMarshaller;
import fr.webmarket.backend.model.Item;
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
    public boolean addItem(@HeaderParam("Session-ID") String sessionID,
                           String itemJson) throws IOException {

        UUID id = AuthUtils.parseSessionID(sessionID);

        if (id == null
                || !ClientSessionManager.getInstance().checkSession(id)) {
            return false;
        }

        Item item = JSONMarshaller.getDefaultMapper().readValue(itemJson,
                Item.class);

        return DataSourcesBundle.getDefaultDataSource().addItem(item);
    }

    // /////////////////////////////////////////////
    // //// DELETE METHODS (needs authentication)
    // /////////////////////////////////////////////

    @DELETE
    @Path("{item-id}")
    public boolean removeItem(@HeaderParam("Session-ID") String sessionID,
                              @PathParam("item-id") int itemID) throws IOException {

        UUID id = AuthUtils.parseSessionID(sessionID);

        if (id == null
                || !ClientSessionManager.getInstance().checkSession(id)) {
            return false;
        }
        return DataSourcesBundle.getDefaultDataSource()
                .removeItem(itemID);
    }

}
