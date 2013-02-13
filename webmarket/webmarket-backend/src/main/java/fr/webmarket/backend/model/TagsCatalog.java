package fr.webmarket.backend.model;

import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;

public class TagsCatalog {

	private Map<UUID, ItemTag> tags;

	public TagsCatalog() {
		tags = Maps.newHashMap();
	}

	public Map<UUID, ItemTag> getTags() {
		return tags;
	}

	public void addTag(ItemTag tag) {
		tags.put(tag.getId(), tag);
	}
}
