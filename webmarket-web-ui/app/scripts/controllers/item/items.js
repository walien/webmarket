'use strict';

angular.module(webmarketUIModuleName)
    .controller('ItemsCtrl', function ($scope, $location, Item, Session) {

        /////////////////
        // SCOPE INIT
        /////////////////

        $scope.items = [];

        //////////////////////////////
        // RETRIEVE ITEMS FROM SERVER
        //////////////////////////////

        var items = Item.getAll(function () {
            $scope.items = items;
        });

        //////////////////////////////
        // SCOPE METHODS
        //////////////////////////////

        /**
         * Display details about a selected item
         * @param id
         */

        $scope.showItemDetails = function (id) {
            $location.path("/items/" + id + "/details");
        }
    });
