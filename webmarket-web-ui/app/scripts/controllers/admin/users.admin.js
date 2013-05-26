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
    .controller('UsersAdminCtrl', function ($scope, $routeParams, $location, User, Notification) {

        /////////////////
        // SCOPE INIT
        /////////////////

        $scope.items = [];
        $scope.predicate = "role";
        $scope.reverse = false;

        // Retrieve users list
        $scope.users = User.query();

        /**
         * Remove a specific user
         * @param index
         */
        $scope.removeUser = function (index) {
            User.remove($scope.users[index], function (response) {
                if (response.status == true) {
                    Notification.success("Removal", "The user has been removed successfuly");
                    $scope.users = User.query();
                } else {
                    Notification.error("Removal", "An error occured during user deletion !");
                }
            });
        };
    });