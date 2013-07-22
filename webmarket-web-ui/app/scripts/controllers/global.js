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
    .controller('GlobalCtrl', function ($scope, $location, Session, Cart) {

        /////////////////////////////
        // PUSH GLOBAL VALUES
        /////////////////////////////

        $scope.isLogged = false;
        $scope.role = null;

        $scope.$watch(Session.get, function (session) {
            if (session != null) {
                $scope.isLogged = true;
                $scope.username = Session.getUser().username;
                $scope.role = Session.getUser().role;
                Cart.init(session.user);
            } else {
                $scope.isLogged = false;
                $scope.username = null;
                $scope.role = null;
                $scope.redirect("/");
                Cart.clear();
            }
        });

        $scope.login = function () {
            $location.search("goto", $location.path()).path("/login");
        };

        $scope.logout = function () {
            $location.search("goto", null).path("/logout");
        };

        $scope.redirect = function (path) {
            $location.search('goto', null).path(path);
        };

        /**
         * Display details about a selected item
         * @param id
         */

        $scope.showItemDetails = function (id) {
            $location.path("/items/" + id + "/details");
        };
    });
