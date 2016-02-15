'use strict';

angular.module('jCMSApp').controller('PagesDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pages', 'ck',
        function($scope, $stateParams, $uibModalInstance, entity, Pages, ck ) {

        $scope.pages = entity;
        $scope.load = function(id) {
            Pages.get({id : id}, function(result) {
                $scope.pages = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jCMSApp:pagesUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        console.log($scope.pages);

        $scope.save = function () {
            console.log($scope.pages);
            /*$scope.isSaving = true;
            if ($scope.pages.id != null) {
                Pages.update($scope.pages, onSaveSuccess, onSaveError);
            } else {
                Pages.save($scope.pages, onSaveSuccess, onSaveError);
            }*/
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
