'use strict';

angular.module('blogaggrApp')
    .factory('Blog', function ($resource, DateUtils) {
        return $resource('api/blogs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
