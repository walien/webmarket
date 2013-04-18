package fr.webmarket.backend.features.search;

import com.google.common.collect.Lists;
import fr.webmarket.backend.model.Item;

import java.util.List;
import java.util.Map;

public class ItemSearchEngine {

    public List<Item> searchFor(Map<Integer, Item> catalog,
                                List<ISearchCriterion> criteria, SearchStrategy globalStrategy) {

        List<Item> result = Lists.newArrayList();

        if (globalStrategy == SearchStrategy.ALL_CRITERIA) {
            for (Item item : catalog.values()) {
                applyAllCriteriaSearchStrategy(item, criteria, result);
            }
        } else if (globalStrategy == SearchStrategy.ONE_OF_CRITERION) {
            for (Item item : catalog.values()) {
                applyOneCriterionSearchStrategy(item, criteria, result);
            }
        }

        return result;
    }

    private void applyAllCriteriaSearchStrategy(Item item,
                                                List<ISearchCriterion> criteria, List<Item> result) {
        for (ISearchCriterion criterion : criteria) {
            if (!criterion.apply(item)) {
                return;
            }
        }
        result.add(item);
    }

    private void applyOneCriterionSearchStrategy(Item item,
                                                 List<ISearchCriterion> criteria, List<Item> result) {
        for (ISearchCriterion criterion : criteria) {
            if (criterion.apply(item)) {
                result.add(item);
                return;
            }
        }
    }
}
