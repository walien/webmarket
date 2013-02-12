package fr.webmarket.backend.test.model;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.webmarket.backend.model.ItemTag;

public class ItemTagTest {

	private static final UUID ITEM_TAG_ID = UUID.randomUUID();
	private static final String ITEM_TAG_NAME = "Electroménager";
	private static final String ITEM_TAG_STRING_FORM = "ItemTag [name=Electroménager]";

	private ItemTag tag;

	@Before
	public void initTest() {
		tag = new ItemTag(ITEM_TAG_ID, ITEM_TAG_NAME, null);
	}

	@Test
	public void testItemTag() {
		assertNotNull(tag);
	}

	@Test
	public void testGetId() {
		assertEquals(tag.getId(), ITEM_TAG_ID);
	}

	@Test
	public void testSetId() {
		UUID newId = UUID.randomUUID();
		tag.setId(newId);
		assertEquals(newId, tag.getId());
	}

	@Test
	public void testGetName() {
		assertEquals(tag.getName(), ITEM_TAG_NAME);
	}

	@Test
	public void testSetName() {
		String newName = "Multimedia";
		tag.setName(newName);
		assertEquals(newName, tag.getName());
	}

	@Test
	public void testGetAndSetParent() {
		ItemTag parent = new ItemTag("Appareils");
		tag.setParent(parent);
		assertEquals(parent, tag.getParent());
	}

	@Test
	public void testToString() {
		assertEquals(tag.toString(), ITEM_TAG_STRING_FORM);
	}

}
