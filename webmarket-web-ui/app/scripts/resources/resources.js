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
    factory('Item', function ($resource, Session) {
        return $resource('/rest/items/:id', {id: '@id', sessionID: Session.getID()}, {
            getAll: {method: 'GET', isArray: true}
        });
    });

angular.module(webmarketResourcesModuleName).
    factory('User', function ($resource, Session) {
        return $resource('/rest/users/:id', {id: '@id', sessionID: Session.getID()});
    });

///////////////////////////////
// CLIENT-SIDE SESSION SERVICE
///////////////////////////////

angular.module(webmarketResourcesModuleName)
    .factory('Session', function () {

        // Retrieve the sessionID & the username
        // from a HTML5 local storage
        var _sessionID = localStorage.getItem('session.id');
        var _user = JSON.parse(localStorage.getItem('current.user'));

        return {
            getID: function () {
                return _sessionID;
            },
            setID: function (id) {
                _sessionID = id;
                localStorage.setItem('session.id', _sessionID);
            },
            clear: function () {
                _sessionID = null;
                _user = null;
                localStorage.setItem('session.id', null);
                localStorage.setItem('current.user', null);
            },
            isLogged: function () {
                return _sessionID != "null" && _sessionID != null;
            },
            setUser: function (user) {
                _user = user;
                localStorage.setItem('current.user', JSON.stringify(_user));
            },
            getUser: function () {
                return _user;
            }
        }
    });