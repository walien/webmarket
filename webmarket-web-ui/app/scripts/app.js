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

/////////////////
// MODULES
/////////////////

angular.module(webmarketUIModule, ['webmarket.services'])
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
            .when('/items/:id/edit', {
                templateUrl: 'views/admin/item.edit.html',
                controller: 'ItemEditorCtrl'
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

angular.module(webmarketServicesModule, ['ngResource']);