package fr.webmarket.backend.test.model;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.webmarket.backend.model.Item;
import fr.webmarket.backend.model.ItemCatalog;

public class ItemCatalogTest {

	private ItemCatalog catalog;

	@Before
	public void initTest() {
		catalog = new ItemCatalog();
	}

	@Test
	public void testItemCatalog() {
		assertNotNull(catalog);
	}

	@Test
	public void testAddAndGetItem() {
		UUID id = UUID.randomUUID();
		Item p = new Item(id, "iPhone", "Apple", "iPhone 5 - 2012", 840.0);
		catalog.addItem(p);
		assertNotNull(catalog.getItems().get(id));
	}
}
