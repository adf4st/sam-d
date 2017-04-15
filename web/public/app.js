'use strict';

require('es5-shim');
require('es5-sham');

require('jquery');
var angular = require('angular');

require('angular-ui-router');
require('angular-animate');
require('angular-ui-bootstrap');
require('angular-spinner');
require('angular-ui-mask');
require('ng-table');
require('angular-jwt');

// Declare app level module which depends on views, and components
var app = angular.module('myApp', [
    'ui.router',
    'ngAnimate',
    'ui.bootstrap',
    'angularSpinner',
    'ui.mask',
    'ngTable',
    'angular-jwt'
]);

require('./services/services.js');

app.config(function ($stateProvider, $urlRouterProvider, $httpProvider, jwtOptionsProvider) {
        $urlRouterProvider.otherwise("/home");

        jwtOptionsProvider.config({
            tokenGetter: [function () {
                return localStorage.getItem('id_token');
            }],
            // TODO : read whiteListedDomains from environment variable
            // TODO : if you're deploying this you must manually include the prod API hostname here!!
            whiteListedDomains: ["localhost", "alexfabian.net", "52.201.231.11"],  // whiteListedDomains should NOT contain http:, port numbers, or paths
            unauthenticatedRedirector: ['$state', 'AlertsService', function ($state, AlertsService) {
                $state.go('login');
                AlertsService.addAlert({
                    text : "Please sign in",
                    title : "Secured Page: ",
                    type : "alert-warning"
                })
            }]
        });

        $httpProvider.interceptors.push('jwtInterceptor');
    });

app.run(['authManager', 'ConfigService', function (authManager, ConfigService) {
        authManager.redirectWhenUnauthenticated();
        authManager.checkAuthOnRefresh();
        ConfigService.initializeConfig();
    }]);

app.controller('Controller', function Controller($http, UserService) {
        // If localStorage contains the id_token it will be sent in the request
        // Authorization: Bearer [yourToken] will be sent

        // //Sample GOOGLE ANALYTICS CODE
        // AnalyticsProvider.setAccount('xxxxxxxxxxx');
        //
        // // track all routes (or not)
        // AnalyticsProvider.trackPages(true);
        //
        // // track all url query params (default is false)
        // AnalyticsProvider.trackUrlParams(true);
        //
        // // Enable enhanced link attribution
        // AnalyticsProvider.useEnhancedLinkAttribution(true);

    });


// CSS
var css = require('./app.css');

// components (they depends on the myApp module so these must be required after myApp is defined)

require('./filters/filters.js');

require('./directives/nav/nav.js');
require('./directives/alertsDisplay/alertsDisplay.js');

require('./components/home/home.js');
require('./components/staff/staff.js');
require('./components/login/login.js');
require('./components/tools/tools.js');
require('./components/tools/toolModalController.js');
require('./components/account/account.js');
require('./components/private/private.js');

