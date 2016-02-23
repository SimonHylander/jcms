'use strict';

angular.module('jCMSApp')
	.controller('ConfigDeleteController', function($scope, $uibModalInstance, entity, Config) {

        $scope.config = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Config.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
