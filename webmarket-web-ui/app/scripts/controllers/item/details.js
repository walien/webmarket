'use strict';

angular.module('webmarketWebUi')
    .controller('ItemDetailsCtrl', function ($scope, $routeParams, $location) {

        $scope.item = {};
        $scope.item.id = $routeParams.id;

        $.get(itemResourceBaseURL + "/" + $scope.item.id, function (data) {
            $scope.item = data;
            $scope.$apply();
        });

        $scope.backToList = function () {
            $location.path("/items");
        }
    });
