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
			return item.getName().contains(name);
		case STRICT:
			return item.getName().equalsIgnoreCase(name);
		}

		return false;
	}

}
