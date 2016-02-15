'use strict';

angular.module('jCMSApp')
	.controller('PagesDeleteController', function($scope, $uibModalInstance, entity, Pages) {

        $scope.pages = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Pages.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
