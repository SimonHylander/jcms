'use strict';

angular.module('jCMSApp')
    /*.controller('PagesDialogController', ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pages',
        function($scope, $stateParams, $uibModalInstance, entity, Pages) {
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

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.pages.id != null) {
                Pages.update($scope.pages, onSaveSuccess, onSaveError);
            } else {
                Pages.save($scope.pages, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }])*/
    .controller('PagesDialogController', function ($scope, $stateParams, $uibModalInstance, entity, Pages, Principal) {
            Principal.identity().then(function(account) {
                $scope.account = account;
            });

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

            $scope.save = function () {
                console.log('save');
                $scope.isSaving = true;
                var account = $scope.account;
                var userId = account.id;
//                $scope.pages.created_by = userId;
//                $scope.pages.updated_by = userId;

                console.log(userId);

                /*if ($scope.pages.id != null) {
                    Pages.update($scope.pages, onSaveSuccess, onSaveError);
                } else {
                    Pages.save($scope.pages, onSaveSuccess, onSaveError);
                }*/
            };

            $scope.clear = function() {
                $uibModalInstance.dismiss('cancel');
            };
        })

    .controller('CkeditorCtrl', ['$scope', function ($scope) {
        $scope.options = {
            language: 'en',
            allowedContent: true,
            entities: false
        };

        $scope.onReady = function () {

        }
    }]);
