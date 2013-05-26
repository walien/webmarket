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

import fr.webmarket.backend.datasource.EntitySequenceProvider;
import fr.webmarket.backend.datasource.impl.MemoryEntitySequenceProvider;
import fr.webmarket.backend.utils.MarshallingUtils;
import fr.webmarket.backend.model.Item;
import fr.webmarket.backend.model.ItemTag;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ItemTest {

    private static final int ITEM_ID = new MemoryEntitySequenceProvider().getNewItemId();
    private static final double ITEM_PRICE = 450.0;
    private static final String ITEM_DESCRIPTION = "Apple iPod Nano - 2012";
    private static final String ITEM_NAME = "iPod Nano";
    private static final String ITEM_BRAND = "Apple";
    private static final String ITEM_TEXT_FORM = "Item [id="
            + ITEM_ID
            + ", name=iPod Nano, brand=Apple, description=Apple iPod Nano - 2012, price=450.0, tags=[]]";

    private Item item;

    @Before
    public void initTest() {
        item = new Item(ITEM_ID, ITEM_NAME, ITEM_BRAND, ITEM_DESCRIPTION,
                ITEM_PRICE);
    }

    @Test
    public void testItem() {
        assertNotNull(item);
    }

    @Test
    public void testGetId() {
        assertEquals(ITEM_ID, item.getId());
    }

    @Test
    public void testSetId() {
        int newID = new MemoryEntitySequenceProvider().getNewItemId();
        item.setId(newID);
        assertEquals(newID, item.getId());
    }

    @Test
    public void testGetName() {
        assertEquals(ITEM_NAME, item.getName());
    }

    @Test
    public void testSetName() {
        String newName = "iPhone 5";
        item.setName(newName);
        assertEquals(newName, item.getName());
    }

    @Test
    public void testGetBrand() {
        assertEquals(ITEM_BRAND, item.getBrand());
    }

    @Test
    public void testSetBrand() {
        String newBrand = "Google";
        item.setBrand(newBrand);
        assertEquals(newBrand, item.getBrand());
    }

    @Test
    public void testGetDescription() {
        assertEquals(ITEM_DESCRIPTION, item.getDescription());
    }

    @Test
    public void testSetDescription() {
        String newName = "Apple iPhone 5 - 2012";
        item.setName(newName);
        assertEquals(newName, item.getName());
    }

    @Test
    public void testGetPrice() {
        assertEquals(ITEM_PRICE, item.getPrice(), 0.0);
    }

    @Test
    public void testSetPrice() {
        double newPrice = 546.0;
        item.setPrice(newPrice);
        assertEquals(newPrice, item.getPrice(), 0.0);
    }

    @Test
    public void testSetAndGetImage() throws IOException {

        String imagePath = "src/test/resources/webmarket-logo.png";
        String base64String = MarshallingUtils
                .transformImageToBase64(imagePath);
        System.out.println(base64String);

        item.setBase64Image(base64String);
        assertEquals(item.getBase64Image(), base64String);
    }

    @Test
    public void testAddAndGetTag() {

        EntitySequenceProvider sequence = new MemoryEntitySequenceProvider();

        ItemTag tag = new ItemTag(sequence.getNewTagId(),
                "Electroménager", null);
        ItemTag tag2 = new ItemTag(sequence.getNewTagId(),
                "Lave-Vaisselle", tag);
        ItemTag tag3 = new ItemTag(sequence.getNewTagId(),
                "Lave-Vaisselle", tag);
        ItemTag tag4 = new ItemTag(sequence.getNewTagId(),
                "Fer à repasser", tag);

        item.getTags().add(tag);
        item.getTags().add(tag2);
        item.getTags().add(tag3);

        assertTrue(item.getTags().contains(tag));
        assertTrue(item.getTags().contains(tag2));
        assertTrue(item.getTags().contains(tag3));
        assertFalse(item.getTags().contains(tag4));
    }

    @Test
    public void testToString() {
        System.out.println(item);
        assertEquals(ITEM_TEXT_FORM, item.toString());
    }
}
