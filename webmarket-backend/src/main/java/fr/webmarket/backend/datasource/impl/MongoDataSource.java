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

import com.google.common.collect.ImmutableMap;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import fr.webmarket.backend.datasource.DataSource;
import fr.webmarket.backend.datasource.EntitySequenceProvider;
import fr.webmarket.backend.log.LoggerBundle;
import fr.webmarket.backend.model.*;
import fr.webmarket.backend.utils.DigestUtils;
import fr.webmarket.backend.utils.MarshallingUtils;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class MongoDataSource implements DataSource {

    public static final String DBNAME = "webmarket";

    private MongoClient mongo;
    private DB db;
    private Jongo jongo;

    private MongoCollection users;
    private MongoCollection items;
    private MongoCollection tags;
    private MongoCollection orders;

    public MongoDataSource() {
        init();
    }

    private void init() {

        // Init connection
        try {
            this.mongo = new MongoClient();
            this.db = mongo.getDB(DBNAME);
            this.jongo = new Jongo(db);
        } catch (UnknownHostException e) {
            LoggerBundle.getDefaultLogger().error("Unable to connect the mongo database : {}", e.getMessage());
        }

        // Retrieve all collections
        this.users = jongo.getCollection("users");
        this.items = jongo.getCollection("items");
        this.tags = jongo.getCollection("tags");
        this.orders = jongo.getCollection("orders");

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

        // Print the content of the data store
        LoggerBundle.getDefaultLogger().info("Data store " + this.getClass().getSimpleName() + " : " + dumpData());
    }

    @Override
    public String dumpData() {
        return null;
    }

    @Override
    public EntitySequenceProvider getEntitySequenceProvider() {
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
        return items.save(item) != null;
    }

    @Override
    public boolean removeItem(String id) {
        return false;
    }

    @Override
    public boolean updateItem(String id, Item item) {
        return false;
    }

    @Override
    public ImmutableMap<String, ItemTag> getItemTags() {
        return null;
    }

    @Override
    public ItemTag getItemTag(String id) {
        return null;
    }

    @Override
    public boolean addItemTag(ItemTag tag) {
        return tags.save(tag) != null;
    }

    @Override
    public boolean removeItemTag(String id) {
        return false;
    }

    @Override
    public boolean updateItemTag(String id, ItemTag tag) {
        return false;
    }

    @Override
    public ImmutableMap<String, User> getUsers() {
        return null;
    }

    @Override
    public User getUser(String username) {
        return users.findOne("{username: #}", username).as(User.class);
    }

    @Override
    public boolean addUser(User u) {
        // Hash the pwd before adding it into the datastore
        u.setPwd(DigestUtils.computeMD5(u.getPwd()));
        return users.save(u) != null;
    }

    @Override
    public boolean updateUser(String username, User user) {
        return false;
    }

    @Override
    public boolean removeUser(String username) {
        return false;
    }

    @Override
    public ImmutableMap<String, Order> getOrders() {
        return null;
    }

    @Override
    public Order getOrder(String id) {
        return null;
    }

    @Override
    public boolean addOrder(Order order) {
        return orders.save(order) != null;
    }

    @Override
    public boolean updateOrder(String id, Order order) {
        return false;
    }

    @Override
    public boolean removeOrder(String id) {
        return false;
    }


}
