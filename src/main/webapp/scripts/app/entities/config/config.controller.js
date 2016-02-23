'use strict';

angular.module('jCMSApp')
    .controller('ConfigController', function ($scope, $state, Config) {

        $scope.configs = [];
        $scope.loadAll = function() {
            Config.query(function(result) {
               $scope.configs = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.config = {
                site_name: null,
                email: null,
                id: null
            };
        };
    });
