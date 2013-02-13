package fr.webmarket.backend.model;

import java.util.Map;

import com.google.common.collect.Maps;

public class ItemsCatalog {

	private Map<Integer, Item> products;

	public ItemsCatalog() {
		products = Maps.newHashMap();
	}

	public Map<Integer, Item> getItems() {
		return products;
	}

	public void addItem(Item p) {
		products.put(p.getId(), p);
	}

}
