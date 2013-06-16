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

import java.util.List;

public class Coupon {

    private CouponType type;

    private double amount;

    private List<Item> concernedItem;

    public Coupon(CouponType type, double amount, List<Item> concernedItem) {
        this.type = type;
        this.amount = amount;
        this.concernedItem = concernedItem;
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

    public List<Item> getConcernedItem() {
        return concernedItem;
    }

    public void setConcernedItem(List<Item> concernedItem) {
        this.concernedItem = concernedItem;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "type=" + type +
                ", amount=" + amount +
                ", concernedItem=" + concernedItem +
                '}';
    }
}
