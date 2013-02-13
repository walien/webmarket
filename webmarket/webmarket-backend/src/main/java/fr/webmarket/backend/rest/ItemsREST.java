package fr.webmarket.backend.rest;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import fr.webmarket.backend.datasource.MemoryDataSource;
import fr.webmarket.backend.marshalling.JSONMarshaller;
import fr.webmarket.backend.model.Item;

@Path("/items")
public class ItemsREST {

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String getAllItems() throws JsonGenerationException,
			JsonMappingException, IOException {
		return new ObjectMapper().writeValueAsString(MemoryDataSource
				.getInstance().getItemsCatalog().getItems().values());
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String getItem(@PathParam("id") String id)
			throws JsonGenerationException, JsonMappingException, IOException {
		UUID uuid = UUID.fromString(id);
		Item item = MemoryDataSource.getInstance().getItemsCatalog().getItems()
				.get(uuid);
		if (item == null) {
			return JSONMarshaller.getDefaultMapper().writeValueAsString(false);
		}
		return JSONMarshaller.getDefaultMapper().writeValueAsString(item);
	}
}
