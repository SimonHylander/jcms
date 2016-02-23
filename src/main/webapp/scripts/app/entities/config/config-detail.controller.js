'use strict';

angular.module('jCMSApp')
    .controller('ConfigDetailController', function ($scope, $rootScope, $stateParams, entity, Config) {
        $scope.config = entity;
        $scope.load = function (id) {
            Config.get({id: id}, function(result) {
                $scope.config = result;
            });
        };
        var unsubscribe = $rootScope.$on('jCMSApp:configUpdate', function(event, result) {
            $scope.config = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
