package fr.webmarket.backend.test.rest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Ignore;
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

	private String doGet(String path) throws ClientProtocolException,
			IOException {

		String result = null;

		// HTTP client
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(path);
		HttpResponse response = httpclient.execute(httpGet);

		try {

			// Read the content of the response
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			result = new String(ByteStreams.toByteArray(content));

			// Consume the content and close the stream
			EntityUtils.consume(entity);
			content.close();
		} finally {
			httpGet.releaseConnection();
		}

		return result;
	}

	@Test
	public void testGetAllItemsREST() throws ClientProtocolException,
			IOException {

		// Retrieve the content doing an HTTP request to the REST API
		String content = doGet(ALL_ITEMS_REST_URL);

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
			String content = doGet(ITEMS_BASE + "/" + item.getId());

			// Unserialize the JSON content
			Item restItem = new ObjectMapper().readValue(content, Item.class);

			// Check the number of items
			assertEquals(item, restItem);
		}
	}

	// //////////////////////////
	// 2. POST
	// //////////////////////////

	private String doPost(String path, String content)
			throws ClientProtocolException, IOException {

		String result = null;

		// HTTP client
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(path);
		httpPost.addHeader("Content-Type", "application/json");

		// Push the content
		httpPost.setEntity(new StringEntity(content));

		// Execute the request
		HttpResponse response = httpclient.execute(httpPost);

		try {

			// Read the content of the response
			HttpEntity entity = response.getEntity();
			InputStream contentResult = entity.getContent();
			result = new String(ByteStreams.toByteArray(contentResult));

			// Consume the content and close the stream
			EntityUtils.consume(entity);
			contentResult.close();
		} finally {
			httpPost.releaseConnection();
		}

		return result;
	}

	@Test
	public void testAddItemREST() throws ClientProtocolException, IOException {

		// The items catalog
		Map<Integer, Item> itemCatalog = DataSourcesBundle
				.getDefaultDataSource().getItemsCatalog().getItems();

		// Iterate over the items catalog
		for (Item item : itemCatalog.values()) {

			// Generate a JSON format of an item
			String itemJSON = new ObjectMapper().writeValueAsString(item);

			// Do POST request to the server
			String response = doPost(ITEMS_BASE, itemJSON);

			// Check the response
			assertEquals(Boolean.toString(true), response);
		}
	}

	// //////////////////////////
	// 3. DELETE
	// //////////////////////////

	private String doDelete(String path) throws ClientProtocolException,
			IOException {

		String result = null;

		// HTTP client
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpDelete httpDelete = new HttpDelete(path);

		// Execute the request
		HttpResponse response = httpclient.execute(httpDelete);

		try {

			// Read the content of the response
			HttpEntity entity = response.getEntity();
			InputStream contentResult = entity.getContent();
			result = new String(ByteStreams.toByteArray(contentResult));

			// Consume the content and close the stream
			EntityUtils.consume(entity);
			contentResult.close();
		} finally {
			httpDelete.releaseConnection();
		}

		return result;
	}

	@Test
	@Ignore
	public void testDeleteItemREST() throws ClientProtocolException,
			IOException {

		// The items catalog
		Map<Integer, Item> itemCatalog = new HashMap<Integer, Item>(
				DataSourcesBundle.getDefaultDataSource().getItemsCatalog()
						.getItems());

		// Iterate over the items catalog
		for (Item item : itemCatalog.values()) {

			// Execute delete request
			String result = doDelete(ITEMS_BASE + "/" + item.getId());

			// Check response
			assertEquals(result, Boolean.toString(true));
		}

		DataSourcesBundle.getDefaultDataSource().getItemsCatalog()
				.setItems(itemCatalog);
	}
}
