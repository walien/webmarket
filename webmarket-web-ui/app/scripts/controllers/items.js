'use strict';

angular.module('webmarketWebUi')
    .controller('ItemsCtrl', function ($scope) {

        $scope.items = [];

        //////////////////////////////
        // RETRIEVE ITEMS FROM SERVER
        //////////////////////////////

        $.get(allItemsURL, function (response) {
            console.log(response);
            $scope.items = response;
            $scope.$apply();
        });

        //////////////////////////////
        // SCOPE METHODS
        //////////////////////////////

        /**
         * Display details about a selected item
         * @param item
         */

        $scope.displayItemDetails = function (item) {

        }


    });
