'use strict';


/////////////////
// MAIN MODULE
/////////////////
angular.module('webmarket', ['webmarket.ui', 'webmarket.resources']);

/////////////////
// MODULE NAMES
/////////////////
var webmarketUIModuleName = 'webmarket.ui';
var webmarketResourcesModuleName = 'webmarket.resources';

/////////////////
// MODULES
/////////////////
angular.module(webmarketUIModuleName, ['webmarket.resources'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'views/main.html',
                controller: 'MainCtrl'
            })
            .when('/login', {
                templateUrl: 'views/admin/login.html',
                controller: 'LoginCtrl'
            })
            .when('/logout', {
                templateUrl: 'views/admin/logout.html',
                controller: 'LogoutCtrl'
            })
            .when('/items/:id/details', {
                templateUrl: 'views/item/details.html',
                controller: 'ItemDetailsCtrl'
            })
            .when('/items', {
                templateUrl: 'views/item/items.html',
                controller: 'ItemsCtrl'
            })
            .when('/admin/items', {
                templateUrl: 'views/admin/items.admin.html',
                controller: 'ItemsAdminCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
    });

angular.module(webmarketResourcesModuleName, ['ngResource']);
