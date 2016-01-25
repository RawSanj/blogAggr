'use strict';

angular.module('blogaggrApp')
    .factory('BlogItems', function ($resource, DateUtils) {
        return $resource('api/items/blog/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.publishedDate = DateUtils.convertLocaleDateFromServer(data.publishedDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.publishedDate = DateUtils.convertLocaleDateToServer(data.publishedDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.publishedDate = DateUtils.convertLocaleDateToServer(data.publishedDate);
                    return angular.toJson(data);
                }
            }
        });
    });
