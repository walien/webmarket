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

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import fr.webmarket.backend.datasource.DataSourcesBundle;
import fr.webmarket.backend.model.Cart;
import fr.webmarket.backend.model.Coupon;
import fr.webmarket.backend.model.OrderLine;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderingBusinessUnit {

    public static Set<Coupon> resolveCartCoupons(Cart cart) {

        if (cart.getCouponsKeys() == null) {
            return null;
        }

        // Retrieve coupons objects from db
        Set<Coupon> coupons = new HashSet<Coupon>();
        for (String key : cart.getCouponsKeys()) {
            Coupon coupon = DataSourcesBundle.getDataSource().getCouponByKey(key);
            if (coupon == null) {
                continue;
            }
            coupons.add(coupon);
        }
        return coupons;
    }

    public static double computeTotalAmount(List<OrderLine> lines, Set<Coupon> coupons) {

        if (lines == null) {
            return 0;
        }

        double amount = 0;
        if (coupons == null || coupons.size() == 0) {
            for (OrderLine line : lines) {
                amount += line.getItem().getPrice() * line.getQuantity();
            }
            return amount;
        } else {

            // Coupute amount and apply specific coupons
            for (final OrderLine line : lines) {
                double lineAmount = line.getItem().getPrice() * line.getQuantity();
                Collection<Coupon> itemCoupons = Collections2.filter(coupons, new Predicate<Coupon>() {
                    @Override
                    public boolean apply(Coupon coupon) {
                        return coupon.getConcernedItems() != null && coupon.getConcernedItems().contains(line.getItem());
                    }
                });
                for (Coupon coupon : itemCoupons) {
                    lineAmount = applyCoupon(coupon, lineAmount);
                }
                amount += lineAmount;
            }

            // Apply global coupons
            Collection<Coupon> globalCoupons = Collections2.filter(coupons, new Predicate<Coupon>() {
                @Override
                public boolean apply(Coupon coupon) {
                    return coupon.getConcernedItems() == null || coupon.getConcernedItems().size() == 0;
                }
            });
            for (Coupon coupon : globalCoupons) {
                amount = applyCoupon(coupon, amount);
            }

            return amount;
        }
    }

    private static double applyCoupon(Coupon coupon, double amount) {
        if (coupon.getType() == null || coupon.getAmount() == 0) {
            return amount;
        }
        switch (coupon.getType()) {
            case AMOUNT:
                return amount - coupon.getAmount();
            case PERCENTAGE:
                return amount * ((100 - coupon.getAmount()) / 100);
            default:
                return 0;
        }
    }
}
