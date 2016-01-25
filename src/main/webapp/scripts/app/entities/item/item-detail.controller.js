'use strict';

angular.module('blogaggrApp')
    .controller('ItemDetailController', function ($scope, $rootScope, $stateParams, entity, Item) {
        $scope.item = entity;
        $scope.load = function (id) {
            Item.get({id: id}, function(result) {
                $scope.item = result;
            });
        };
        var unsubscribe = $rootScope.$on('blogaggrApp:itemUpdate', function(event, result) {
            $scope.item = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
