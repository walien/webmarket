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
    .controller('GlobalCtrl', function ($scope, $location, Session) {

        /////////////////////////////
        // PUSH GLOBAL VALUES
        /////////////////////////////

        $scope.currency = '€';
        $scope.isLogged = false;

        $scope.$watch(Session.getSession, function (value) {
            if (value != null) {
                $scope.isLogged = true;
                $scope.username = Session.getUser().username;
            } else {
                $scope.isLogged = false;
                $scope.redirect("/");
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
    });
