'use strict';

angular.module(webmarketUIModuleName)
    .controller('LoginCtrl', function ($scope, $routeParams, $location, Auth) {

        /**
         * Make auth
         */
        $scope.doAuth = function () {
            var authResult = Auth.login($.param({
                username: $scope.username,
                pwd: $scope.pwd
            }), function () {
                console.log("Logged with the session id : ");
                console.log(authResult.sessionID);
            });
        }
    });