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

angular.module('webmarket.filters').
    factory('localize',function () {
        return {
            // array to hold the localized resource string entries
            missingKeys: {},

            getLocalizedString: function (value, LocalDictionary, scope) {
                // default the result to an empty string
                var result = '';
                // make sure the dictionary has valid data
                if (LocalDictionary !== {}) {
                    result = LocalDictionary[value];
                    if (angular.isUndefined(result)) {
                        result = value + ' #';
                        if (angular.isUndefined(this.missingKeys[value])) {
                            console.error('missing key:' + value);
                            this.missingKeys[value] = "";
                        }
                    }
                    if (result.indexOf('{{') > -1) {
                        result = scope.$eval(result);
                    }
                }
                // return the value to the call
                return result;
            }
        };
    }).
    filter('i18n', function (localize, LocalDictionary) {
        return function (input, $scope) {
            return localize.getLocalizedString(input, LocalDictionary, $scope);
        };
    });