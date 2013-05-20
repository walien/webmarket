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

///////////////////////
// DATA SERVICES
///////////////////////

angular.module(webmarketServicesModule).
    factory('Item', function ($resource, Session) {

        var Item = $resource('/rest/items/:id', {id: '@id', sessionID: '@sessionID'}, {
            getAll: {method: 'GET', isArray: true},
            _save: {method: 'POST'},
            _remove: {method: 'DELETE'}
        });

        return angular.extend(Item, {
            save: function (item, fct) {
                return Item._save({
                    id: item.id,
                    sessionID: Session.getID()
                }, item, fct);
            },
            remove: function (item, fct) {
                return Item._remove({
                    id: item.id,
                    sessionID: Session.getID()
                }, fct);
            }
        });
    });

angular.module(webmarketServicesModule).
    factory('Tag', function ($resource, Session) {

        var Tag = $resource('/rest/tags/:id', {id: '@id', sessionID: '@sessionID'}, {
            getAll: {method: 'GET', isArray: true},
            _save: {method: 'POST'},
            _remove: {method: 'DELETE'}
        });

        return angular.extend(Tag, {
            save: function (tag, fct) {
                return Tag._save({
                    id: tag.id,
                    sessionID: Session.getID()
                }, tag, fct);
            },
            remove: function (tag, fct) {
                return Tag._remove({
                    id: tag.id,
                    sessionID: Session.getID()
                }, fct);
            }
        });
    });

angular.module(webmarketServicesModule).
    factory('Cart', function ($resource, Session) {

        var Cart = $resource('/rest/orders/:id', {id: '@id', sessionID: '@sessionID'}, {
            _query: {method: 'GET', isArray: true},
            _order: {method: 'POST'}
        });

        // Init the cart
        var _cart = {lines: [], user: Session.getUser(), date: new Date()};

        return angular.extend(Cart, {
            query: function (id, fct) {
                return Cart._query({
                    id: id,
                    sessionID: Session.getID()
                }, fct);
            },
            order: function (order, fct) {
                return Cart._order({
                    sessionID: Session.getID()
                }, order, fct);
            },
            get: function () {
                return _cart;
            },
            addItem: function (item, qty) {
                console.log("Item " + JSON.stringify(item) + " (" + qty + ") ==> Cart");
                _cart.lines.push({item: item, quantity: qty});
            },
            clear: function () {
                console.log("Clearing the cart...");
                _cart = {lines: [], user: Session.getUser(), date: new Date()};
            }
        });
    });

angular.module(webmarketServicesModule).
    factory('User', function ($resource) {
        return $resource('/rest/users/:id', {id: '@id'});
    });
