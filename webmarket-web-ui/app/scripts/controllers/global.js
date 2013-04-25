'use strict';

angular.module(webmarketUIModuleName)
    .controller('GlobalCtrl', function ($scope, $location, Session) {

        /////////////////////////////
        // PUSH GLOBAL VALUES
        /////////////////////////////

        $scope.currency = '€';

        $scope.isLogged = Session.isLogged();

        if(Session.getUser() != null){
            $scope.username = Session.getUser().username;
        }

        $scope.$on("login", function () {
            $scope.isLogged = true;
            $scope.username = Session.getUser().username;
        });

        $scope.$on("logout", function () {
            $scope.isLogged = false;
        });

        $scope.auth = function () {
            $location.search("goto", $location.path()).path("/login");
        };

        $scope.logout = function () {
            $location.search("goto", null).path("/logout");
        };

        $scope.redirect = function (path) {
            $location.search('goto', null).path(path);
        };
    });
