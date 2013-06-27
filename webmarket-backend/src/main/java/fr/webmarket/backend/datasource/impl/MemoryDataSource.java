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

package fr.webmarket.backend.datasource.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import fr.webmarket.backend.datasource.DataSource;
import fr.webmarket.backend.datasource.EntitySequenceProvider;
import fr.webmarket.backend.model.Coupon;
import fr.webmarket.backend.log.LoggerBundle;
import fr.webmarket.backend.utils.MarshallingUtils;
import fr.webmarket.backend.model.*;
import fr.webmarket.backend.utils.DigestUtils;

import java.io.IOException;
import java.util.*;

public class MemoryDataSource implements DataSource {

    private final Map<String, User> users;
    private final Map<String, ItemTag> tags;
    private final Map<String, Item> items;
    private final Map<String, Order> orders;
    private final EntitySequenceProvider sequences;

    public MemoryDataSource(EntitySequenceProvider sequenceProvider) {
        this.tags = Maps.newHashMap();
        this.items = Maps.newHashMap();
        this.users = Maps.newHashMap();
        this.orders = Maps.newHashMap();
        this.sequences = sequenceProvider;
        initMockData();
    }

    @Override
    public EntitySequenceProvider getEntitySequenceProvider() {
        return this.sequences;
    }

    private void initMockData() {

        // Initialize users
        addUser(new User("eoriou", "pass", "Elian", "ORIOU", "eoriou@gmail.com", UserRole.ADMIN));
        addUser(new User("jdoe", "pass", "John", "DOE", "jdoe@jdoe.com", UserRole.CUSTOMER));

        // Initialize default image
        String imagePath = "webmarket-backend/src/main/resources/default-image.png";
        String base64Image = null;
        try {
            base64Image = "data:image/png;base64," + MarshallingUtils.transformImageToBase64(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize mock objects
        String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vitae ultrices quam. Sed eu ante at ipsum ultrices volutpat vel in nibh. Nullam at ipsum eu massa ultrices euismod. Proin ligula dolor, tincidunt eu viverra id, ultricies sit amet risus. Sed et eros vel ligula fermentum aliquam et at lorem.";

        // 1. Tags
        ItemTag electroMenager = new ItemTag("Electroménager");
        ItemTag aspirateur = new ItemTag("Aspirateur");
        ItemTag laveVaisselle = new ItemTag("Lave-Vaisselle");
        ItemTag lecteurSalon = new ItemTag("Lecteur Salon");
        ItemTag hifi = new ItemTag("HiFi");
        ItemTag tv = new ItemTag("TV");
        ItemTag baladeur = new ItemTag("Baladeur");

        addItemTag(electroMenager);
        addItemTag(aspirateur);
        addItemTag(laveVaisselle);
        addItemTag(lecteurSalon);
        addItemTag(hifi);
        addItemTag(tv);
        addItemTag(baladeur);

        // 2. Items
        Item item1 = new Item("Lave-Vaisselle PV123", "Miele", description, 250);
        item1.getTags().add(electroMenager);
        item1.getTags().add(laveVaisselle);
        item1.setBase64Image(base64Image);

        Item item2 = new Item("Lave-Vaisselle", "Whirpool", description, 190);
        item2.getTags().add(electroMenager);
        item2.getTags().add(laveVaisselle);
        item2.setBase64Image(base64Image);

        Item item3 = new Item("iPod", "Apple", description, 340);
        item3.getTags().add(hifi);
        item3.getTags().add(baladeur);
        item3.setBase64Image(base64Image);

        Item item4 = new Item("Lecteur Blu-Ray", "Samsung", description, 190);
        item4.getTags().add(hifi);
        item4.getTags().add(lecteurSalon);
        item4.setBase64Image(base64Image);

        Item item5 = new Item("Aspirateur SupraPlus", "Dyson", description, 450);
        item5.getTags().add(aspirateur);
        item5.getTags().add(electroMenager);
        item5.setBase64Image(base64Image);

        Item item6 = new Item("Lave-Vaisselle PV567", "Miele", description
                + "pouet", 280);
        item6.getTags().add(electroMenager);
        item6.getTags().add(laveVaisselle);
        item6.setBase64Image(base64Image);

        Item item7 = new Item("TV LCD-LED", "Samsung", description, 670);
        item7.getTags().add(hifi);
        item7.getTags().add(tv);
        item7.setBase64Image(base64Image);

        Item item8 = new Item("TV Plasma SXH-819", "Sony", description, 670);
        item8.getTags().add(hifi);
        item8.getTags().add(tv);
        item8.setBase64Image(base64Image);

        Item item9 = new Item("TV Plasma SRF-876", "LG", description, 670);
        item9.getTags().add(hifi);
        item9.getTags().add(tv);
        item9.setBase64Image(base64Image);

        Item item10 = new Item("TV LCD-LED YTG-789", "Loewe", description, 670);
        item10.getTags().add(hifi);
        item10.getTags().add(tv);
        item10.setBase64Image(base64Image);

        addItem(item1);
        addItem(item2);
        addItem(item3);
        addItem(item4);
        addItem(item5);
        addItem(item6);
        addItem(item7);
        addItem(item8);
        addItem(item9);
        addItem(item10);

        // 3. Mock Orders
        Order order1 = new Order(new Date(), users.get("eoriou"), Collections.EMPTY_LIST);
        addOrder(order1);

        // Print the content of the data store
        LoggerBundle.getDefaultLogger().info("Data store " + this.getClass().getSimpleName() + " : " + dumpData());
    }

    @Override
    public ImmutableMap<String, Item> getItems() {
        return ImmutableMap.<String, Item>builder().putAll(this.items).build();
    }

    @Override
    public Item getItem(String id) {
        return items.get(id);
    }

    @Override
    public boolean addItem(Item item) {
        item.setId(sequences.getNewTagId());
        items.put(item.getId(), item);
        LoggerBundle.getDefaultLogger().debug("The item {} was added to the data source.", item.toString());
        return true;
    }

    @Override
    public boolean removeItem(String id) {
        LoggerBundle.getDefaultLogger().debug("Removing the item {} from the data source.", id);
        return items.remove(id) != null;
    }

    @Override
    public boolean updateItem(String id, Item newItem) {
        LoggerBundle.getDefaultLogger().debug("Updating the item {} (new: {}).", id, newItem);
        Item item = items.get(id);
        if (item == null || newItem.getId() != id) {
            return false;
        }
        // Update tags with server-side references (keep the link between item and its
        // associated tags)
        Set<ItemTag> tags = new HashSet<ItemTag>();
        for (ItemTag tag : newItem.getTags()) {
            ItemTag srvTag = this.tags.get(tag.getId());
            if (srvTag == null) {
                continue;
            }
            tags.add(srvTag);
        }
        newItem.getTags().clear();
        newItem.getTags().addAll(tags);
        return items.put(id, newItem) != null;
    }

    @Override
    public ImmutableMap<String, ItemTag> getItemTags() {
        return ImmutableMap.<String, ItemTag>builder().putAll(this.tags).build();
    }

    @Override
    public ItemTag getItemTag(String id) {
        return tags.get(id);
    }

    @Override
    public boolean addItemTag(ItemTag tag) {
        tag.setId(sequences.getNewTagId());
        tags.put(tag.getId(), tag);
        LoggerBundle.getDefaultLogger().debug("The tag {} was added to the data source.", tag);
        return true;
    }

    @Override
    public boolean removeItemTag(String id) {
        LoggerBundle.getDefaultLogger().debug("Removing the tag {} from the data source.", id);
        ItemTag tag = tags.remove(id);
        if (tag == null) {
            return false;
        }
        // Remove the tag in very item referencing it
        for (Item item : items.values()) {
            item.getTags().remove(tag);
        }
        return true;
    }

    @Override
    public boolean updateItemTag(String id, ItemTag newTag) {
        LoggerBundle.getDefaultLogger().debug("Updating the tag {} (new: {}).", id, newTag);
        // Full update on the object
        ItemTag old = tags.get(id);
        if (old == null || newTag.getId() == id) {
            return false;
        }
        old.setName(newTag.getName());
        old.setParent(newTag.getParent());
        return true;
    }

    @Override
    public ImmutableMap<String, User> getUsers() {
        return ImmutableMap.<String, User>builder().putAll(this.users).build();
    }

    @Override
    public User getUser(String username) {
        return users.get(username);
    }

    @Override
    public boolean addUser(User u) {
        // Hash the pwd before adding it into the datastore
        u.setPwd(DigestUtils.computeMD5(u.getPwd()));
        // Persist the user
        users.put(u.getUsername(), u);
        LoggerBundle.getDefaultLogger().debug("The user {} was added to the data source.", u);
        return true;
    }

    @Override
    public boolean updateUser(String username, User user) {
        LoggerBundle.getDefaultLogger().debug("Updating the user {} (new: {}).", username, user);

        User dsUser = users.get(username);
        if (dsUser == null) {
            return false;
        }

        // Unchanged pwd
        if (user.getPwd().equals("") || user.getPwd() == null) {
            user.setPwd(dsUser.getPwd());
        } else if (dsUser.getPwd().equals(DigestUtils.computeMD5(user.getPwd()))) {
            user.setPwd(dsUser.getPwd());
        }
        // Changed pwd
        else {
            user.setPwd(DigestUtils.computeMD5(user.getPwd()));
        }

        return users.put(user.getUsername(), user) != null;
    }

    @Override
    public boolean removeUser(String username) {
        LoggerBundle.getDefaultLogger().debug("Removing the user {} from the data source.", username);
        return users.remove(username) != null;
    }

    @Override
    public ImmutableMap<String, Order> getOrders() {
        return ImmutableMap.<String, Order>builder().putAll(this.orders).build();
    }

    @Override
    public Order getOrder(String id) {
        return this.orders.get(id);
    }

    @Override
    public boolean addOrder(Order order) {
        order.setId(sequences.getNewOrderId());
        this.orders.put(order.getId(), order);
        LoggerBundle.getDefaultLogger().debug("The order {} was added to the datasource.", order);
        return true;
    }

    @Override
    public boolean updateOrder(String id, Order order) {
        LoggerBundle.getDefaultLogger().debug("Updating the order {} (new: {}).", id, order);
        return orders.put(id, order) != null;
    }

    @Override
    public boolean removeOrder(String id) {
        LoggerBundle.getDefaultLogger().debug("Removing the order {} from the data source.", id);
        return orders.remove(id) != null;
    }

    @Override
    public ImmutableMap<String, Coupon> getCoupons() {
        return null;
    }

    @Override
    public Coupon getCouponByKey(String key) {
        return null;
    }

    @Override
    public Coupon getCouponById(String id) {
        return null;
    }

    @Override
    public boolean addCoupon(Coupon coupon) {
        return false;
    }

    @Override
    public boolean updateCoupon(String id, Coupon coupon) {
        return false;
    }

    @Override
    public boolean removeCoupon(String id) {
        return false;
    }

    @Override
    public String dumpData() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n* Users : \n  - ");
        builder.append(Joiner.on("\n  - ").join(users.values()));
        builder.append("\n* Items : \n  - ");
        builder.append(Joiner.on("\n  - ").join(items.values()));
        builder.append("\n* Tags : \n  - ");
        builder.append(Joiner.on("\n  - ").join(tags.values()));
        builder.append("\n* Orders : \n  - ");
        builder.append(Joiner.on("\n  - ").join(orders.values()));
        return builder.toString();
    }
}
