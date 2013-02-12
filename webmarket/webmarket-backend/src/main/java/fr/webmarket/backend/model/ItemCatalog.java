package fr.webmarket.backend.model;

import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;

public class ItemCatalog {

	private Map<UUID, Item> products;

	public ItemCatalog() {
		products = Maps.newHashMap();
	}

	public Map<UUID, Item> getItems() {
		return products;
	}

	public void addItem(Item p) {
		products.put(p.getId(), p);
	}

}
