'use strict';

angular.module('blogaggrApp').controller('ItemDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Item',
        function($scope, $stateParams, $uibModalInstance, entity, Item) {

        $scope.item = entity;
        $scope.load = function(id) {
            Item.get({id : id}, function(result) {
                $scope.item = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('blogaggrApp:itemUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.item.id != null) {
                Item.update($scope.item, onSaveSuccess, onSaveError);
            } else {
                Item.save($scope.item, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForPublishedDate = {};

        $scope.datePickerForPublishedDate.status = {
            opened: false
        };

        $scope.datePickerForPublishedDateOpen = function($event) {
            $scope.datePickerForPublishedDate.status.opened = true;
        };
}]);
