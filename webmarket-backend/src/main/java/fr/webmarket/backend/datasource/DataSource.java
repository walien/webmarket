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

import com.google.common.collect.ImmutableMap;
import fr.webmarket.backend.features.commercial.Coupon;
import fr.webmarket.backend.model.Item;
import fr.webmarket.backend.model.ItemTag;
import fr.webmarket.backend.model.Order;
import fr.webmarket.backend.model.User;

public interface DataSource {

    String dumpData();

    EntitySequenceProvider getEntitySequenceProvider();

    // ///////////////////////////
    // ITEMS
    // ///////////////////////////

    ImmutableMap<String, Item> getItems();

    Item getItem(String id);

    boolean addItem(Item item);

    boolean removeItem(String id);

    boolean updateItem(String id, Item item);

    // ///////////////////////////
    // TAGS
    // ///////////////////////////

    ImmutableMap<String, ItemTag> getItemTags();

    ItemTag getItemTag(String id);

    boolean addItemTag(ItemTag tag);

    boolean removeItemTag(String id);

    boolean updateItemTag(String id, ItemTag tag);

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

    ImmutableMap<String, Order> getOrders();

    Order getOrder(String id);

    boolean addOrder(Order order);

    boolean updateOrder(String id, Order order);

    boolean removeOrder(String id);

    // ///////////////////////////
    // COUPONS
    // ///////////////////////////

    ImmutableMap<String, Coupon> getCoupons();

    Coupon getCouponByKey(String key);

    Coupon getCouponById(String id);

    boolean addCoupon(Coupon coupon);

    boolean updateCoupon(String id, Coupon coupon);

    boolean removeCoupon(String id);

}
