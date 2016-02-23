'use strict';

angular.module('jCMSApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('config', {
                parent: 'entity',
                url: '/configs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Configs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/config/configs.html',
                        controller: 'ConfigController'
                    }
                },
                resolve: {
                }
            })
            .state('config.detail', {
                parent: 'entity',
                url: '/config/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Config'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/config/config-detail.html',
                        controller: 'ConfigDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Config', function($stateParams, Config) {
                        return Config.get({id : $stateParams.id});
                    }]
                }
            })
            .state('config.new', {
                parent: 'config',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/config/config-dialog.html',
                        controller: 'ConfigDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    site_name: null,
                                    email: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('config', null, { reload: true });
                    }, function() {
                        $state.go('config');
                    })
                }]
            })
            .state('config.edit', {
                parent: 'config',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/config/config-dialog.html',
                        controller: 'ConfigDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Config', function(Config) {
                                return Config.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('config', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('config.delete', {
                parent: 'config',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/config/config-delete-dialog.html',
                        controller: 'ConfigDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Config', function(Config) {
                                return Config.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('config', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
