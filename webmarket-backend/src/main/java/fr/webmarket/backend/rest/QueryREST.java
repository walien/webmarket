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

package fr.webmarket.backend.rest;

import com.google.common.collect.Lists;
import fr.webmarket.backend.datasource.DataSourcesBundle;
import fr.webmarket.backend.features.search.ISearchCriterion;
import fr.webmarket.backend.features.search.ItemSearchEngine;
import fr.webmarket.backend.features.search.SearchAccuracy;
import fr.webmarket.backend.features.search.SearchStrategy;
import fr.webmarket.backend.features.search.criteria.ItemBrandCriterion;
import fr.webmarket.backend.features.search.criteria.ItemDescriptionCriterion;
import fr.webmarket.backend.features.search.criteria.ItemNameCriterion;
import fr.webmarket.backend.model.Item;
import fr.webmarket.backend.model.ItemTag;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Path("/query")
public class QueryREST {

    private static final String ALL_SEARCH_STRATEGY = "all";
    private static final String ONE_OF_SEARCH_STRATEGY = "oneOf";
    private static final ItemSearchEngine engine = new ItemSearchEngine();

    @GET
    @Path("/by-data/{search-strategy}")
    public List<Item> queryItemsByData(
            @PathParam("search-strategy") String searchStrategy,
            @Context UriInfo uri) throws IOException {

        // Get parameters
        MultivaluedMap<String, String> params = uri.getQueryParameters();

        // Create criteria
        List<ISearchCriterion> criteria = Lists.newArrayList();
        if (params.containsKey("name")) {
            ISearchCriterion nameCriterion = new ItemNameCriterion(params.get(
                    "name").get(0), SearchAccuracy.FLEXIBLE);
            criteria.add(nameCriterion);
        }
        if (params.containsKey("brand")) {
            ISearchCriterion brandCriterion = new ItemBrandCriterion(params
                    .get("brand").get(0), SearchAccuracy.FLEXIBLE);
            criteria.add(brandCriterion);
        }
        if (params.containsKey("description")) {
            ISearchCriterion descriptionCriterion = new ItemDescriptionCriterion(
                    params.get("description").get(0), SearchAccuracy.FLEXIBLE);
            criteria.add(descriptionCriterion);
        }

        // Launch research
        SearchStrategy strategy = SearchStrategy.NONE;
        if (ALL_SEARCH_STRATEGY.equalsIgnoreCase(searchStrategy)) {
            strategy = SearchStrategy.ALL_CRITERIA;
        }
        if (ONE_OF_SEARCH_STRATEGY.equalsIgnoreCase(searchStrategy)) {
            strategy = SearchStrategy.ONE_OF_CRITERION;
        }

        return engine.searchFor(DataSourcesBundle
                .getDefaultDataSource().getItems(), criteria, strategy);
    }

    @GET
    @Path("by-tags")
    public List<Item> queryItemsByTags(@Context UriInfo uri) throws IOException {

        // Get parameters
        MultivaluedMap<String, String> params = uri.getQueryParameters();

        // Retrieve tags list
        if (!params.containsKey("tags")) {
            return Collections.EMPTY_LIST;
        }

        // The item tag catalog
        Map<Integer, ItemTag> tags = DataSourcesBundle.getDefaultDataSource()
                .getItemTags();

        // The item list
        Collection<Item> items = DataSourcesBundle.getDefaultDataSource()
                .getItems().values();

        // The result list
        List<Item> result = Lists.newArrayList();

        // Iterate over tags id list
        String[] tagsIdList = params.get("tags").get(0).split("\\s");
        for (String tagId : tagsIdList) {

            // Retrieve the tag object
            ItemTag tag = tags.get(Integer.parseInt(tagId));
            if (tag == null) {
                continue;
            }

            // Check if the tag is linked to an item
            for (Item item : items) {
                if (!item.getTags().contains(tag)) {
                    continue;
                }
                result.add(item);
            }
        }

        return result;
    }
}
