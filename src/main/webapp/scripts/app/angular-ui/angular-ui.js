'use strict';

angular.module('blogaggrApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('angular-ui', {
                parent: 'site',
                url: '/angular-ui',
                data: {
                    authorities: [],
                    pageTitle: 'AngularUI'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/angular-ui/angular-ui.html',
                        controller: 'AngularUIController'
                    }
                }
            });
    });
