package fr.webmarket.backend.rest;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String getItem(@PathParam("id") int id)
			throws JsonGenerationException, JsonMappingException, IOException {
		return JSONMarshaller.getDefaultMapper().writeValueAsString(
				DataSourcesBundle.getDefaultDataSource().getItemsCatalog()
						.getItems().get(id));
	}

	// ///////////////////////////////
	// //// POST METHODS
	// ///////////////////////////////

	@POST
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String addItem(String itemJson) throws JsonParseException,
			JsonMappingException, IOException {

		Item item = JSONMarshaller.getDefaultMapper().readValue(itemJson,
				Item.class);
		DataSourcesBundle.getDefaultDataSource().getItemsCatalog()
				.addItem(item);

		return Boolean.toString(true);
	}

	// ///////////////////////////////
	// //// DELETE METHODS
	// ///////////////////////////////

	@DELETE
	@Path("{id}")
	public String removeItem(@PathParam("id") int id)
			throws JsonParseException, JsonMappingException, IOException {

		boolean result = (DataSourcesBundle.getDefaultDataSource()
				.getItemsCatalog().getItems().remove(id) != null);
		return Boolean.toString(result);
	}
}
