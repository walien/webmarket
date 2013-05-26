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
    .controller('ItemDetailsCtrl', function ($scope, $routeParams, $location, Item, Cart, Notification) {

        /////////////////
        // SCOPE INIT
        /////////////////

        $scope.item = {};
        $scope.item.id = $routeParams.id;

        // Retrieve items list from server
        var item = Item.get({id: $scope.item.id}, function () {
            $scope.item = item;
        });

        /////////////////
        // USER ACTIONS
        /////////////////

        $scope.addToCart = function () {
            Cart.addItem($scope.item);
            Notification.success("Item added !", "The item was succesfuly added to the cart");
        };
    });
