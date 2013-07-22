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
    .controller('CouponEditorCtrl', function ($scope, $routeParams, $timeout, Coupon, Notification) {

        $scope.coupon = {};
        $scope.types = [
            { value: "PERCENTAGE", label: "Percentage"},
            { value: "AMOUNT", label: "Fixed discount"}
        ];
        $scope.isEditor = false;

        if ($routeParams.couponID) {
            $scope.isEditor = true;
            $scope.coupon = Coupon.get($routeParams.couponID);
        }

        $scope.saveCoupon = function () {
            Coupon.save($scope.coupon, function (response) {
                if (response.status) {
                    Notification.success("Success", "Coupon was successfuly saved !");
                    $scope.redirect('/admin/coupons');
                } else {
                    Notification.error("Error", "Error during coupon data changes...");
                }
            });
        };
    });