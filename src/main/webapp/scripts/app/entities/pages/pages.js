'use strict';

angular.module('ck', [])
    .directive('ckEditor', function(){
        return {
            /*
            require: '?ngModel',
            link: function(scope, elm, attr, ngModel){
                var ck = CKEDITOR.replace(elm[0]);
                if (!ngModel) return;

                ck.on('pasteState', function() {
                scope.$apply(function() {
                    ngModel.$setViewValue(ck.getData());
                });
              });

              ngModel.$render = function(value) {
                ck.setData(ngModel.$viewValue);
              };
            }*/
        }
    });


angular.module('jCMSApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pages', {
                parent: 'entity',
                url: '/pages',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Pages'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pages/pages.html',
                        controller: 'PagesController'
                    }
                },
                resolve: {
                }
            })
            .state('pages.detail', {
                parent: 'entity',
                url: '/pages/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Pages'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pages/pages-detail.html',
                        controller: 'PagesDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Pages', function($stateParams, Pages) {
                        return Pages.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pages.new', {
                parent: 'pages',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/pages/pages-dialog.html',
                        controller: 'PagesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null,
                                    title: null,
                                    type: null,
                                    content: null
                                    //author: null,
                                    //status: null,
                                    //created: null,
                                    //updated: null,

                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('pages', null, { reload: true });
                    }, function() {
                        $state.go('pages');
                    })
                }]
            })
            .state('pages.edit', {
                parent: 'pages',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/pages/pages-dialog.html',
                        controller: 'PagesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Pages', function(Pages) {
                                return Pages.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pages', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('pages.delete', {
                parent: 'pages',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/pages/pages-delete-dialog.html',
                        controller: 'PagesDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Pages', function(Pages) {
                                return Pages.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pages', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
