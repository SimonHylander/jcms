'use strict';

angular.module('jCMSApp')
    .controller('PageDialogController', ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Page', 'Principal',
        function ($scope, $stateParams, $uibModalInstance, entity, Page, Principal) {
            Principal.identity().then(function(account) {
                $scope.account = account;
            });

            console.log(entity);

            $scope.page = entity;
            $scope.load = function(id) {
                console.log('load');
                Page.get({id : id}, function(result) {
                    $scope.page = result;
                });
            };

            var onSaveSuccess = function (result) {
                $scope.$emit('jCMSApp:pageUpdate', result);
                $uibModalInstance.close(result);
                $scope.isSaving = false;
            };

            var onSaveError = function (result) {
                $scope.isSaving = false;
            };

            $scope.save = function () {
                console.log($scope.account.id);
                $scope.page.created_by = $scope.account.id;
                $scope.page.updated_by = $scope.account.id;

                $scope.isSaving = true;
                if ($scope.page.id != null) {
                    Page.update($scope.page, onSaveSuccess, onSaveError);
                } else {
                    Page.save($scope.page, onSaveSuccess, onSaveError);
                }
            };

            $scope.clear = function() {
                $uibModalInstance.dismiss('cancel');
            };
        }
    ])
    .controller('CkeditorCtrl', ['$scope', function ($scope) {
        $scope.options = {
            language: 'en',
            allowedContent: true,
            entities: false
        };

        $scope.onReady = function () {

        }
    }]);
