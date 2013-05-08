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

package fr.webmarket.backend.model;

import com.google.common.collect.Sets;
import fr.webmarket.backend.datasource.EntitySequences;

import java.util.Set;

public class Item {

    private int id;
    private String name;
    private String brand;
    private String description;
    private double price;
    private Set<ItemTag> tags;
    private String base64Image;

    public Item() {
        this("", "", "", 0);
    }

    public Item(String name, String brand, String description, double price) {
        this(EntitySequences.getNewItemId(), name, brand, description, price);
    }

    public Item(int id, String name, String brand, String description,
                double price) {
        super();
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.tags = Sets.newHashSet();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<ItemTag> getTags() {
        return tags;
    }

    public void setTags(Set<ItemTag> tags) {
        this.tags = tags;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    @Override
    public String toString() {
        return "Item [id=" + id + ", name=" + name + ", brand=" + brand
                + ", description=" + description + ", price=" + price
                + ", tags=" + tags + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Item other = (Item) obj;
        if (id != other.id)
            return false;
        return true;
    }

}
