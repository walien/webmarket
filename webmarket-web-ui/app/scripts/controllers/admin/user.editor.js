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
    .controller('UserEditorCtrl', function ($scope, $routeParams, $timeout, User, Notification) {

        /////////////////
        // SCOPE INIT
        /////////////////

        $scope.user = {};
        $scope.rpwd = {value: ""};
        $scope.roles = [
            {label: "Administrator", value: "ADMIN"},
            {label: "Customer", value: "CUSTOMER"}
        ];
        $scope.isEditor = false;

        //////////////////////////////
        // RETRIEVE USER FROM SERVER
        //////////////////////////////

        // Check if the item id is provided, if its the case,
        // it's an edition, otherwise it's a creation
        if ($routeParams.username) {
            $scope.isEditor = true;
            User.get($routeParams.username, function (user) {
                $scope.user = user;
            });
        }

        $scope.saveUser = function () {

            if($scope.rpwd.value != $scope.user.pwd){
                Notification.error("Validation error", "Passwords are not the same !");
                return;
            }

            var handler = function (response) {
                if (response.status == true) {
                    Notification.success("User", "The user has been successfuly updated !");
                    $scope.redirect('/admin/users');
                } else {
                    Notification.error("User", "An error ocurred during the update !");
                }
            };
            if ($scope.isEditor) {
                User.save($scope.user.username, $scope.user, handler);
            } else {
                User.save('', $scope.user, handler);
            }
        };
    });