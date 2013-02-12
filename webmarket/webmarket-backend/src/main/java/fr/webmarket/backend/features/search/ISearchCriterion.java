package fr.webmarket.backend.features.search;

import fr.webmarket.backend.model.Item;

public interface ISearchCriterion {

	boolean apply(Item item);

}
