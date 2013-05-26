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

'use strict';

angular.module(webmarketUIModule)
    .controller('CartSummaryCtrl', function ($scope, $location, Cart, Order, Notification) {

        $scope.cart = Cart.get();

        $scope.fromNow = function () {
            return moment($scope.cart.date).calendar();
        };

        $scope.computeTotalAmount = function () {
            return Cart.computeTotalAmount($scope.cart);
        };

        $scope.showItemDetails = function (id) {
            $location.path("/items/" + id + "/details");
        };

        $scope.removeItem = function (item) {
            Cart.removeItem(item);
        };

        $scope.order = function () {
            Order.do(Cart.get(), function () {
                Notification.success("Success", "Ordering : done !");
            });
        };
    });

