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
    .controller('ReferencingAdminCtrl', function ($scope, $routeParams, $location, Tag, Notification) {

        // Retrieve all tags
        $scope.tags = Tag.query();
        $scope.selected = {};
        $scope.predicate = "id";
        $scope.reverse = false;

        /**
         * Open tag editing modal
         * @param tag
         */
        $scope.openTagEditorModal = function (tag) {
            // Edit existing one
            if (tag != undefined) {
                $scope.selected = tag;
            } else {
                $scope.selected = {};
            }
            // Open modal
            $("#tagEditorModal").modal("show");
        };

        /**
         * Save changes on the selected tag
         */
        $scope.saveTag = function () {
            $("#tagEditorModal").modal("hide");
            Tag.save($scope.selected, function (response) {
                if (response.status) {
                    Notification.success('Tag updated', "The tag was successfuly updated !");
                    $scope.tags = Tag.query();
                } else {
                    Notification.error('Error', "Error during tag editing...");
                }
                $scope.selected = {};
            });
        };

        /**
         * Remove an existing tag
         * @param tag
         */
        $scope.removeTag = function (tag) {
            Tag.remove(tag, function (response) {
                if (response.status) {
                    Notification.success('Tag removed', "The tag was successfuly removed !");
                } else {
                    Notification.error('Error', "Error during tag removing...");
                }
                $scope.tags = Tag.query();
            });
        };
    });