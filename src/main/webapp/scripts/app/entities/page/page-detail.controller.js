'use strict';

angular.module('jCMSApp')
    .controller('PageDetailController', function ($scope, $rootScope, $stateParams, entity, Page) {
        $scope.page = entity;
        $scope.load = function (id) {
            Page.get({id: id}, function(result) {
                $scope.page = result;
            });
        };
        var unsubscribe = $rootScope.$on('jCMSApp:pageUpdate', function(event, result) {
            $scope.page = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
