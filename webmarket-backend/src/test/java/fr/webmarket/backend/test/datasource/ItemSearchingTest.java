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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import fr.webmarket.backend.datasource.DataSource;
import fr.webmarket.backend.datasource.DataSourcesBundle;
import fr.webmarket.backend.features.search.ISearchCriterion;
import fr.webmarket.backend.features.search.ItemSearchEngine;
import fr.webmarket.backend.features.search.SearchAccuracy;
import fr.webmarket.backend.features.search.SearchStrategy;
import fr.webmarket.backend.features.search.criteria.*;
import fr.webmarket.backend.model.Item;
import fr.webmarket.backend.model.ItemTag;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ItemSearchingTest {

    ItemSearchEngine engine;
    private DataSource data;
    private Item item1;
    private Item item6;
    private Item item2;
    private Item item3;
    private Item item4;
    private Item item5;
    private ItemTag electroMenager;
    private ItemTag aspirateur;
    private ItemTag laveVaisselle;
    private ItemTag lecteurSalon;
    private ItemTag hifi;
    private ItemTag baladeur;

    @Before
    public void init() {

        data = DataSourcesBundle.getDefaultDataSource();

        engine = new ItemSearchEngine();

        electroMenager = new ItemTag("Electroménager");
        aspirateur = new ItemTag("Aspirateur");
        laveVaisselle = new ItemTag("Lave-Vaisselle");
        lecteurSalon = new ItemTag("Lecteur Salon");
        hifi = new ItemTag("HiFi");
        baladeur = new ItemTag("Baladeur");

        item1 = new Item("Lave-Vaisselle PV123", "Miele", "", 250);
        item1.getTags().add(electroMenager);
        item1.getTags().add(laveVaisselle);

        item2 = new Item("Lave-Vaisselle", "Wirpool", "", 190);
        item2.getTags().add(electroMenager);
        item2.getTags().add(laveVaisselle);

        item3 = new Item("iPod", "Apple", "Pouet", 340);
        item3.getTags().add(hifi);
        item3.getTags().add(baladeur);

        item4 = new Item("Lecteur Blu-Ray", "Samsung", "", 190);
        item4.getTags().add(hifi);
        item4.getTags().add(lecteurSalon);

        item5 = new Item("Aspirateur SupraPlus", "Dyson", "", 450);
        item5.getTags().add(aspirateur);
        item5.getTags().add(electroMenager);

        item6 = new Item("Lave-Vaisselle PV567", "Miele", "", 280);
        item6.getTags().add(electroMenager);
        item6.getTags().add(laveVaisselle);
    }

    @Test
    public void testSimpleSearching() {

        // Load data
        data.addItem(item1);
        data.addItem(item2);

        // Load all criteria
        List<ISearchCriterion> criteria = Lists.newArrayList();
        criteria.add(new ItemPriceCriterion(190, 300));
        criteria.add(new ItemTagCriterion(Sets.newHashSet(electroMenager,
                laveVaisselle), SearchStrategy.ALL_CRITERIA));

        // Launch searching
        List<Item> result = engine.searchFor(data.getItems(), criteria,
                SearchStrategy.ALL_CRITERIA);

        // Check result
        assertTrue(result.contains(item1));
        assertTrue(result.contains(item2));
    }

    @Test
    public void testUncompatibleCriteriaSearching() {

        // Load data
        data.addItem(item1);
        data.addItem(item2);

        // Load all criteria
        List<ISearchCriterion> criteria = Lists.newArrayList();
        criteria.add(new ItemPriceCriterion(190, 300));
        criteria.add(new ItemTagCriterion(Sets.newHashSet(electroMenager,
                laveVaisselle, hifi), SearchStrategy.ALL_CRITERIA));

        // Launch searching
        List<Item> result = engine.searchFor(data.getItems(), criteria,
                SearchStrategy.ALL_CRITERIA);

        // Check result
        assertTrue(result.size() == 0);
    }

    @Test
    public void testPriceAndTagsCriteriaSearching() {

        // Load data
        data.addItem(item1);
        data.addItem(item2);
        data.addItem(item3);
        data.addItem(item4);
        data.addItem(item5);
        data.addItem(item6);

        // Load all criteria
        List<ISearchCriterion> criteria = Lists.newArrayList();
        criteria.add(new ItemPriceCriterion(190, 300));
        criteria.add(new ItemTagCriterion(Sets.newHashSet(electroMenager,
                laveVaisselle, hifi), SearchStrategy.ONE_OF_CRITERION));

        // Launch searching
        List<Item> result = engine.searchFor(data.getItems(), criteria,
                SearchStrategy.ALL_CRITERIA);

        // Check result
        assertTrue(result.contains(item1));
        assertTrue(result.contains(item2));
        assertFalse(result.contains(item3));
        assertTrue(result.contains(item4));
        assertFalse(result.contains(item5));
        assertTrue(result.contains(item6));
    }

    @Test
    public void testBrandCriteriaSearching() {

        // Load data
        data.addItem(item1);
        data.addItem(item2);
        data.addItem(item3);
        data.addItem(item4);
        data.addItem(item5);
        data.addItem(item6);

        // Load all criteria
        List<ISearchCriterion> criteria = Lists.newArrayList();
        criteria.add(new ItemPriceCriterion(190, 500));
        criteria.add(new ItemTagCriterion(Sets.newHashSet(electroMenager,
                laveVaisselle, hifi), SearchStrategy.ONE_OF_CRITERION));
        criteria.add(new ItemBrandCriterion("Apple", SearchAccuracy.FLEXIBLE));

        // Launch searching
        List<Item> result = engine.searchFor(data.getItems(), criteria,
                SearchStrategy.ALL_CRITERIA);

        // Check result
        assertFalse(result.contains(item1));
        assertFalse(result.contains(item2));
        assertTrue(result.contains(item3));
        assertFalse(result.contains(item4));
        assertFalse(result.contains(item5));
        assertFalse(result.contains(item6));
    }

    @Test
    public void testDescriptionCriteriaSearching() {

        // Load data
        data.addItem(item1);
        data.addItem(item2);
        data.addItem(item3);
        data.addItem(item4);
        data.addItem(item5);
        data.addItem(item6);

        // Load all criteria
        List<ISearchCriterion> criteria = Lists.newArrayList();
        criteria.add(new ItemPriceCriterion(190, 500));
        criteria.add(new ItemTagCriterion(Sets.newHashSet(electroMenager,
                laveVaisselle, hifi), SearchStrategy.ONE_OF_CRITERION));
        criteria.add(new ItemDescriptionCriterion("Pouet",
                SearchAccuracy.FLEXIBLE));

        // Launch searching
        List<Item> result = engine.searchFor(data.getItems(), criteria,
                SearchStrategy.ALL_CRITERIA);

        // Check result
        assertFalse(result.contains(item1));
        assertFalse(result.contains(item2));
        assertTrue(result.contains(item3));
        assertFalse(result.contains(item4));
        assertFalse(result.contains(item5));
        assertFalse(result.contains(item6));
    }

    @Test
    public void testNameCriteriaSearching() {

        // Load data
        data.addItem(item1);
        data.addItem(item2);
        data.addItem(item3);
        data.addItem(item4);
        data.addItem(item5);
        data.addItem(item6);

        // Load all criteria
        List<ISearchCriterion> criteria = Lists.newArrayList();
        criteria.add(new ItemPriceCriterion(190, 500));
        criteria.add(new ItemTagCriterion(Sets.newHashSet(electroMenager,
                laveVaisselle, hifi), SearchStrategy.ONE_OF_CRITERION));
        criteria.add(new ItemNameCriterion("Aspirateur SupraPlus",
                SearchAccuracy.FLEXIBLE));

        // Launch searching
        List<Item> result = engine.searchFor(data.getItems(), criteria,
                SearchStrategy.ALL_CRITERIA);

        // Check result
        assertFalse(result.contains(item1));
        assertFalse(result.contains(item2));
        assertFalse(result.contains(item3));
        assertFalse(result.contains(item4));
        assertTrue(result.contains(item5));
        assertFalse(result.contains(item6));
    }
}
