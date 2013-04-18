'use strict';

angular.module(webmarketUIModuleName)
    .controller('ItemDetailsCtrl', function ($scope, $routeParams, $location, Item) {

        /////////////////
        // SCOPE INIT
        /////////////////

        $scope.item = {};
        $scope.item.id = $routeParams.id;

        // Retrieve items list from server
        var item = Item.get({id: $scope.item.id}, function () {
            $scope.item = item;
        });

        /////////////////
        // USER ACTIONS
        /////////////////

        $scope.backToList = function () {
            $location.path("/items");
        }

        $scope.addToCart = function () {

        }

    });
