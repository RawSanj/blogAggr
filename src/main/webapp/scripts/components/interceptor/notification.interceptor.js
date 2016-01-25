 'use strict';

angular.module('blogaggrApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-blogaggrApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-blogaggrApp-params')});
                }
                return response;
            }
        };
    });
