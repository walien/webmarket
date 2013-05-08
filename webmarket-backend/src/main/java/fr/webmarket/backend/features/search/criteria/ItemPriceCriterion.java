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
import fr.webmarket.backend.model.Item;

public class ItemPriceCriterion implements ISearchCriterion {

	private double from;

	private double to;

	public ItemPriceCriterion(double from, double to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public boolean apply(Item item) {
		return item.getPrice() >= from && item.getPrice() <= to;
	}
}
