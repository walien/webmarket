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
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import fr.webmarket.backend.datasource.DataSource;
import fr.webmarket.backend.datasource.EntitySequenceProvider;
import fr.webmarket.backend.model.Coupon;
import fr.webmarket.backend.model.CouponType;
import fr.webmarket.backend.log.LoggerBundle;
import fr.webmarket.backend.model.*;
import fr.webmarket.backend.utils.DigestUtils;
import fr.webmarket.backend.utils.MarshallingUtils;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MongoDataSource implements DataSource {

    public static final String DBNAME = "webmarket";
    public static final String USERS_COLLECTION_NAME = "users";
    public static final String ITEMS_COLLECTION_NAME = "items";
    public static final String TAGS_COLLECTION_NAME = "tags";
    public static final String ORDERS_COLLECTION_NAME = "orders";
    public static final String COUPONS_COLLECTION_NAME = "coupons";

    private DB db;
    private Jongo jongo;

    private MongoCollection users;
    private MongoCollection items;
    private MongoCollection tags;
    private MongoCollection orders;
    private MongoCollection coupons;

    public MongoDataSource() {
        init();
    }

    private void init() {

        // Init connection
        try {
            this.db = new MongoClient().getDB(DBNAME);
            this.jongo = new Jongo(db);
        } catch (UnknownHostException e) {
            LoggerBundle.getDefaultLogger().error("Unable to connect the mongo database : {}", e.getMessage());
        }

        // Retrieve all collections
        this.users = jongo.getCollection(USERS_COLLECTION_NAME);
        this.items = jongo.getCollection(ITEMS_COLLECTION_NAME);
        this.tags = jongo.getCollection(TAGS_COLLECTION_NAME);
        this.orders = jongo.getCollection(ORDERS_COLLECTION_NAME);
        this.coupons = jongo.getCollection(COUPONS_COLLECTION_NAME);

        // Init mock data
        initMockData();
    }

    private void initMockData() {

        // Drop existing data
        db.dropDatabase();

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

        // 3. Coupons
        addCoupon(new Coupon("VAISSELLE", CouponType.AMOUNT, 20, Collections.EMPTY_LIST));
        addCoupon(new Coupon("HIFI", CouponType.PERCENTAGE, 25, Collections.EMPTY_LIST));
        addCoupon(new Coupon("IPOD", CouponType.PERCENTAGE, 10, Arrays.asList(item3, item9)));

        // Print the content of the data store
        LoggerBundle.getDefaultLogger().info("Data store " + this.getClass().getSimpleName() + " : " + dumpData());
    }

    @Override
    public String dumpData() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n* Users : \n\t - ").append(Joiner.on("\n\t - ").join(this.users.find().as(User.class)));
        builder.append("\n* Tags : \n\t - ").append(Joiner.on("\n\t - ").join(this.tags.find().as(ItemTag.class)));
        builder.append("\n* Items : \n\t - ").append(Joiner.on("\n\t - ").join(this.items.find().as(Item.class)));
        builder.append("\n* Orders : \n\t - ").append(Joiner.on("\n\t - ").join(this.orders.find().as(Order.class)));
        builder.append("\n* Coupons : \n\t - ").append(Joiner.on("\n\t - ").join(this.coupons.find().as(Coupon.class)));
        return builder.toString();
    }

    @Override
    public EntitySequenceProvider getEntitySequenceProvider() {
        // Id's are managed by MongoDB itself
        return null;
    }

    @Override
    public ImmutableMap<String, Item> getItems() {
        Iterable<Item> mongoItems = this.items.find().as(Item.class);
        Map<String, Item> itemsMap = new HashMap<String, Item>();
        for (Item item : mongoItems) {
            itemsMap.put(item.getId(), item);
        }
        return ImmutableMap.<String, Item>builder().putAll(itemsMap).build();
    }

    @Override
    public Item getItem(String id) {
        return items.findOne(new ObjectId(id)).as(Item.class);
    }

    @Override
    public boolean addItem(Item item) {
        WriteResult result = items.save(item);
        if (result.getError() == null) {
            LoggerBundle.getDefaultLogger().debug("Item '{}' registered !", item.getId());
            return true;
        } else {
            LoggerBundle.getDefaultLogger().debug("Error while registering the item '{}' !", item.getId());
            return false;
        }
    }

    @Override
    public boolean removeItem(String id) {
        WriteResult result = items.remove(new ObjectId(id));
        if (result.getError() == null) {
            LoggerBundle.getDefaultLogger().debug("Item '{}' removed !", id);
            return true;
        } else {
            LoggerBundle.getDefaultLogger().debug("Error while removing the item '{}' !", id);
            return false;
        }
    }

    @Override
    public boolean updateItem(String id, Item item) {
        WriteResult result = items.update(new ObjectId(id)).merge(item);
        if (result.getError() == null) {
            LoggerBundle.getDefaultLogger().debug("Item '{}' updated !", id);
            return true;
        } else {
            LoggerBundle.getDefaultLogger().debug("Error while updating the item '{}' !", id);
            return false;
        }
    }

    @Override
    public ImmutableMap<String, ItemTag> getItemTags() {
        Iterable<ItemTag> mongoTags = tags.find().as(ItemTag.class);
        Map<String, ItemTag> tagsMap = new HashMap<String, ItemTag>();
        for (ItemTag tag : mongoTags) {
            tagsMap.put(tag.getId(), tag);
        }
        return ImmutableMap.<String, ItemTag>builder().putAll(tagsMap).build();
    }

    @Override
    public ItemTag getItemTag(String id) {
        return tags.findOne(new ObjectId(id)).as(ItemTag.class);
    }

    @Override
    public boolean addItemTag(ItemTag tag) {
        WriteResult result = tags.save(tag);
        if (result.getError() == null) {
            LoggerBundle.getDefaultLogger().debug("Tag '{}' registered !", tag.getId());
            return true;
        } else {
            LoggerBundle.getDefaultLogger().debug("Error while registering the tag '{}' !", tag.getId());
            return false;
        }
    }

    @Override
    public boolean removeItemTag(String id) {
        WriteResult result = tags.remove(new ObjectId(id));
        if (result.getError() == null) {
            LoggerBundle.getDefaultLogger().debug("Tag '{}' removed !", id);
            return true;
        } else {
            LoggerBundle.getDefaultLogger().debug("Error while removing the tag '{}' !", id);
            return false;
        }
    }

    @Override
    public boolean updateItemTag(String id, ItemTag tag) {
        WriteResult result = tags.update(new ObjectId(id)).merge(tag);
        if (result.getError() == null) {
            LoggerBundle.getDefaultLogger().debug("Tag '{}' updated !", id);
            return true;
        } else {
            LoggerBundle.getDefaultLogger().debug("Error while updating the tag '{}' !", id);
            return false;
        }
    }

    @Override
    public ImmutableMap<String, User> getUsers() {
        Iterable<User> mongoUsers = users.find().as(User.class);
        Map<String, User> usersMap = new HashMap<String, User>();
        for (User user : mongoUsers) {
            usersMap.put(user.getUsername(), user);
        }
        return ImmutableMap.<String, User>builder().putAll(usersMap).build();
    }

    @Override
    public User getUser(String username) {
        return users.findOne("{username: #}", username).as(User.class);
    }

    @Override
    public boolean addUser(User u) {
        // Hash the pwd before adding it into the datastore
        u.setPwd(DigestUtils.computeMD5(u.getPwd()));
        WriteResult result = users.save(u);
        if (result.getError() == null) {
            LoggerBundle.getDefaultLogger().debug("User '{}' registered !", u.getUsername());
            return true;
        } else {
            LoggerBundle.getDefaultLogger().debug("Error while registering the user '{}' !", u.getUsername());
            return false;
        }
    }

    @Override
    public boolean updateUser(String username, User user) {
        WriteResult result = users.update("{username: #}", username).merge(user);
        if (result.getError() == null) {
            LoggerBundle.getDefaultLogger().debug("User '{}' updated !", username);
            return true;
        } else {
            LoggerBundle.getDefaultLogger().debug("Error while updating the user '{}' !", username);
            return false;
        }
    }

    @Override
    public boolean removeUser(String username) {
        WriteResult result = users.remove("{username: #}", username);
        if (result.getError() == null) {
            LoggerBundle.getDefaultLogger().debug("User '{}' removed !", username);
            return true;
        } else {
            LoggerBundle.getDefaultLogger().debug("Error while removing the user '{}' !", username);
            return false;
        }
    }

    @Override
    public ImmutableMap<String, Order> getOrders() {
        Iterable<Order> mongoOrders = orders.find().as(Order.class);
        Map<String, Order> ordersMap = new HashMap<String, Order>();
        for (Order order : mongoOrders) {
            ordersMap.put(order.getId(), order);
        }
        return ImmutableMap.<String, Order>builder().putAll(ordersMap).build();
    }

    @Override
    public Order getOrder(String id) {
        return orders.findOne(new ObjectId(id)).as(Order.class);
    }

    @Override
    public boolean addOrder(Order order) {
        WriteResult result = orders.save(order);
        if (result.getError() == null) {
            LoggerBundle.getDefaultLogger().debug("Order '{}' registered !", order.getId());
            return true;
        } else {
            LoggerBundle.getDefaultLogger().debug("Error while registering the order '{}' !", order.getId());
            return false;
        }
    }

    @Override
    public boolean updateOrder(String id, Order order) {
        WriteResult result = orders.update(new ObjectId(id)).merge(order);
        if (result.getError() == null) {
            LoggerBundle.getDefaultLogger().debug("Order '{}' updated !", id);
            return true;
        } else {
            LoggerBundle.getDefaultLogger().debug("Error while updating the order '{}' !", id);
            return false;
        }
    }

    @Override
    public boolean removeOrder(String id) {
        WriteResult result = orders.remove(new ObjectId(id));
        if (result.getError() == null) {
            LoggerBundle.getDefaultLogger().debug("Order '{}' removed !", id);
            return true;
        } else {
            LoggerBundle.getDefaultLogger().debug("Error while removing the order '{}' !", id);
            return false;
        }
    }

    @Override
    public ImmutableMap<String, Coupon> getCoupons() {
        Iterable<Coupon> mongoCoupons = coupons.find().as(Coupon.class);
        Map<String, Coupon> couponsMap = new HashMap<String, Coupon>();
        for (Coupon coupon : mongoCoupons) {
            couponsMap.put(coupon.getId(), coupon);
        }
        return ImmutableMap.<String, Coupon>builder().putAll(couponsMap).build();
    }

    @Override
    public Coupon getCouponByKey(String key) {
        return coupons.findOne("{key: #}", key).as(Coupon.class);
    }

    @Override
    public Coupon getCouponById(String id) {
        return coupons.findOne(new ObjectId(id)).as(Coupon.class);
    }

    @Override
    public boolean addCoupon(Coupon coupon) {
        WriteResult result = coupons.save(coupon);
        if (result.getError() == null) {
            LoggerBundle.getDefaultLogger().debug("Coupon '{}' registered!", coupon.getId());
            return true;
        } else {
            LoggerBundle.getDefaultLogger().debug("Error while registering the coupon '{}' !", coupon.getId());
            return false;
        }
    }

    @Override
    public boolean updateCoupon(String id, Coupon coupon) {
        WriteResult result = coupons.update(new ObjectId(id)).merge(coupon);
        if (result.getError() == null) {
            LoggerBundle.getDefaultLogger().debug("Coupon '{}' updated!", coupon.getId());
            return true;
        } else {
            LoggerBundle.getDefaultLogger().debug("Error while updating the coupon '{}' !", coupon.getId());
            return false;
        }
    }

    @Override
    public boolean removeCoupon(String id) {
        WriteResult result = coupons.remove(new ObjectId(id));
        if (result.getError() == null) {
            LoggerBundle.getDefaultLogger().debug("Coupon '{}' updated!", id);
            return true;
        } else {
            LoggerBundle.getDefaultLogger().debug("Error while removing the coupon '{}' !", id);
            return false;
        }
    }
}
