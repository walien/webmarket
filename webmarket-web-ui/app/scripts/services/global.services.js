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

///////////////////////////
// AUTH SERVICE
///////////////////////////

angular.module(webmarketServicesModule)
    .factory('Session', function ($resource) {

        var Session = $resource('/rest/auth/:action', {action: '@action', sessionID: "@sessionID"}, {
            _login: {method: 'POST', params: {action: 'login'}, headers: {'Content-Type': 'application/x-www-form-urlencoded'}},
            _logout: {method: 'POST', params: {action: 'logout'}, headers: {'Content-Type': 'application/x-www-form-urlencoded'}},
            check: {method: 'GET', params: {action: 'check'}, isArray: false}
        });

        // Retrieve the session object from the browser
        // local storage
        var _session = localStorage.getItem('session');
        if (_session != "" && _session != "null") {
            _session = JSON.parse(_session);
        } else {
            _session = null;
        }

        Session = angular.extend(Session, {
            // Resource methods wrapping
            login: function (username, pwd, fct) {
                return Session._login($.param({
                    username: username,
                    pwd: pwd
                }), fct);
            },
            logout: function (username, fct) {
                return Session._logout($.param({
                    username: username
                }), fct);
            },
            // Session management
            get: function () {
                return _session;
            },
            getID: function () {
                if (_session == null) {
                    return null;
                }
                return _session.id;
            },
            setSession: function (session) {
                _session = session;
                localStorage.setItem('session', JSON.stringify(_session));
            },
            clear: function () {
                _session = null;
                localStorage.removeItem('session');
            },
            isLogged: function () {
                return _session != null;
            },
            getUser: function () {
                if (_session == null) {
                    return null;
                }
                return _session.user;
            }
        });

        // Check the validity of the session
        if (_session != null) {
            Session.check({sessionID: _session.id}, function (response) {
                if (response.status == false) {
                    console.log("The previously registered session is not valid. This session will be cleared...");
                    Session.clear();
                }
            });
        }

        return Session;
    });

///////////////////////////
// NOTIFICATION SERVICE
///////////////////////////

angular.module(webmarketServicesModule).
    factory('Notification', function () {

        var stack = {"dir1": "down", "dir2": "left", "push": "top"};
        var delay = 2000;

        return {
            info: function (title, text) {
                $.pnotify({
                    title: title,
                    text: text,
                    type: 'info',
                    delay: delay,
                    stack: stack
                });
            },
            error: function (title, text) {
                $.pnotify({
                    title: title,
                    text: text,
                    type: 'error',
                    delay: delay,
                    stack: stack
                });
            },
            success: function (title, text) {
                $.pnotify({
                    title: title,
                    text: text,
                    type: 'success',
                    delay: delay,
                    stack: stack
                });
            }
        }
    });