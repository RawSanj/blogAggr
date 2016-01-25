'use strict';

angular.module('blogaggrApp')
    .factory('LogsService', function ($resource) {
        return $resource('api/logs', {}, {
            'findAll': { method: 'GET', isArray: true},
            'changeLevel': { method: 'PUT'}
        });
    });


angular.module('blogaggrApp')
    .factory('LogsFileNameService', function ($resource) {
        return $resource('api/logs/files', {}, {
            'getLogFileNames': { method: 'GET', isArray: true}
        });
    });