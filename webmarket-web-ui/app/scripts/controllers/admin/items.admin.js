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
    .controller('ItemsAdminCtrl', function ($scope, $routeParams, $location, Item, Session, Notification) {

        /////////////////
        // SCOPE INIT
        /////////////////

        $scope.items = [];
        $scope.predicate = "id";
        $scope.reverse = false;

        //////////////////////////////
        // RETRIEVE ITEMS FROM SERVER
        //////////////////////////////

        Item.getAll(function (items) {
            $scope.items = items;
        });

        /////////////////////
        // USER ACTIONS
        /////////////////////

        /**
         * Remove an item
         * @param index
         * @param item
         */

        $scope.removeItem = function (index, item) {

            // Request to the server
            Item.remove(item, function (response) {

                // Analyze the response
                if (response.status == true) {

                    // Refresh the list
                    var items = Item.getAll(function () {
                        $scope.items = items;
                    });

                    // Notification
                    Notification.success('Item removed', "The item was removed successfully");
                } else {
                    Notification.error('Error', "An error occurred during the item deletion...");
                }
            });
        };
    });