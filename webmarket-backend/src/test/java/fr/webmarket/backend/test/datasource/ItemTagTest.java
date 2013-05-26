/*
 * Copyright 2013 - Elian ORIOU
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.webmarket.backend.test.datasource;

import fr.webmarket.backend.datasource.impl.MemoryEntitySequenceProvider;
import fr.webmarket.backend.model.ItemTag;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ItemTagTest {

	private static final int ITEM_TAG_ID = new MemoryEntitySequenceProvider().getNewTagId();
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
		int newId = new MemoryEntitySequenceProvider().getNewTagId();
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
