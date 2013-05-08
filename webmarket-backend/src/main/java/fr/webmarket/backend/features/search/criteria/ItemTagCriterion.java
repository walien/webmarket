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

package fr.webmarket.backend.features.search.criteria;

import java.util.Set;

import com.google.common.collect.Sets;

import fr.webmarket.backend.features.search.ISearchCriterion;
import fr.webmarket.backend.features.search.SearchStrategy;
import fr.webmarket.backend.model.Item;
import fr.webmarket.backend.model.ItemTag;

public class ItemTagCriterion implements ISearchCriterion {

	private Set<ItemTag> tags;

	private SearchStrategy strategy;

	public ItemTagCriterion(Set<ItemTag> tags, SearchStrategy strategy) {
		super();
		this.tags = tags;
		this.strategy = strategy;
	}

	@Override
	public boolean apply(Item item) {

		switch (strategy) {
		case ALL_CRITERIA:
			return item.getTags().containsAll(tags);
		case ONE_OF_CRITERION:
			return Sets.intersection(item.getTags(), tags).size() != 0;
		case NONE:
			return true;
		}

		return false;
	}
}
