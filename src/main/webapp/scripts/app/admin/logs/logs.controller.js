'use strict';

angular.module('blogaggrApp')
    .controller('LogsController', function ($scope, LogsService, LogsFileNameService, $timeout) {
        $scope.loggers = LogsService.findAll();

        $scope.changeLevel = function (name, level) {
            LogsService.changeLevel({name: name, level: level}, function () {
                $scope.loggers = LogsService.findAll();
            });
        };
        $scope.logfilename = [];
        $scope.logFileNames = LogsFileNameService.getLogFileNames();

    });