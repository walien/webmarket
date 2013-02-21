package fr.webmarket.backend.datasource;

import java.util.Map;

import fr.webmarket.backend.model.ItemsCatalog;
import fr.webmarket.backend.model.TagsCatalog;
import fr.webmarket.backend.model.User;

public interface DataSource {

	// ///////////////////////////
	// ITEMS
	// ///////////////////////////

	ItemsCatalog getItemsCatalog();

	// ///////////////////////////
	// TAGS
	// ///////////////////////////

	TagsCatalog getTagsCatalog();

	// ///////////////////////////
	// USERS
	// ///////////////////////////

	Map<String, User> getUsers();

	void addUser(User u);
}
