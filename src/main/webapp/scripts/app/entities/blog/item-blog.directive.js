'use strict';

angular.module('blogaggrApp')
    .directive('itemsByBlog', function() {
        return {
            restrict: 'E',
            // scope: {
            //     theme : '=itemsByBlog'
            // },
            templateUrl : "/scripts/app/entities/blog/item-blog.directive.html",
            controller: "ItemBlogController"
        };
    });