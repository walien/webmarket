package fr.webmarket.backend.model;

import fr.webmarket.backend.datasource.EntitySequences;

public class ItemTag {

	private int id;

	private String name;

	private ItemTag parent;

	public ItemTag() {

	}

	public ItemTag(String name) {
		this(EntitySequences.getNewTagId(), name, null);
	}

	public ItemTag(int id, String name, ItemTag parent) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ItemTag getParent() {
		return parent;
	}

	public void setParent(ItemTag parent) {
		this.parent = parent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemTag other = (ItemTag) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ItemTag [name=" + name + "]";
	}

}
