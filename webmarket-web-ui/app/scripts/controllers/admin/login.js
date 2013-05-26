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
    .controller('LoginCtrl', function ($scope, $routeParams, $location, Session, Notification) {

        $scope.username = "";

        // Do authentication
        $scope.doAuth = function () {

            Session.login($scope.username, $scope.pwd, function (session) {

                if (session.user == null || session.id == null) {
                    Notification.error("Error", "Error during authentication. Please retry !");
                    return;
                }

                // Set the new session ID
                Session.setSession(session);

                // Logging & notify
                console.log("Received session : " + JSON.stringify(session));
                Notification.success("Login", "Login was a success.");

                // Redirect the client to the provided url
                if (!$routeParams.goto) {
                    $scope.redirect("/");
                } else {
                    $scope.redirect($routeParams.goto);
                }
            });
        }
    });