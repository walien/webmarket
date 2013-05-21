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
        var _cart = localStorage.getItem('cart');
        if (_cart != "" && _cart != "null" && _cart != null) {
            _cart = JSON.parse(_cart);
        } else {
            Cart.init();
        }

        return angular.extend(Cart, {
            query: function (id, fct) {
                return Cart._query({
                    id: id,
                    sessionID: Session.getID()
                }, fct);
            },
            order: function (fct) {
                return Cart._order({
                    sessionID: Session.getID()
                }, _cart, fct);
            },
            get: function () {
                return _cart;
            },
            save: function () {
                localStorage.setItem('cart', JSON.stringify(_cart));
            },
            contains: function (item) {
                var _line = null;
                $.each(_cart.lines, function (index, line) {
                    if (line.item.id == item.id) {
                        _line = line;
                        return false;
                    }
                });
                return _line;
            },
            addItem: function (item) {
                var orderLine = this.contains(item);
                if (orderLine != null) {
                    orderLine.quantity++;
                } else {
                    _cart.lines.push({item: item, quantity: 1});
                }
                this.save();
            },
            removeItem: function (item) {
                var orderLine = this.contains(item);
                if (orderLine == null) {
                    return;
                }
                if (orderLine.quantity > 1) {
                    orderLine.quantity--;
                } else {
                    _cart.lines.splice(_cart.lines.indexOf(orderLine), 1);
                }
                this.save();
            },
            init: function () {
                _cart = {lines: [], user: Session.getUser(), date: new Date()};
                this.save();
            },
            clear: function () {
                _cart = {lines: [], user: null, date: new Date()};
                this.save();
            }
        });
    });

angular.module(webmarketServicesModule).
    factory('User', function ($resource) {
        return $resource('/rest/users/:id', {id: '@id'});
    });
