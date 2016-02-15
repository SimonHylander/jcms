 'use strict';

angular.module('jCMSApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-jCMSApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-jCMSApp-params')});
                }
                return response;
            }
        };
    });
