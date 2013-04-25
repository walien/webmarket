'use strict';

angular.module(webmarketUIModuleName)
    .controller('ItemsAdminCtrl', function ($scope, $routeParams, $location, Item, Session, Notification) {

        /////////////////
        // SCOPE INIT
        /////////////////

        $scope.items = [];
        $scope.predicate = "id";
        $scope.reverse = false;

        //////////////////////////////
        // RETRIEVE ITEMS FROM SERVER
        //////////////////////////////

        var items = Item.getAll(function () {
            $scope.items = items;
        });

        /////////////////////
        // USER ACTIONS
        /////////////////////

        $scope.removeItem = function (index, item) {
            var response = Item.remove({id: item.id}, Session.getID(), function () {

                if (response.status == true) {

                    // Refresh the list
                    var items = Item.getAll(function () {
                        $scope.items = items;
                    });

                    // Notification
                    Notification.info('Item removed', "The item was removed successfully");
                } else {
                    Notification.error('Error', "An error occurred during the item deletion...");
                }
            });
        };
    });