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
