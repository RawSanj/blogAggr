'use strict';

angular.module('blogaggrApp').controller('BlogDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Blog',
        function($scope, $stateParams, $uibModalInstance, entity, Blog) {

        $scope.blog = entity;
        $scope.load = function(id) {
            Blog.get({id : id}, function(result) {
                $scope.blog = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('blogaggrApp:blogUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.blog.id != null) {
                Blog.update($scope.blog, onSaveSuccess, onSaveError);
            } else {
                Blog.save($scope.blog, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
