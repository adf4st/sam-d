'use strict';
var angular = require('angular');

var homeView = angular.module('myApp');

homeView.config(function ($stateProvider) {
    $stateProvider.
        state('home', {
            url: "/home",
            templateUrl: "components/home/home.html",
            controller: "HomeCtrl"
        })
});

homeView.controller('HomeCtrl', ['$scope', 'AlertsService', '$state',
    function ($scope, AlertsService, $state) {

        var sampleAlerts = [
            {
                text : "This is a a test success alert.",
                title : "Success!",
                type : "alert-success"
            },
            {
                text : "This is a a test warning alert.",
                title : "Warning!",
                type : "alert-warning"
            },
            {
                text : "This is a a test info alert.",
                title : "Warning!",
                type : "alert-info"
            },
            {
                text : "This is a a test danger alert.",
                title : "DANGER!",
                type : "alert-danger"
            }

        ];

        $scope.randomAlert = function () {
            AlertsService.addAlert( angular.copy(sampleAlerts[Math.floor(Math.random() * 3)]) );
        };

        $scope.changeState = function() {
            $state.go('login');
        };


        $scope.items = [
            'The first choice!',
            'And another choice for you.',
            'but wait! A third!'
        ];

    }
]);


