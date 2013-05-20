/*
 * Copyright 2013 - Elian ORIOU
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.webmarket.backend.datasource;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import fr.webmarket.backend.model.Item;
import fr.webmarket.backend.model.ItemTag;
import fr.webmarket.backend.model.Order;
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

    ImmutableMap<String, User> getUsers();

    User getUser(String username);

    boolean addUser(User u);

    boolean updateUser(String username, User user);

    boolean removeUser(String username);

    // ///////////////////////////
    // ORDERS
    // ///////////////////////////

    ImmutableMap<Integer, Order> getOrders();

    Order getOrder(int id);

    boolean addOrder(Order order);

    boolean updateOrder(int id, Order order);

    boolean removeOrder(int id);

}
