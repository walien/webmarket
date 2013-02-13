package fr.webmarket.backend.test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.webmarket.backend.datasource.EntitySequences;
import fr.webmarket.backend.model.Item;
import fr.webmarket.backend.model.ItemsCatalog;

public class ItemCatalogTest {

	private ItemsCatalog catalog;

	@Before
	public void initTest() {
		catalog = new ItemsCatalog();
	}

	@Test
	public void testItemCatalog() {
		assertNotNull(catalog);
	}

	@Test
	public void testAddAndGetItem() {
		int id = EntitySequences.getNewItemId();
		Item p = new Item(id, "iPhone", "Apple", "iPhone 5 - 2012", 840.0);
		catalog.addItem(p);
		assertNotNull(catalog.getItems().get(id));
	}
}
