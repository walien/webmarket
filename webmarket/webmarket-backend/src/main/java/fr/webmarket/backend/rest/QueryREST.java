package fr.webmarket.backend.rest;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.google.common.collect.Lists;

import fr.webmarket.backend.datasource.MemoryDataSource;
import fr.webmarket.backend.features.search.ISearchCriterion;
import fr.webmarket.backend.features.search.ItemSearchEngine;
import fr.webmarket.backend.features.search.SearchAccuracy;
import fr.webmarket.backend.features.search.SearchStrategy;
import fr.webmarket.backend.features.search.criteria.ItemBrandCriterion;
import fr.webmarket.backend.features.search.criteria.ItemNameCriterion;
import fr.webmarket.backend.marshalling.JSONMarshaller;
import fr.webmarket.backend.model.Item;

@Path("/query")
public class QueryREST {

	private static final ItemSearchEngine engine = new ItemSearchEngine();

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String queryItems(@Context UriInfo uri)
			throws JsonGenerationException, JsonMappingException, IOException {

		// Get parameters
		MultivaluedMap<String, String> params = uri.getQueryParameters();

		// Create criteria
		List<ISearchCriterion> criteria = Lists.newArrayList();
		if (params.containsKey("name")) {
			ISearchCriterion nameCriterion = new ItemNameCriterion(params.get(
					"name").get(0), SearchAccuracy.FLEXIBLE);
			criteria.add(nameCriterion);
		}
		if (params.containsKey("brand")) {
			ISearchCriterion brandCriterion = new ItemBrandCriterion(params
					.get("brand").get(0), SearchAccuracy.FLEXIBLE);
			criteria.add(brandCriterion);
		}

		// Launch research
		List<Item> result = engine.searchFor(MemoryDataSource.getInstance()
				.getCatalog(), criteria, SearchStrategy.ONE_OF_CRITERION);

		return JSONMarshaller.getDefaultMapper().writeValueAsString(result);
	}
}
