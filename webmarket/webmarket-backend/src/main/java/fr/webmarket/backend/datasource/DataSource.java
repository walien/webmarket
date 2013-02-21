package fr.webmarket.backend.datasource;

import java.util.Map;

import fr.webmarket.backend.model.Item;
import fr.webmarket.backend.model.ItemTag;
import fr.webmarket.backend.model.ItemsCatalog;
import fr.webmarket.backend.model.TagsCatalog;
import fr.webmarket.backend.model.User;

public interface DataSource {

	// ///////////////////////////
	// ITEMS
	// ///////////////////////////

	ItemsCatalog getItemsCatalog();

	void addItem(Item item);

	// ///////////////////////////
	// TAGS
	// ///////////////////////////

	TagsCatalog getTagsCatalog();

	void addItemTag(ItemTag tag);

	// ///////////////////////////
	// USERS
	// ///////////////////////////

	Map<String, User> getUsers();

	void addUser(User u);
}
