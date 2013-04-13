'use strict';

angular.module('webmarketWebUi')
    .controller('ItemsCtrl', function ($scope, $location) {

        $scope.items = [];

        //////////////////////////////
        // RETRIEVE ITEMS FROM SERVER
        //////////////////////////////

        $.get(allItemsURL, function (response) {
            $scope.items = response;
            $scope.$apply();
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
