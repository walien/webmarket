package fr.webmarket.backend.model;

import java.util.UUID;

public class ItemTag {

	private UUID id;

	private String name;

	private ItemTag parent;

	public ItemTag(String name) {
		this(UUID.randomUUID(), name, null);
	}

	public ItemTag(UUID id, String name, ItemTag parent) {
		super();
		this.id = id;
		this.name = name;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ItemTag [name=" + name + "]";
	}

}
