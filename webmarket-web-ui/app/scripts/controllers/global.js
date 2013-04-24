'use strict';

angular.module(webmarketUIModuleName)
    .controller('GlobalCtrl', function ($scope, $location, Session) {

        /////////////////////////////
        // PUSH GLOBAL VALUES
        /////////////////////////////

        $scope.currency = '€';

        $scope.isLogged = false;

        $scope.$on("login", function () {
            $scope.isLogged = true;
            $scope.username = Session.getUserName();
            $scope.$broadcast("login");
        });

        $scope.$on("logout", function () {
            $scope.isLogged = false;
            $scope.$broadcast("logout");
        });

        $scope.auth = function () {
            $location.search("goto", "/").path("/login");
        };

        $scope.logout = function () {
            $location.search("goto", "/").path("/logout");
        };

        $scope.redirect = function (path) {
            $location.search('goto', null).path(path);
        };
    });
