'use strict';

angular.module(webmarketUIModuleName)
    .controller('LoginCtrl', function ($scope, $routeParams, $location, Auth, Session) {

        // Do authentication
        $scope.doAuth = function () {

            var authResult = Auth.login($.param({
                username: $scope.username,
                pwd: $scope.pwd
            }), function () {

                // Set the new session ID
                Session.setID(authResult.sessionID);

                // Set the logged username
                Session.setUserName(authResult.user.username);

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