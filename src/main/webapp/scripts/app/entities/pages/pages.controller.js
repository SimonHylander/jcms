'use strict';

angular.module('jCMSApp')
    .controller('PagesController', function ($scope, $state, Pages, Principal) {
        $scope.pages = [];
        $scope.loadAll = function() {
            Pages.query(function(result) {
               $scope.pages = result;
            });
        };
        $scope.loadAll();

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.pages = {
                title: null,
                type: null,
                author: null,
                status: null,
                created: null,
                updated: null,
                id: null
            };
        };
    });
