'use strict';

angular.module(webmarketUIModuleName)
    .controller('GlobalCtrl', function ($scope) {

        /////////////////////////////
        // PUSH GLOBAL VALUES
        /////////////////////////////

        $scope.currency = defaultCurrency;

        $scope.session = {};

    });
