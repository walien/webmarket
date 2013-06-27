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
    .controller('CouponsAdminCtrl', function ($scope, $routeParams, $location, Notification, Coupon) {

        $scope.coupons = Coupon.query();

        $scope.addCoupon = function () {

        };

        $scope.removeCoupon = function (index) {
            var coupon = $scope.coupons[index];
            if (!coupon) {
                return;
            }
            Coupon.remove(coupon.id, function (response) {
                if (response.status == true) {
                    Notification.success("Success", "Coupon successfuly removed !");
                    $scope.coupons = Coupon.query();
                } else {
                    Notification.success("Error", "An error occured during coupon deletion !");
                }
            });
        };
    });