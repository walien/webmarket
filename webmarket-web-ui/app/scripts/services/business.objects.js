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

        // REST Backend methods
        var Item = $resource('/rest/items/:id', {id: '@id', sessionID: '@sessionID'}, {
            _save: {method: 'POST'},
            _remove: {method: 'DELETE'}
        });

        // Business methods
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

        // REST Backend methods
        var Tag = $resource('/rest/tags/:id', {id: '@id', sessionID: '@sessionID'}, {
            _save: {method: 'POST'},
            _remove: {method: 'DELETE'}
        });

        // Business methods
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
    factory('Cart', function ($resource) {

        // REST Backend methods
        var Cart = $resource('/rest/cart/:action', {action: '@action'}, {
            _compute: {method: 'POST', params: {action: 'compute'}}
        });

        // Load the cart
        var _cart = localStorage.getItem('cart');

        // Business methods
        Cart = angular.extend(Cart, {
            get: function () {
                return _cart;
            },
            compute: function (fct) {
                return Cart._compute({}, _cart, fct);
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
            init: function (user) {
                if (user) {
                    return;
                }
                _cart = {lines: [], couponsKeys: [], coupons: [], amount: 0};
                this.save();
            },
            clear: function () {
                Cart.init();
                this.save();
            }
        });

        // Init the cart
        if (_cart != "" && _cart != "null" && _cart != null) {
            _cart = JSON.parse(_cart);
        } else {
            Cart.init();
        }

        return Cart;
    });

angular.module(webmarketServicesModule).
    factory('Order', function ($resource, Session) {

        // REST Backend methods
        var Order = $resource('/rest/orders/:id', {id: '@id', sessionID: '@sessionID'}, {
            _get: {method: 'GET'},
            _query: {method: 'GET', isArray: true},
            _do: {method: 'POST'}
        });

        return angular.extend(Order, {
            get: function (id, fct) {
                return Order._get({
                    id: id,
                    sessionID: Session.getID()
                }, fct);
            },
            query: function (fct) {
                return Order._query({
                    sessionID: Session.getID()
                }, fct);
            },
            do: function (cart, fct) {
                return Order._do({
                    sessionID: Session.getID()
                }, cart, fct);
            }
        });
    });

angular.module(webmarketServicesModule).
    factory('User', function ($resource, Session) {

        // REST Backend methods
        var User = $resource('/rest/users/:username', {username: '@username', sessionID: '@sessionID'}, {
            _get: {method: 'GET'},
            _query: {method: 'GET', isArray: true},
            _save: {method: 'POST'},
            _remove: {method: 'DELETE'}
        });

        // Business methods
        return angular.extend(User, {
            get: function (username, fct) {
                return User._get({
                    username: username,
                    sessionID: Session.getID()
                }, fct);
            },
            query: function (fct) {
                return User._query({
                    sessionID: Session.getID()
                }, fct);
            },
            save: function (username, user, fct) {
                return User._save({
                    username: username,
                    sessionID: Session.getID()
                }, user, fct);
            },
            remove: function (user, fct) {
                return User._remove({
                    username: user.username,
                    sessionID: Session.getID()
                }, fct);
            }
        });
    });

angular.module(webmarketServicesModule).
    factory('Coupon', function ($resource, Session) {

        var Coupon = $resource('/rest/coupons/:id', {id: '@id', sessionID: '@sessionID'}, {
            _get: {method: 'GET'},
            _query: {method: 'GET', isArray: true},
            _save: {method: 'POST'},
            _remove: {method: 'DELETE'}
        });

        return angular.extend(Coupon, {
            get: function (id, fct) {
                return Coupon._get({
                    id: id,
                    sessionID: Session.getID()
                }, fct);
            },
            query: function (fct) {
                return Coupon._query({
                    sessionID: Session.getID()
                }, fct);
            },
            save: function (coupon, fct) {
                return Coupon._save({
                    id: coupon.id,
                    sessionID: Session.getID()
                }, coupon, fct);
            },
            remove: function (id, fct) {
                return Coupon._remove({
                    id: id,
                    sessionID: Session.getID()
                }, fct);
            }
        });
    });
