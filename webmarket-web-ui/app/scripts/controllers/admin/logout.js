'use strict';

angular.module(webmarketUIModuleName)
    .controller('LogoutCtrl', function ($scope, $routeParams, $location, Auth, Session) {

        Auth.logout($.param({
            username: $scope.username
        }), function () {

            // Set the new session ID
            Session.clear();

            // Emit the auth event
            $scope.$emit('logout');

            // Logging
            console.log("Session cleared...");
        });
    });