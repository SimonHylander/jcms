'use strict';

angular.module('jCMSApp')
    .controller('PageController', function ($scope, $state, Page) {

        $scope.pages = [];
        $scope.loadAll = function() {
            Page.query(function(result) {
               $scope.pages = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.page = {
                title: null,
                type: null,
                status: null,
                content: null,
                created: null,
                updated: null,
                created_by: null,
                updated_by: null,
                id: null
            };
        };
    });
