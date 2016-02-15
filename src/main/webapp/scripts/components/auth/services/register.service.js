'use strict';

angular.module('jCMSApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


