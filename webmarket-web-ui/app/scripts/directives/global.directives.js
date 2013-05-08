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

angular.module(webmarketUIModule).directive('file', function () {
    return {
        scope: {
            file: '='
        },
        link: function (scope, element) {
            element.bind('change', function (event) {
                var files = event.target.files;
                scope.file = files[0];
                scope.$apply();
            });
        }
    };
});

angular.module(webmarketUIModule).directive('typeAhead', function () {
    return {
        scope: {
            source: '=',
            onSelect: '='
        },
        link: function (scope, element) {
            scope.$watch("source", function () {

                var map = {};
                var jqElt = angular.element(element);

                jqElt.bind("focus", function () {
                    jqElt.val("");
                });

                jqElt.typeahead({
                    source: function (query, process) {
                        var tagNames = [];
                        $.each(scope.source, function (index, tag) {
                            map[tag.name] = tag;
                            tagNames.push(tag.name);
                        });
                        process(tagNames);
                    },
                    updater: function (item) {
                        scope.onSelect(map[item]);
                        return item;
                    }
                });
            });
        }
    };
});
