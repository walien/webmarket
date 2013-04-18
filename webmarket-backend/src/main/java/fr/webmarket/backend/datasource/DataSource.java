package fr.webmarket.backend.datasource;

import com.google.common.collect.ImmutableMap;
import fr.webmarket.backend.model.Item;
import fr.webmarket.backend.model.ItemTag;
import fr.webmarket.backend.model.User;

import java.util.Map;

public interface DataSource {

    String dumpData();

    // ///////////////////////////
    // ITEMS
    // ///////////////////////////

    ImmutableMap<Integer, Item> getItems();

    Item getItem(int id);

    boolean addItem(Item item);

    boolean removeItem(int id);

    boolean updateItem(int id, Item item);

    // ///////////////////////////
    // TAGS
    // ///////////////////////////

    ImmutableMap<Integer, ItemTag> getItemTags();

    ItemTag getItemTag(int id);

    boolean addItemTag(ItemTag tag);

    boolean removeItemTag(int id);

    boolean updateItemTag(int id, ItemTag tag);

    // ///////////////////////////
    // USERS
    // ///////////////////////////

    Map<String, User> getUsers();

    User getUser(String username);

    boolean addUser(User u);

    boolean updateUser(String username, User user);

    boolean removeUser(String username);
}
