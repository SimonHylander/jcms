'use strict';

angular.module('jCMSApp').controller('ConfigDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Config',
        function($scope, $stateParams, $uibModalInstance, entity, Config) {

        $scope.config = entity;
        $scope.load = function(id) {
            Config.get({id : id}, function(result) {
                $scope.config = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jCMSApp:configUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.config.id != null) {
                Config.update($scope.config, onSaveSuccess, onSaveError);
            } else {
                Config.save($scope.config, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
