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

import fr.webmarket.backend.features.search.ISearchCriterion;
import fr.webmarket.backend.features.search.SearchAccuracy;
import fr.webmarket.backend.model.Item;

public class ItemNameCriterion implements ISearchCriterion {

	private String name;

	private SearchAccuracy accuracy;

	public ItemNameCriterion(String name, SearchAccuracy accuracy) {
		super();
		this.name = name;
		this.accuracy = accuracy;
	}

	@Override
	public boolean apply(Item item) {

		switch (accuracy) {
		case FLEXIBLE:
			return item.getName().toLowerCase().contains(name.toLowerCase());
		case STRICT:
			return item.getName().equalsIgnoreCase(name);
		}

		return false;
	}

}
