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

angular.module(webmarketUIModule)
    .controller('OrderDetailsCtrl', function ($scope, $routeParams, $location, Order) {

        $scope.order = Order.get($routeParams.orderID);

        $scope.prettyDate = function (date) {
            return moment(date).calendar();
        };

        $scope.computeTotalAmount = function (order) {
            return Order.computeTotalAmount(order);
        };
    });