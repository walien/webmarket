///////////////////////
// DATA SERVICES
///////////////////////

angular.module(webmarketResourcesModuleName).
    factory('Auth', function ($resource) {
        return $resource('/rest/auth/:action', {action: '@action'}, {
            login: {method: 'POST', params: {action: 'login'}, headers: {'Content-Type': 'application/x-www-form-urlencoded'}},
            logout: {method: 'POST', params: {action: 'logout'}, headers: {'Content-Type': 'application/x-www-form-urlencoded'}}
        });
    });

angular.module(webmarketResourcesModuleName).
    factory('Item', function ($resource) {
        return $resource('/rest/items/:id', {id: '@id'}, {
            getAll: {method: 'GET', isArray: true}
        });
    });

angular.module(webmarketResourcesModuleName).
    factory('User', function ($resource) {
        return $resource('/rest/users/:id', {id: '@id'});
    });

///////////////////////////////
// CLIENT-SIDE SESSION SERVICE
///////////////////////////////

angular.module(webmarketResourcesModuleName)
    .factory('Session', function () {

        // Retrieve it from a cookie or store it
        // (if it doesn't exist or has changed)
        var _sessionID = null;
        var _userName = null;

        return {
            getID: function () {
                return _sessionID;
            },
            setID: function (id) {
                _sessionID = id;
            },
            clear: function () {
                _sessionID = null;
                _userName = null;
            },
            isLogged: function () {
                return _sessionID != null && _sessionID != '';
            },
            setUserName: function (name) {
                _userName = name;
            },
            getUserName: function () {
                return _userName;
            }
        }
    });