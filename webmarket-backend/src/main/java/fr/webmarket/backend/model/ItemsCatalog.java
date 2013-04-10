package fr.webmarket.backend.model;

import java.util.Map;

import com.google.common.collect.Maps;

public class ItemsCatalog {

	private Map<Integer, Item> items;

	public ItemsCatalog() {
		items = Maps.newHashMap();
	}

	public Map<Integer, Item> getItems() {
		return items;
	}

	public void setItems(Map<Integer, Item> items) {
		this.items = items;
	}

	public void addItem(Item p) {
		items.put(p.getId(), p);
	}

}
