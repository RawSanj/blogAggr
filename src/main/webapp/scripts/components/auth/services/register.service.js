'use strict';

angular.module('blogaggrApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


