package fr.webmarket.backend.rest;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import fr.webmarket.backend.datasource.DataSourcesBundle;
import fr.webmarket.backend.marshalling.JSONMarshaller;
import fr.webmarket.backend.model.Item;
import fr.webmarket.backend.rest.auth.AuthUtils;
import fr.webmarket.backend.rest.auth.ClientSessionManager;

@Path("/items")
public class ItemsREST {

	// ///////////////////////////////
	// //// GET METHODS
	// ///////////////////////////////

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String getAllItems() throws JsonGenerationException,
			JsonMappingException, IOException {
		return JSONMarshaller.getDefaultMapper().writeValueAsString(
				DataSourcesBundle.getDefaultDataSource().getItemsCatalog()
						.getItems().values());
	}

	@GET
	@Path("{item-id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String getItem(@PathParam("item-id") int itemID)
			throws JsonGenerationException, JsonMappingException, IOException {
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
			String itemJson) throws JsonParseException, JsonMappingException,
			IOException {

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
			@PathParam("item-id") int itemID) throws JsonParseException,
			JsonMappingException, IOException {

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
