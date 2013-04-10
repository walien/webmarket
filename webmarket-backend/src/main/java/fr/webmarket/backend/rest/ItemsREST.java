package fr.webmarket.backend.rest;

import fr.webmarket.backend.datasource.DataSourcesBundle;
import fr.webmarket.backend.marshalling.JSONMarshaller;
import fr.webmarket.backend.model.Item;
import fr.webmarket.backend.rest.auth.AuthUtils;
import fr.webmarket.backend.rest.auth.ClientSessionManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.UUID;

@Path("/items")
public class ItemsREST {

    // ///////////////////////////////
    // //// GET METHODS
    // ///////////////////////////////

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getAllItems() throws IOException {
        return JSONMarshaller.getDefaultMapper().writeValueAsString(
                DataSourcesBundle.getDefaultDataSource().getItemsCatalog()
                        .getItems().values());
    }

    @GET
    @Path("{item-id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getItem(@PathParam("item-id") int itemID) throws IOException {
        return JSONMarshaller.getDefaultMapper().writeValueAsString(
                DataSourcesBundle.getDefaultDataSource().getItemsCatalog()
                        .getItems().get(itemID));
    }

    // ///////////////////////////////////////////
    // //// POST METHODS (needs authentication)
    // ///////////////////////////////////////////

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String addItem(@HeaderParam("Session-ID") String sessionID,
                          String itemJson) throws IOException {

        UUID id = AuthUtils.parseSessionID(sessionID);

        if (id == null
                || ClientSessionManager.getInstance().checkSession(id) == false) {
            return Boolean.toString(false);
        }

        Item item = JSONMarshaller.getDefaultMapper().readValue(itemJson,
                Item.class);
        DataSourcesBundle.getDefaultDataSource().getItemsCatalog()
                .addItem(item);

        return Boolean.toString(true);
    }

    // /////////////////////////////////////////////
    // //// DELETE METHODS (needs authentication)
    // /////////////////////////////////////////////

    @DELETE
    @Path("{item-id}")
    public String removeItem(@HeaderParam("Session-ID") String sessionID,
                             @PathParam("item-id") int itemID) throws IOException {

        UUID id = AuthUtils.parseSessionID(sessionID);

        if (id == null
                || ClientSessionManager.getInstance().checkSession(id) == false) {
            return Boolean.toString(false);
        }

        boolean result = (DataSourcesBundle.getDefaultDataSource()
                .getItemsCatalog().getItems().remove(itemID) != null);
        return Boolean.toString(result);
    }

}
