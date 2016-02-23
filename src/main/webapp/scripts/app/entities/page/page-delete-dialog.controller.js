'use strict';

angular.module('jCMSApp')
	.controller('PageDeleteController', function($scope, $uibModalInstance, entity, Page) {

        $scope.page = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Page.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
