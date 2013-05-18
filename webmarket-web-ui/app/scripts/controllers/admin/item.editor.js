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
    .controller('ItemEditorCtrl', function ($scope, $routeParams, $timeout, Item, Tag, Notification) {

        /////////////////
        // SCOPE INIT
        /////////////////

        $scope.item = {};
        $scope.item.tags = [];
        $scope.current = {};
        $scope.tags = [];

        //////////////////////////////
        // RETRIEVE ITEMS FROM SERVER
        //////////////////////////////

        // Check if the item id is provided, if its the case,
        // it's an edition, otherwise it's a creation
        if ($routeParams.id) {
            Item.get({id: $routeParams.id}, function (item) {
                $scope.item = item;
            });
        }

        // Watch the selected file in order to computing it
        $scope.$watch("current.file", function (file) {
            if (file == null) {
                return;
            }
            $scope.computeImage(file);
        });

        // Retrieve all available tags
        Tag.query(function (tags) {
            $scope.tags = tags;
        });

        /////////////////////
        // USER ACTIONS
        /////////////////////

        /**
         * Compute base64 format of the chosen image
         * and associate it with the current item
         * @param f
         */
        $scope.computeImage = function (f) {

            // Check the type of the selected file
            if (!f.type.match('image.*')) {
                return;
            }

            // Load the content of the image
            var reader = new FileReader();

            // Called when the data is loaded
            reader.onload = function (e) {
                $scope.item.base64Image = e.target.result;
                $scope.$apply();
            };
            reader.readAsDataURL(f)
        };


        /**
         * Add a tag to the item
         * @param tag
         */
        $scope.addTag = function (tag) {
            $scope.item.tags.push(tag);
            $scope.$apply();
        };

        /**
         * Remove a tag from the item
         * @param index
         */
        $scope.removeTag = function (index) {
            $scope.item.tags.splice(index, 1);
        };

        /**
         * Save item changes
         */
        $scope.saveItem = function () {

            // Make request to the server
            Item.save($scope.item, function (response) {
                if (response.status) {
                    Notification.info("Success", "Item data were changed successfuly !");
                    $scope.redirect('/admin/items');
                } else {
                    Notification.error("Error", "Error during item data changes...");
                }
            });
        };
    });