'use strict';

angular.module(webmarketUIModuleName)
    .controller('ItemsAdminCtrl', function ($scope, $routeParams, $location, Item) {

        /////////////////
        // SCOPE INIT
        /////////////////

        $scope.item = {};

        // Retrieve items list from server
        var items = Item.get(function () {
            console.log(items);
        });

        /////////////////////
        // USER ACTIONS
        /////////////////////


    });