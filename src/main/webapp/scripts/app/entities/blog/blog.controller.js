'use strict';

angular.module('blogaggrApp')
    .controller('BlogController', function ($scope, $state, Blog, ParseLinks, $rootScope) {

        $scope.blogs = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Blog.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.blogs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.blog = {
                url: null,
                name: null,
                id: null
            };
        };

        $scope.setBlogId = function (blogId) {
            $rootScope.blogIdForItems = blogId;
        }

    });
