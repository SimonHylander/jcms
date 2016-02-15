'use strict';

angular.module('jCMSApp')
    .controller('PagesDetailController', function ($scope, $rootScope, $stateParams, entity, Pages) {
        $scope.pages = entity;
        $scope.load = function (id) {
            Pages.get({id: id}, function(result) {
                $scope.pages = result;
            });
        };
        var unsubscribe = $rootScope.$on('jCMSApp:pagesUpdate', function(event, result) {
            $scope.pages = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
