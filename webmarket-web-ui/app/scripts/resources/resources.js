///////////////////////
// ITEMS DATA SERVICE
///////////////////////

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