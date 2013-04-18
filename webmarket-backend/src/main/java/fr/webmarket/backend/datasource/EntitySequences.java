package fr.webmarket.backend.datasource;

public class EntitySequences {

	private static int ITEM_ENTITY_SEQUENCE = 100777;

	private static int TAG_ENTITY_SEQUENCE = 0;

	public static int getNewItemId() {
		return ++ITEM_ENTITY_SEQUENCE;
	}

	public static int getNewTagId() {
		return ++TAG_ENTITY_SEQUENCE;
	}
}
