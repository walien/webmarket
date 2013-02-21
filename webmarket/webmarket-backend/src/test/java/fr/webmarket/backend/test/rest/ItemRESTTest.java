package fr.webmarket.backend.test.rest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.google.common.io.ByteStreams;

import fr.webmarket.backend.datasource.DataSourcesBundle;
import fr.webmarket.backend.model.Item;

public class ItemRESTTest {

	private static final String SERVER_BASE = "http://localhost:8080";

	private static final String ITEMS_BASE = SERVER_BASE + "/rest/items";

	private static final String ALL_ITEMS_REST_URL = ITEMS_BASE + "/all";

	// //////////////////////////
	// 1. GET
	// //////////////////////////

	private String doRequest(HttpRequestBase httpBase)
			throws ClientProtocolException, IOException {

		String result = null;

		// HTTP client
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpResponse response = httpclient.execute(httpBase);

		try {

			// Read the content of the response
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			result = new String(ByteStreams.toByteArray(content));

			// Consume the content and close the stream
			EntityUtils.consume(entity);
			content.close();
		} finally {
			httpBase.releaseConnection();
		}

		return result;
	}

	@Test
	public void testGetAllItemsREST() throws ClientProtocolException,
			IOException {

		// Retrieve the content doing an HTTP request to the REST API
		String content = doRequest(new HttpGet(ALL_ITEMS_REST_URL));

		// Unserialize the JSON content
		Item[] items = new ObjectMapper().readValue(content, Item[].class);

		// The items catalog
		Map<Integer, Item> itemCatalog = DataSourcesBundle
				.getDefaultDataSource().getItemsCatalog().getItems();

		// Check the number of items
		assertEquals(items.length, itemCatalog.size());

		// Check the content
		for (Item item : items) {

			// Retrieve the original item into the catalog
			Item originalItem = itemCatalog.get(item.getId());
			assertNotNull(originalItem);

			// And compare it with the retrieved one
			assertEquals(originalItem, item);
		}
	}

	@Test
	public void testGetItemREST() throws ClientProtocolException, IOException {

		// The items catalog
		Map<Integer, Item> itemCatalog = DataSourcesBundle
				.getDefaultDataSource().getItemsCatalog().getItems();

		// Iterate over item catalog and retrieve the same item from the API
		for (Item item : itemCatalog.values()) {

			// Retrieve the content doing an HTTP request to the REST API
			String content = doRequest(new HttpGet(ITEMS_BASE + "/"
					+ +item.getId()));

			// Unserialize the JSON content
			Item restItem = new ObjectMapper().readValue(content, Item.class);

			// Check the number of items
			assertEquals(item, restItem);
		}
	}

	// //////////////////////////
	// 2. POST
	// //////////////////////////

	@Test
	public void testAddItemREST() throws ClientProtocolException, IOException {

		// // The items catalog
		// Map<Integer, Item> itemCatalog = DataSourcesBundle
		// .getDefaultDataSource().getItemsCatalog().getItems();
		//
		// // Retrieve the content doing an HTTP request to the REST API
		// String content = doRequest(new HttpGet(ITEMS_BASE + "/" +
		// +item.getId()));
	}

}
