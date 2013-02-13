package fr.webmarket.backend.features.search;

import java.util.List;

import com.google.common.collect.Lists;

import fr.webmarket.backend.model.Item;
import fr.webmarket.backend.model.ItemsCatalog;

public class ItemSearchEngine {

	public List<Item> searchFor(ItemsCatalog catalog,
			List<ISearchCriterion> criteria, SearchStrategy globalStrategy) {

		List<Item> result = Lists.newArrayList();

		if (globalStrategy == SearchStrategy.ALL_CRITERIA) {
			for (Item item : catalog.getItems().values()) {
				applyAllCriteriaSearchStrategy(item, criteria, result);
			}
		} else if (globalStrategy == SearchStrategy.ONE_OF_CRITERION) {
			for (Item item : catalog.getItems().values()) {
				applyOneCriterionSearchStrategy(item, criteria, result);
			}
		}

		return result;
	}

	private void applyAllCriteriaSearchStrategy(Item item,
			List<ISearchCriterion> criteria, List<Item> result) {
		for (ISearchCriterion criterion : criteria) {
			if (criterion.apply(item) == false) {
				return;
			}
		}
		result.add(item);
	}

	private void applyOneCriterionSearchStrategy(Item item,
			List<ISearchCriterion> criteria, List<Item> result) {
		for (ISearchCriterion criterion : criteria) {
			if (criterion.apply(item) == true) {
				result.add(item);
				return;
			}
		}
	}
}
