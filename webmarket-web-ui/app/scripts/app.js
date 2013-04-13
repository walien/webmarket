'use strict';

angular.module('webmarketWebUi', [])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'views/main.html',
                controller: 'MainCtrl'
            })
            .when('/items/:id/details', {
                templateUrl: 'views/item/details.html',
                controller: 'ItemDetailsCtrl'
            })
            .when('/items', {
                templateUrl: 'views/item/items.html',
                controller: 'ItemsCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
    });
