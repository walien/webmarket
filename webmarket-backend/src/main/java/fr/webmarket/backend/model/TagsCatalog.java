package fr.webmarket.backend.model;

import java.util.Map;

import com.google.common.collect.Maps;

public class TagsCatalog {

	private Map<Integer, ItemTag> tags;

	public TagsCatalog() {
		tags = Maps.newHashMap();
	}

	public Map<Integer, ItemTag> getTags() {
		return tags;
	}

	public void addTag(ItemTag tag) {
		tags.put(tag.getId(), tag);
	}
}
