'use strict';

angular.module('blogaggrApp')
    .controller('ItemBlogController', function ($scope, $state, BlogItems, ParseLinks, $rootScope) {
        // var id = $rootScope.blogIdForItems;
        // console.log(id);
        $scope.items = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;

        $scope.loadAll = function() {
            BlogItems.query({id: $rootScope.blogIdForItems, page: $scope.page, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.items.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.items = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.item = {
                title: null,
                description: null,
                publishedDate: null,
                link: null,
                id: null
            };
        };
    });