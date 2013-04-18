///////////////////////
// ITEMS DATA SERVICE
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