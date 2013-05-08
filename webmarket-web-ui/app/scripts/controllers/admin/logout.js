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
    .controller('LogoutCtrl', function ($scope, $timeout, Session) {

        Session.logout($scope.username, function () {

            // Set the new session ID
            Session.clear();

            // Logging
            console.log("Session cleared...");

            // Redirect to the main page
            $timeout(function () {
                $scope.redirect("/");
            }, 1500);
        });
    });