'use strict';

angular.module('app.myDirective', [])
    .directive('myDirective', function(){
        return {
//            restrict: 'E',
            require: '?ngModel',
            link: function($scope, elm, attr, ngModel){

            }
        }
    });
