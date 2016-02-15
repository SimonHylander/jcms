'use strict';

angular.module('jCMSApp')
    .factory('Pages', function ($resource, DateUtils) {
        return $resource('api/pages/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });

