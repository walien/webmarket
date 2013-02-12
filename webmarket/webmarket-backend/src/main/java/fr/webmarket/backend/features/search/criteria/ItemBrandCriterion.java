package fr.webmarket.backend.features.search.criteria;

import fr.webmarket.backend.features.search.ISearchCriterion;
import fr.webmarket.backend.features.search.SearchAccuracy;
import fr.webmarket.backend.model.Item;

public class ItemBrandCriterion implements ISearchCriterion {

	private String brand;

	private SearchAccuracy accuracy;

	public ItemBrandCriterion(String brand, SearchAccuracy accuracy) {
		super();
		this.brand = brand;
		this.accuracy = accuracy;
	}

	@Override
	public boolean apply(Item item) {

		switch (accuracy) {
		case FLEXIBLE:
			return this.brand.contains(item.getBrand());
		case STRICT:
			return this.brand.equalsIgnoreCase(item.getBrand());
		}

		return false;
	}

}
