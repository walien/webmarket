/*
 * Copyright 2013 - Elian ORIOU
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

'use strict';


/////////////////
// MAIN MODULE
/////////////////
angular.module('webmarket', ['webmarket.ui']);

/////////////////
// MODULE NAMES
/////////////////
var webmarketUIModule = 'webmarket.ui';
var webmarketServicesModule = 'webmarket.services';
var webmarketFiltersModule = 'webmarket.filters';

/////////////////
// MODULES
/////////////////

function checkRights(role, current) {
    var session = localStorage.getItem('session');
    if (session == null || session == 'null') {
        return '/error';
    }
    session = JSON.parse(session);
    if (session.user && session.user.role == role) {
        return current;
    } else {
        return '/error';
    }
}

var checkAdminRights = function (params, current) {
    return checkRights('ADMIN', current);
};

var checkCustomerRights = function (params, current) {
    return checkRights('CUSTOMER', current);
};

angular.module(webmarketUIModule, ['webmarket.services', 'webmarket.filters'])
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
            .when('/items', {
                templateUrl: 'views/item/items.html',
                controller: 'ItemsCtrl'
            })
            .when('/items/:id/details', {
                templateUrl: 'views/item/details.html',
                controller: 'ItemDetailsCtrl'
            })
            .when('/items/:id/edit', {
                templateUrl: 'views/admin/item.edit.html',
                controller: 'ItemEditorCtrl',
                redirectTo: checkAdminRights
            })
            .when('/items/new', {
                templateUrl: 'views/admin/item.edit.html',
                controller: 'ItemEditorCtrl',
                redirectTo: checkAdminRights
            })
            .when('/users/:username/edit', {
                templateUrl: 'views/admin/user.edit.html',
                controller: 'UserEditorCtrl',
                redirectTo: checkAdminRights
            })
            .when('/users/new', {
                templateUrl: 'views/admin/user.edit.html',
                controller: 'UserEditorCtrl',
                redirectTo: checkAdminRights
            })
            .when('/orders/:orderID/details', {
                templateUrl: 'views/admin/order.details.html',
                controller: 'OrderDetailsCtrl',
                redirectTo: checkAdminRights
            })
            .when('/admin/items', {
                templateUrl: 'views/admin/items.admin.html',
                controller: 'ItemsAdminCtrl',
                redirectTo: checkAdminRights
            })
            .when('/admin/users', {
                templateUrl: 'views/admin/users.admin.html',
                controller: 'UsersAdminCtrl',
                redirectTo: checkAdminRights
            })
            .when('/admin/referencing', {
                templateUrl: 'views/admin/tags.admin.html',
                controller: 'ReferencingAdminCtrl',
                redirectTo: checkAdminRights
            })
            .when('/admin/orders', {
                templateUrl: 'views/admin/orders.admin.html',
                controller: 'OrdersAdminCtrl',
                redirectTo: checkAdminRights
            })
            .when('/cart/summary', {
                templateUrl: 'views/cart/cart.summary.html',
                controller: 'CartSummaryCtrl',
                redirectTo: checkCustomerRights
            })
            .when('/error', {
                templateUrl: 'views/error.html'
            })
            .otherwise({
                redirectTo: '/'
            });
    });

angular.module(webmarketServicesModule, ['ngResource']);

angular.module(webmarketFiltersModule, []);