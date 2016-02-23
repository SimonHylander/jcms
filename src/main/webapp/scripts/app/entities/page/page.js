'use strict';

angular.module('jCMSApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('page', {
                parent: 'entity',
                url: '/pages',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Pages'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/page/pages.html',
                        controller: 'PageController'
                    }
                },
                resolve: {
                }
            })
            .state('page.detail', {
                parent: 'entity',
                url: '/page/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Page'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/page/page-detail.html',
                        controller: 'PageDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Page', function($stateParams, Page) {
                        return Page.get({id : $stateParams.id});
                    }]
                }
            })
            .state('page.new', {
                parent: 'page',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/page/page-dialog.html',
                        controller: 'PageDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
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
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('page', null, { reload: true });
                    }, function() {
                        $state.go('page');
                    })
                }]
            })
            .state('page.edit', {
                parent: 'page',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/page/page-dialog.html',
                        controller: 'PageDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Page', function(Page) {
                                return Page.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('page', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('page.delete', {
                parent: 'page',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/page/page-delete-dialog.html',
                        controller: 'PageDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Page', function(Page) {
                                return Page.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('page', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
