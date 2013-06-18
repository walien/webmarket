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

package fr.webmarket.backend.features.commercial;

import fr.webmarket.backend.model.Item;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

import java.util.List;

public class Coupon {

    @Id
    @ObjectId
    private String id;

    private String key;

    private CouponType type;

    private double amount;

    private List<Item> concernedItems;

    public Coupon() {

    }

    public Coupon(String key, CouponType type, double amount, List<Item> concernedItems) {
        this.key = key;
        this.type = type;
        this.amount = amount;
        this.concernedItems = concernedItems;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public CouponType getType() {
        return type;
    }

    public void setType(CouponType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<Item> getConcernedItems() {
        return concernedItems;
    }

    public void setConcernedItems(List<Item> concernedItems) {
        this.concernedItems = concernedItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coupon coupon = (Coupon) o;

        if (!id.equals(coupon.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", concernedItems=" + concernedItems +
                '}';
    }
}
