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
import com.google.common.collect.Iterables;
import fr.webmarket.backend.model.Order;
import fr.webmarket.backend.model.OrderLine;

import java.util.List;

public class OrderingBusinessUnit {

    public static double computeTotalAmount(Order order) {
        return computeTotalAmount(order.getLines(), order.getCoupons());
    }

    public static double computeTotalAmount(List<OrderLine> lines, List<Coupon> coupons) {

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
                amount += line.getItem().getPrice() * line.getQuantity();
                List<Coupon> itemCoupons = (List<Coupon>) Iterables.filter(coupons, new Predicate<Coupon>() {
                    @Override
                    public boolean apply(Coupon coupon) {
                        return coupon.getConcernedItem().contains(line.getItem());
                    }
                });
                for (Coupon coupon : itemCoupons) {
                    applyCoupon(coupon, amount);
                }
            }

            // Apply global coupons
            List<Coupon> globalCoupons = (List<Coupon>) Iterables.filter(coupons, new Predicate<Coupon>() {
                @Override
                public boolean apply(Coupon coupon) {
                    return coupon.getConcernedItem() == null;
                }
            });
            for (Coupon coupon : globalCoupons) {
                amount = applyCoupon(coupon, amount);
            }

            return amount;
        }
    }

    private static double applyCoupon(Coupon coupon, double amount) {
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
