package fr.webmarket.backend.features.search.criteria;

import fr.webmarket.backend.features.search.ISearchCriterion;
import fr.webmarket.backend.features.search.SearchAccuracy;
import fr.webmarket.backend.model.Item;

public class ItemDescriptionCriterion implements ISearchCriterion {

	private String description;

	private SearchAccuracy accuracy;

	public ItemDescriptionCriterion(String description, SearchAccuracy accuracy) {
		super();
		this.description = description;
		this.accuracy = accuracy;
	}

	@Override
	public boolean apply(Item item) {

		switch (accuracy) {
		case FLEXIBLE:
			return item.getDescription().toLowerCase()
					.contains(description.toLowerCase());
		case STRICT:
			return item.getDescription().equalsIgnoreCase(description);
		}

		return false;
	}

}
