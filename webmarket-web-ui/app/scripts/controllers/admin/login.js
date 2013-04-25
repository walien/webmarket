'use strict';

angular.module(webmarketUIModuleName)
    .controller('LoginCtrl', function ($scope, $routeParams, $location, Auth, Session, Notification) {

        $scope.username = "";

        // Do authentication
        $scope.doAuth = function () {

            var authResult = Auth.login($.param({
                username: $scope.username,
                pwd: $scope.pwd
            }), function () {

                if (authResult.user == null || authResult.sessionID == null) {
                    Notification.error("Error", "Error during authentication. Please retry !");
                    return;
                }

                // Set the new session ID
                Session.setID(authResult.sessionID);

                // Set the logged username
                Session.setUser(authResult.user);

                // Emit the auth event
                $scope.$emit('login');

                // Logging
                console.log("The new session id : " + authResult.sessionID);

                // Redirect the client to the provided url
                if (!$routeParams.goto) {
                    $scope.redirect("/");
                } else {
                    $scope.redirect($routeParams.goto);
                }
            });
        }
    });